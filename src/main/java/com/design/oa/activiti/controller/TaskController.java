package com.design.oa.activiti.controller;


import com.alibaba.fastjson.JSONObject;
import com.design.oa.activiti.config.Reject;
import com.design.oa.activiti.vo.MyHistoricTaskInstance;
import com.design.oa.activiti.vo.MyTask;
import com.design.oa.dao.AnnouncementMapper;
import com.design.oa.dao.IncomingMapper;
import com.design.oa.dao.OutgoingMessageMapper;
import com.design.oa.model.*;
import com.design.oa.service.DocumentService;
import com.design.oa.service.LeaveNoteService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    ProcessEngine processEngine;
    @Resource
    LeaveNoteService leaveNoteService;
    @Resource
    AnnouncementMapper announcementMapper;
    @Resource
    OutgoingMessageMapper outgoingMessageMapper;
    @Resource
    IncomingMapper incomingMapper;
    @Resource
    DocumentService documentService;

    @GetMapping("/taskList")
    public String getTaskList(int rowSize, int page, int sign) {
        JSONObject jsonObject = new JSONObject();
        TaskService taskService = processEngine.getTaskService();
        User user = null;
        Object o = SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        if (o instanceof User) {
            user = (User) o;
        }
        List<Task> taskList = null;
        PageHelper.startPage(rowSize, page);
        if (sign == 0) {
            taskList = taskService.createTaskQuery()
                    .taskAssignee(String.valueOf(user.getUserId()))
                    .processDefinitionKeyLike("leaveBill").list();
        } else if (sign == 1) {
            taskList = taskService.createTaskQuery()
                    .taskAssignee(String.valueOf(user.getUserId()))
                    .processDefinitionKeyLike("announcement").list();
        }else if(sign == 2){
             taskList =  taskService.createTaskQuery()
                    .taskAssignee(String.valueOf(user.getUserId()))
                    .processDefinitionKeyLike("document").list();
            List<Task> taskList2 =  taskService.createTaskQuery()
                    .taskAssignee(String.valueOf(user.getUserId()))
                    .processDefinitionKeyLike("incoming").list();
            taskList.addAll(taskList2);
        }
        if(taskList!=null) {
            PageInfo<Task> pageList = new PageInfo<>(taskList);
            List<MyTask> res = new ArrayList<>();
            String userName = user.getUserName();
            pageList.getList().forEach(f -> {
                String processInstanceId = f.getProcessInstanceId();
                ProcessInstance processInstance = processEngine.getRuntimeService().createProcessInstanceQuery()
                        .processInstanceId(processInstanceId).singleResult();
                Deployment deployment = processEngine.getRepositoryService().createDeploymentQuery()
                        .deploymentId(processInstance.getDeploymentId()).singleResult();
                ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery()
                        .deploymentId(deployment.getId()).singleResult();
                MyTask myTask = new MyTask(f);
                myTask.setAssignee(userName);
                myTask.setProDefKey(processDefinition.getKey());
                myTask.setDeploymentName(deployment.getName());
                res.add(myTask);
            });
            PageInfo<MyTask> pageInfo = new PageInfo<>(res);
            PageHelper.clearPage();
            jsonObject.put("msg", 201);
            jsonObject.put("taskList", pageInfo);
            return jsonObject.toString();
        }else {
            jsonObject.put("msg",201);
            jsonObject.put("taskList", null);
            return null;
        }
    }

    @GetMapping("/formMessage")
    public String getFormMessage(String processInstanceId) {
        JSONObject jsonObject = new JSONObject();
        Object obj = null;
        ProcessInstance processInstance = processEngine.getRuntimeService()
                .createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        String proDefId = processInstance.getProcessDefinitionId();
        String businessKey = processInstance.getBusinessKey();
        if (proDefId.contains("leaveBill")) {
            obj = leaveNoteService.selectByPrimaryKey(Integer.parseInt(businessKey));
            LeaveNote leaveNote = (LeaveNote) obj;
            jsonObject.put("formMessage", leaveNote);
        } else if (proDefId.contains("announcement")) {
            obj = announcementMapper.selectByPrimaryKey(Integer.parseInt(businessKey));
            Announcement announcement = (Announcement) obj;
            jsonObject.put("formMessage", announcement);
        }else if(proDefId.contains("document")){
            obj = outgoingMessageMapper.selectByPrimaryKey(Integer.parseInt(businessKey));
            OutgoingMessage outgoingMessage = (OutgoingMessage) obj;
            jsonObject.put("formMessage", outgoingMessage);
        }else if(proDefId.contains("incoming")){
            obj = incomingMapper.selectByPrimaryKey(Integer.parseInt(businessKey));
            Incoming incoming = (Incoming)obj;
            jsonObject.put("formMessage", incoming);
        }
        String rejectReason = (String) processEngine.getRuntimeService().getVariable(processInstanceId, "reject_reason");
        jsonObject.put("msg", 201);
        jsonObject.put("rejectReason", rejectReason);
        return jsonObject.toString();
    }

    @PostMapping("/agree")
    public String agree(String suggestion, String taskId) {
        JSONObject jsonObject = new JSONObject();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("reject_reason", suggestion);
            processEngine.getTaskService().complete(task.getId(), map);
            jsonObject.put("msg", 201);
        } else
            jsonObject.put("msg", 202);
        return jsonObject.toString();
    }

    @PostMapping("/disagree")
    public String disagree(String suggestion, String currentTaskId, String desTaskId) {
        JSONObject jsonObject = new JSONObject();
        Reject reject = new Reject(processEngine);
        reject.rejectTask(desTaskId, currentTaskId, suggestion);
        jsonObject.put("msg", 201);
        return jsonObject.toString();
    }

    @GetMapping("/historyActivity")
    public String getHistoryActivity(String taskId) {
        JSONObject jsonObject = new JSONObject();
        HistoryService historyService = processEngine.getHistoryService();
        Task task = processEngine.getTaskService().createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        List<HistoricTaskInstance> historicTaskInstanceList = historyService
                .createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId)
                .orderByTaskCreateTime()
                .asc()
                .list();
        List<MyHistoricTaskInstance> res = new ArrayList<>();
        historicTaskInstanceList.forEach(f -> {
            MyHistoricTaskInstance myTask = new MyHistoricTaskInstance(f);
            res.add(myTask);
        });
        res.remove(res.size() - 1);
        jsonObject.put("msg", 201);
        jsonObject.put("historyActivityList", res);
        return jsonObject.toString();
    }

    @GetMapping("/historicTask")
    public String getHistoricTask(int id,String userName){
        System.out.println("selectedKeys:"+id);
        System.out.println("userName:"+userName);
        JSONObject jsonObject = new JSONObject();
        List<HashMap<String,Object>> list = documentService.historicTask(id,userName);
        jsonObject.put("msg",201);
        jsonObject.put("list",list);
        return jsonObject.toString();
    }
}
