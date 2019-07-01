package com.design.oa.controller;

import com.alibaba.fastjson.JSONObject;
import com.design.oa.activiti.config.MyAnnListener;
import com.design.oa.activiti.config.MyExecutionListener;
import com.design.oa.activiti.vo.MyProcessDefinition;
import com.design.oa.model.AnnType;
import com.design.oa.model.Announcement;
import com.design.oa.model.Department;
import com.design.oa.model.User;
import com.design.oa.service.AnnService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

@RequestMapping("/ann")
@RestController
public class AnnController {

    @Resource
    AnnService annService;
    @Autowired
    RepositoryService repositoryService;
    @Resource
    ProcessEngine processEngine;

    @GetMapping("/anntype")
    public String annType() {
        JSONObject jsonObject = new JSONObject();
        List<AnnType> annTypeList = annService.getAnnType();
        jsonObject.put("msg", 201);
        jsonObject.put("annType", annTypeList);
        return jsonObject.toString();
    }

    @PostMapping("/announcement")
    public String createAnnouncement(@RequestBody Announcement announcement) {
        JSONObject jsonObject = new JSONObject();
        User user = null;
        Object o = SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        if (o instanceof User) {
            user = (User) o;
        }
        int annId = announcement.getAnnId()!=null?announcement.getAnnId():0;
        if(annId>0){
            announcement.setAnnId(annId);
            int state = annService.updateAnnouncement(announcement);
            ProcessInstance processInstance = processEngine.getRuntimeService().createProcessInstanceQuery()
                    .processInstanceBusinessKey(String.valueOf(annId)).singleResult();
            processEngine.getRuntimeService().setVariableLocal(processInstance.getId(),"reject_reason",null);
            jsonObject.put("msg",state);
            return jsonObject.toString();
        }
        int state = annService.createAnn(announcement);
        List<ProcessDefinition> processDefinitionLast = repositoryService.createProcessDefinitionQuery()
                .orderByProcessDefinitionVersion().desc()
                .processDefinitionKey("announcement").latestVersion().list();
        MyProcessDefinition myProcessDefinition = null;
        for (ProcessDefinition definition : processDefinitionLast) {
            myProcessDefinition = new MyProcessDefinition(definition);
        }
        String key = myProcessDefinition.getKey();
        String businessId = String.valueOf(announcement.getAnnId());
        Map<String, Object> map = new HashMap<>();
        map.put("announcement", announcement);
        map.put("start_person", user.getUserId());
        map.put("myAnnListener", new MyAnnListener());
        ProcessInstance processInstance = processEngine.getRuntimeService()
                .startProcessInstanceByKey(key, businessId, map);
        Task task = processEngine.getTaskService().createTaskQuery()
                .processInstanceId(processInstance.getId()).singleResult();
        task.setAssignee(String.valueOf(user.getUserId()));
        processEngine.getTaskService().saveTask(task);
        processEngine.getTaskService().complete(task.getId());

        jsonObject.put("msg",state);
        return jsonObject.toString();
    }

    @GetMapping("/announcement")
    public String getAnnouncement(int curPage){
        JSONObject jsonObject = new JSONObject();
        User user = new User();
        Object o = SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        if (o instanceof User) {
            user = (User) o;
        }
        PageHelper.startPage(curPage, 4);
        Set<Announcement> announcements = annService.getAnnouncement(user.getUserId());
        PageInfo<Announcement> pageList = new PageInfo<>(new ArrayList<>(announcements));
        PageHelper.clearPage();
        jsonObject.put("msg",201);
        jsonObject.put("annList",pageList);
        return jsonObject.toString();
    }

    @GetMapping("/notReadAnn")
    public String notReadAnn(int curPage){
        JSONObject jsonObject = new JSONObject();
        PageHelper.startPage(curPage, 4);
        List<Announcement> announcements = annService.getNotReadAnn();
        PageInfo<Announcement> pageList = new PageInfo<>(new ArrayList<>(announcements));
        PageHelper.clearPage();
        jsonObject.put("msg",201);
        jsonObject.put("annList",pageList);
        return jsonObject.toString();
    }
    @GetMapping("/readAnn")
    public String readAnn(int curPage){
        JSONObject jsonObject = new JSONObject();
        PageHelper.startPage(curPage, 4);
        List<Announcement> announcements = annService.getReadAnn();
        PageInfo<Announcement> pageList = new PageInfo<>(new ArrayList<>(announcements));
        PageHelper.clearPage();
        jsonObject.put("msg",201);
        jsonObject.put("annList",pageList);
        return jsonObject.toString();
    }

    @GetMapping("/overtime")
    public String overTime(int curPage){
        JSONObject jsonObject = new JSONObject();
        PageHelper.startPage(curPage, 4);
        List<Announcement> announcements = annService.getOverTimeAnn();
        PageInfo<Announcement> pageList = new PageInfo<>(new ArrayList<>(announcements));
        PageHelper.clearPage();
        jsonObject.put("msg",201);
        jsonObject.put("annList",pageList);
        return jsonObject.toString();
    }

    @PostMapping("/reader")
    public String userRead(int annId){
        JSONObject jsonObject = new JSONObject();
        int state = annService.userRead(annId);
        jsonObject.put("msg",state);
        return jsonObject.toString();
    }

    @PostMapping("/deleteAnn")
    public String deleteAnn(int annId){
        JSONObject jsonObject = new JSONObject();
        int state = annService.deleteAnn(annId);
        jsonObject.put("msg",state);
        return jsonObject.toString();
    }

    @PostMapping("/addType")
    public String addType(String typeName){
        JSONObject jsonObject = new JSONObject();
        int state = annService.addType(typeName);
        jsonObject.put("msg",state);
        return jsonObject.toString();
    }

    @PostMapping("/updateType")
    public String updateType(String typeName,int typeId){
        JSONObject jsonObject = new JSONObject();
        int state = annService.updateType(typeName,typeId);
        jsonObject.put("msg",state);
        return jsonObject.toString();
    }

    @PostMapping("/deleteType")
    public String deleteType(int typeId){
        JSONObject jsonObject = new JSONObject();
        int state = annService.deleteType(typeId);
        jsonObject.put("msg",state);
        return jsonObject.toString();
    }
}
