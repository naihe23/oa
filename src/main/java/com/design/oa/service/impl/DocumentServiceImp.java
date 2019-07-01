package com.design.oa.service.impl;

import com.design.oa.dao.*;
import com.design.oa.model.*;
import com.design.oa.service.DocumentService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class DocumentServiceImp implements DocumentService {

    @Resource
    TextMapper textMapper;
    @Autowired
    ProcessEngine processEngine;
    @Resource
    IncomingMapper incomingMapper;
    @Resource
    OutgoingMessageMapper outgoingMessageMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    LeaveNoteMapper leaveNoteMapper;
    @Resource
    AnnouncementMapper announcementMapper;

    @Override
    public int saveText(Text text) {
        User user = new User();
        Object o = SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        if (o instanceof User) {
            user = (User) o;
        }
        text.setUserId(user.getUserId());
        int state = textMapper.insert(text);
        if (state > 0) {
            return 201;
        } else
            return 401;
    }

    @Override
    public List<Text> getTexts() {
        List<Text> texts = textMapper.selectAllTexts();
        return texts;
    }

    @Override
    public int saveOutgoing(OutgoingMessage outgoingMessage) {
        int state = outgoingMessageMapper.insert(outgoingMessage);
        if (state > 0)
            return 201;
        else
            return 401;
    }

    @Override
    public int saveIncoming(Incoming incoming) {
        int state = incomingMapper.insert(incoming);
        if (state > 0)
            return 201;
        else
            return 401;

    }

    @Override
    public List<HashMap<String, Object>> getOutMessageByUserId(String deploymentId, int sign) {
        User user = null;
        Object o = SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        if (o instanceof User) {
            user = (User) o;
        }
        List<HashMap<String, Object>> list = new ArrayList<>();
        //   List<OutgoingMessage> outgoingMessages = outgoingMessageMapper.selectByUserId(userId);
        List<HistoricTaskInstance> historicTaskInstanceList = processEngine.getHistoryService()
                .createHistoricTaskInstanceQuery().taskAssignee(String.valueOf(user.getUserId()))
                .deploymentId(deploymentId).list();
        for (HistoricTaskInstance historicTaskInstance : historicTaskInstanceList) {
            HashMap<String, Object> map = new HashMap<>();
            String proInsId = historicTaskInstance.getProcessInstanceId();
            ProcessInstance processInstance = processEngine.getRuntimeService().createProcessInstanceQuery()
                    .processInstanceId(proInsId).singleResult();
            int businessKey = Integer.parseInt(processEngine.getHistoryService().createHistoricProcessInstanceQuery()
                    .processInstanceId(proInsId)
                    .singleResult().getBusinessKey());
            if (sign == 0) {
                OutgoingMessage outgoingMessage = outgoingMessageMapper.selectByPrimaryKey(businessKey);
                Task task = null;
                User user1 = null;
                if (processInstance == null) {
                    map.put("currentNode", "已归档");
                    map.put("nextOperate", "已归档");
                } else {
                    task = processEngine.getTaskService().createTaskQuery().processInstanceId(proInsId)
                            .singleResult();
                    int nextId = Integer.parseInt(task.getAssignee());
                    user1 = userMapper.selectByPrimaryKey(nextId);
                    map.put("currentNode", task.getName());
                    map.put("nextOperate", user1.getUserName());
                }
                map.put("outgoing_id", outgoingMessage.getOutgoingMessageId());
                map.put("title", outgoingMessage.getOutgoingMessageTitle());
                map.put("creater", outgoingMessage.getUserName());
                map.put("createTime", outgoingMessage.getOutgoingMessageDate());
            } else if (sign == 1) {
                Incoming incoming = incomingMapper.selectByPrimaryKey(businessKey);
                Task task = null;
                User user1 = null;
                if (processInstance == null) {
                    map.put("currentNode", "已归档");
                    map.put("nextOperate", "已归档");
                } else {
                    task = processEngine.getTaskService().createTaskQuery().processInstanceId(proInsId)
                            .singleResult();
                    int nextId = Integer.parseInt(task.getAssignee());
                    user1 = userMapper.selectByPrimaryKey(nextId);
                    map.put("currentNode", task.getName());
                    map.put("nextOperate", user1.getUserName());
                }
                map.put("incomingId", incoming.getIncomingId());
                map.put("title", incoming.getIncomingTitle());
                map.put("creater", incoming.getUserName());
                map.put("createTime", incoming.getIncomingDate());
            }
            list.add(map);
        }
        return list;
    }

    @Override
    public List<HashMap<String, Object>> getMyOutMessage(String deploymentId) {
        User user = null;
        Object o = SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        if (o instanceof User) {
            user = (User) o;
        }
        List<HashMap<String, Object>> list = new ArrayList<>();
        List<OutgoingMessage> outgoingMessageList = outgoingMessageMapper.selectByUserId(user.getUserId());
        for (OutgoingMessage outgoingMessage : outgoingMessageList) {
            HashMap<String, Object> map = new HashMap<>();
            String businessKey = String.valueOf(outgoingMessage.getOutgoingMessageId());
//            ProcessInstance processInstance = processEngine.getRuntimeService().createProcessInstanceQuery()
//                    .processInstanceBusinessKey(businessKey).deploymentId(deploymentId).singleResult();
            HistoricProcessInstance historicProcessInstance = processEngine.getHistoryService()
                    .createHistoricProcessInstanceQuery().deploymentId(deploymentId)
                    .processInstanceBusinessKey(businessKey).singleResult();
            Task task = null;
            if (historicProcessInstance == null) {
                return null;
            } else if (historicProcessInstance.getEndTime() != null) {
                map.put("currentNode", "已归档");
                map.put("nextOperate", "已归档");
            } else {
                task = processEngine.getTaskService().createTaskQuery().processInstanceId(historicProcessInstance.getId())
                        .singleResult();
                int nextId = Integer.parseInt(task.getAssignee());
                User user1 = userMapper.selectByPrimaryKey(nextId);
                map.put("currentNode", task.getName());
                map.put("nextOperate", user1.getUserName());
            }
            map.put("outgoing_id", outgoingMessage.getOutgoingMessageId());
            map.put("title", outgoingMessage.getOutgoingMessageTitle());
            map.put("createTime", outgoingMessage.getOutgoingMessageDate());
            list.add(map);
        }

        return list;
    }

    @Override
    public List<HashMap<String, Object>> searchDocument(String title, String deploymentId,
                                                        int outgoingMessageUrgent, int userId) {
        User user = null;
        Object o = SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        if (o instanceof User) {
            user = (User) o;
        }
        List<HistoricTaskInstance> historicTaskInstanceList = null;
        List<HashMap<String, Object>> list = new ArrayList<>();
        if (deploymentId.equals("null")) {
            historicTaskInstanceList = processEngine.getHistoryService()
                    .createHistoricTaskInstanceQuery().taskAssignee(String.valueOf(user.getUserId())).list();
        } else {
            historicTaskInstanceList = processEngine.getHistoryService()
                    .createHistoricTaskInstanceQuery().taskAssignee(String.valueOf(user.getUserId()))
                    .deploymentId(deploymentId).list();
        }
        for (HistoricTaskInstance historicTaskInstance : historicTaskInstanceList) {
            HashMap<String, Object> map = new HashMap<>();
            String proInsId = historicTaskInstance.getProcessInstanceId();
            ProcessInstance processInstance = processEngine.getRuntimeService().createProcessInstanceQuery()
                    .processInstanceId(proInsId).singleResult();
            int businessKey = Integer.parseInt(processEngine.getHistoryService().createHistoricProcessInstanceQuery()
                    .processInstanceId(proInsId)
                    .singleResult().getBusinessKey());
            OutgoingMessage outgoingMessage = outgoingMessageMapper.selectByTitleAndUrgAndCreater(title, outgoingMessageUrgent, userId, businessKey);
            if (outgoingMessage == null) {
                continue;
            }
            Task task = null;
            User user1 = null;
            if (processInstance == null) {
                map.put("currentNode", "已归档");
                map.put("nextOperate", "已归档");
            } else {
                task = processEngine.getTaskService().createTaskQuery().processInstanceId(proInsId)
                        .singleResult();
                int nextId = Integer.parseInt(task.getAssignee());
                user1 = userMapper.selectByPrimaryKey(nextId);
                map.put("currentNode", task.getName());
                map.put("nextOperate", user1.getUserName());
            }
            map.put("outgoing_id", outgoingMessage.getOutgoingMessageId());
            map.put("title", outgoingMessage.getOutgoingMessageTitle());
            map.put("creater", outgoingMessage.getUserName());
            map.put("createTime", outgoingMessage.getOutgoingMessageDate());
            list.add(map);
        }
        return list;

    }

    @Override
    public List<HashMap<String, Object>> getMyIncMessage(String deploymentId) {
        User user = null;

        Object o = SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        if (o instanceof User) {
            user = (User) o;
        }
        List<HashMap<String, Object>> list = new ArrayList<>();
        List<Incoming> incomingList = incomingMapper.selectByUserId(user.getUserId());
        for (Incoming incoming : incomingList) {
            HashMap<String, Object> map = new HashMap<>();
            String businessKey = String.valueOf(incoming.getIncomingId());
//            ProcessInstance processInstance = processEngine.getRuntimeService().createProcessInstanceQuery()
//                    .processInstanceBusinessKey(businessKey).deploymentId(deploymentId).singleResult();
            HistoricProcessInstance historicProcessInstance = processEngine.getHistoryService()
                    .createHistoricProcessInstanceQuery().deploymentId(deploymentId)
                    .processInstanceBusinessKey(businessKey).singleResult();
            Task task = null;
            if (historicProcessInstance == null) {
                return null;
            } else if (historicProcessInstance.getEndTime() != null) {
                map.put("currentNode", "已归档");
                map.put("nextOperate", "已归档");
            } else {
                task = processEngine.getTaskService().createTaskQuery().processInstanceId(historicProcessInstance.getId())
                        .singleResult();
                int nextId = Integer.parseInt(task.getAssignee());
                User user1 = userMapper.selectByPrimaryKey(nextId);
                map.put("currentNode", task.getName());
                map.put("nextOperate", user1.getUserName());
            }
            map.put("incomingId", incoming.getIncomingId());
            map.put("title", incoming.getIncomingTitle());
            map.put("createTime", incoming.getIncomingDate());
            list.add(map);
        }
        return list;
    }

    @Override
    public List<HashMap<String, Object>> searchIncoming(String title, String deploymentId, int incomingUrgent, int userId) {
        User user = null;
        Object o = SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        if (o instanceof User) {
            user = (User) o;
        }
        List<HistoricTaskInstance> historicTaskInstanceList = null;
        List<HashMap<String, Object>> list = new ArrayList<>();
        if (deploymentId.equals("null")) {
            historicTaskInstanceList = processEngine.getHistoryService()
                    .createHistoricTaskInstanceQuery().taskAssignee(String.valueOf(user.getUserId())).list();
        } else {
            historicTaskInstanceList = processEngine.getHistoryService()
                    .createHistoricTaskInstanceQuery().taskAssignee(String.valueOf(user.getUserId()))
                    .deploymentId(deploymentId).list();
        }
        for (HistoricTaskInstance historicTaskInstance : historicTaskInstanceList) {
            HashMap<String, Object> map = new HashMap<>();
            String proInsId = historicTaskInstance.getProcessInstanceId();
            ProcessInstance processInstance = processEngine.getRuntimeService().createProcessInstanceQuery()
                    .processInstanceId(proInsId).singleResult();
            int businessKey = Integer.parseInt(processEngine.getHistoryService().createHistoricProcessInstanceQuery()
                    .processInstanceId(proInsId)
                    .singleResult().getBusinessKey());
            Incoming incoming = incomingMapper.selectByTitleAndUrgAndCreater(title, incomingUrgent, userId, businessKey);
            if (incoming == null) {
                continue;
            }
            Task task = null;
            User user1 = null;
            if (processInstance == null) {
                map.put("currentNode", "已归档");
                map.put("nextOperate", "已归档");
            } else {
                task = processEngine.getTaskService().createTaskQuery().processInstanceId(proInsId)
                        .singleResult();
                int nextId = Integer.parseInt(task.getAssignee());
                user1 = userMapper.selectByPrimaryKey(nextId);
                map.put("currentNode", task.getName());
                map.put("nextOperate", user1.getUserName());
            }
            map.put("incomingId", incoming.getIncomingId());
            map.put("title", incoming.getIncomingTitle());
            map.put("creater", incoming.getUserName());
            map.put("createTime", incoming.getIncomingDate());
            list.add(map);
        }
        return list;
    }

    @Override
    public  List<HashMap<String,Object>> historicTask(int id,String userName) {
        List<User> userList = new ArrayList<>();
        if(userName.equals("null")){
            userList = userMapper.selectByDepartmentId(id);
        }else if(id==-1&&!userName.equals("undefined")){
            User user = userMapper.selectByUserName(userName);
            userList.add(user);
        }else{
            return null;
        }
        List<HashMap<String,Object>> listList = new ArrayList<>();
        for (User user : userList) {
            int finished = 0;
            int unfinished = 0;
            HashMap<String,Object> map = new HashMap<>();
            List<HashMap<String,Object>> list = new ArrayList<>();
            int userId = user.getUserId();
            List<Task> taskList = processEngine.getTaskService().createTaskQuery()
                    .taskAssignee(String.valueOf(userId)).list();
            unfinished = taskList.size();

            List<HistoricTaskInstance> historicTaskInstanceList = processEngine.getHistoryService()
                    .createHistoricTaskInstanceQuery().taskAssignee(String.valueOf(userId)).finished().list();
            for (HistoricTaskInstance historicTaskInstance : historicTaskInstanceList) {
                HashMap<String,Object> map1 = new HashMap<>();
                String taskName = historicTaskInstance.getName();
                Date taskEndTime = historicTaskInstance.getEndTime();
                String processInstanceId = historicTaskInstance.getProcessInstanceId();
                HistoricProcessInstance processInstance = processEngine.getHistoryService().createHistoricProcessInstanceQuery()
                        .processInstanceId(processInstanceId).singleResult();

                    Deployment deployment = processEngine.getRepositoryService().createDeploymentQuery()
                            .deploymentId(processInstance.getDeploymentId()).singleResult();
                    String deploymentName = deployment.getName();
                    Date startTime = processInstance.getStartTime();
                    String proDefKey = processInstance.getProcessDefinitionKey();
                    if(processInstance.getEndTime()!=null){
                        map1.put("nextTaskName","已完成");
                        map1.put("nextTaskAssignee","已完成");
                        finished++;
                    }else{
                        Task task = processEngine.getTaskService().createTaskQuery().processInstanceId(processInstanceId)
                                .singleResult();
                        String nextTaskName = task.getName();
                        String nextAssignee = task.getAssignee();
                        User user1 = userMapper.selectByPrimaryKey(Integer.parseInt(nextAssignee));
                        finished++;
                        map1.put("nextTaskName",nextTaskName);
                        map1.put("nextTaskAssignee",user1.getUserName());
                    }
                    String businessKey = processInstance.getBusinessKey();
                    HashMap<String,Object> o = new HashMap<>();
                    if (proDefKey.equals("leaveBill")) {
                        LeaveNote leaveNote1 = leaveNoteMapper.selectByPrimaryKey(Integer.parseInt(businessKey));
                        o.put("creater",leaveNote1.getUserName());
                    } else if (proDefKey.equals("announcement")) {
                        Announcement announcement1 = announcementMapper.selectByPrimaryKey(Integer.parseInt(businessKey));
                        o.put("creater",announcement1.getAnnCreater());
                    }else if(proDefKey.equals("document")){
                        OutgoingMessage outgoingMessage1 = outgoingMessageMapper.selectByPrimaryKey(Integer.parseInt(businessKey));
                        o.put("creater",outgoingMessage1.getUserName());
                    }else if(proDefKey.equals("incoming")){
                        Incoming incoming = incomingMapper.selectByPrimaryKey(Integer.parseInt(businessKey));
                        o.put("creater",incoming.getUserName());
                    }
                    map1.put("deploymentName",deploymentName);
                    map1.put("taskName",taskName);
                    map1.put("taskEndTime",taskEndTime);
                    map1.put("startTime",startTime);
                    map1.put("proDefKey",proDefKey);
                    map1.put("creater",o.get("creater"));
                    list.add(map1);
            }
            map.put("unfinished",unfinished);
            map.put("finished",finished);
            map.put("list",list);
            map.put("userName",user.getUserName());
            map.put("userDepartment",user.getDepartmentName());
            listList.add(map);
        }
        return listList;
    }

}
