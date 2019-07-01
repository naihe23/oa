package com.design.oa.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.design.oa.activiti.config.MyDocListener;
import com.design.oa.activiti.config.MyEndIncListener;
import com.design.oa.dao.IncomingMapper;
import com.design.oa.dao.OutgoingMessageMapper;
import com.design.oa.dao.TextMapper;
import com.design.oa.model.*;
import com.design.oa.service.DocumentService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/document")
@RestController
public class DocumentController {

    @Autowired
    ProcessEngine processEngine;
    @Resource
    TextMapper textMapper;
    @Resource
    DocumentService documentService;
    @Resource
    OutgoingMessageMapper outgoingMessageMapper;
    @Resource
    IncomingMapper incomingMapper;

    @PostMapping("/text")
    public String saveText(@RequestBody Text text) {
        JSONObject jsonObject = new JSONObject();
        int state = documentService.saveText(text);
        jsonObject.put("msg", state);
        return jsonObject.toString();
    }

    @GetMapping("/texts")
    public String getTexts() {
        JSONObject jsonObject = new JSONObject();
        List<Text> texts = documentService.getTexts();
        jsonObject.put("msg", 201);
        jsonObject.put("texts", texts);
        return jsonObject.toString();
    }

    @PostMapping("/outgoing")
    public String saveOutgoing(@RequestBody OutgoingMessageDepl outgoingMessage) {
        User user = null;
        JSONObject jsonObject = new JSONObject();
        Object o = SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        if (o instanceof User) {
            user = (User) o;
        }
        int state = documentService.saveOutgoing(outgoingMessage);

        ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery()
                .deploymentId(outgoingMessage.getId()).singleResult();
        String processDefinitionId = processDefinition.getId();
        String businessKey = String.valueOf(outgoingMessage.getOutgoingMessageId());
        Map<String, Object> map = new HashMap<>();
        map.put("outgoingMessageId", outgoingMessage.getOutgoingMessageId());
        map.put("start_person", user.getUserId());
        map.put("myDocListener", new MyDocListener());
        ProcessInstance processInstance = processEngine.getRuntimeService()
                .startProcessInstanceById(processDefinitionId, businessKey, map);
        Task task = processEngine.getTaskService().createTaskQuery()
                .processInstanceId(processInstance.getId()).singleResult();
        task.setAssignee(String.valueOf(user.getUserId()));
        processEngine.getTaskService().saveTask(task);
        processEngine.getTaskService().complete(task.getId());

        jsonObject.put("msg", state);
        return jsonObject.toString();
    }

    @PostMapping("/incoming")
    public String saveIncoming(@RequestBody Incoming incoming) {
        JSONObject jsonObject = new JSONObject();
        User user = null;
        Object o = SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        if (o instanceof User) {
            user = (User) o;
        }
        int state = documentService.saveIncoming(incoming);
        ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery()
                .deploymentId(incoming.getId()).singleResult();
        String processDefinitionId = processDefinition.getId();
        String businessKey = String.valueOf(incoming.getIncomingId());
        Map<String, Object> map = new HashMap<>();
        map.put("incomingId", incoming.getIncomingId());
        map.put("start_person", user.getUserId());
        map.put("myEndIncListener", new MyEndIncListener());
        ProcessInstance processInstance = processEngine.getRuntimeService()
                .startProcessInstanceById(processDefinitionId, businessKey, map);
        Task task = processEngine.getTaskService().createTaskQuery()
                .processInstanceId(processInstance.getId()).singleResult();
        task.setAssignee(String.valueOf(user.getUserId()));
        processEngine.getTaskService().saveTask(task);
        processEngine.getTaskService().complete(task.getId());

        jsonObject.put("msg", state);
        return jsonObject.toString();
    }

    @GetMapping("/doneOutMessage")
    public String getDoneOutMessage(String deploymentId,int sign) {
        JSONObject jsonObject = new JSONObject();
        List<HashMap<String, Object>> list = documentService.getOutMessageByUserId(deploymentId,sign);
        jsonObject.put("msg", 201);
        jsonObject.put("doneOutMessages", list);
        return jsonObject.toString();
    }

    @GetMapping("/getText")
    public String getText(int id) {
        JSONObject jsonObject = new JSONObject();
        Text text = textMapper.selectByPrimaryKey(id);
        jsonObject.put("msg", 201);
        jsonObject.put("text", text);
        return jsonObject.toString();
    }

    @GetMapping("/myOutMessage")
    public String getMyOutMessage(String deploymentId,int sign) {
        JSONObject jsonObject = new JSONObject();
        if(sign==0){
            List<HashMap<String, Object>> list = documentService.getMyOutMessage(deploymentId);
            jsonObject.put("myOutMessages", list);
        }else if(sign==1){
            List<HashMap<String, Object>> list = documentService.getMyIncMessage(deploymentId);
            jsonObject.put("myOutMessages", list);
        }
        jsonObject.put("msg", 201);

        return jsonObject.toString();
    }

    @GetMapping("/searchdocument")
    public String searchDocument(@RequestParam(value = "title") String title,
                                 @RequestParam(value = "deploymentId") String deploymentId,
                                 @RequestParam(value = "outgoingMessageUrgent") int outgoingMessageUrgent,
                                 @RequestParam(value = "userId") int userId) {
        JSONObject jsonObject = new JSONObject();
        List<HashMap<String, Object>> searchResult = documentService.searchDocument(title, deploymentId, outgoingMessageUrgent, userId);
        jsonObject.put("msg", 201);
        jsonObject.put("searchResult", searchResult);
        return jsonObject.toString();
    }

    @GetMapping("/searchincoming")
    public String searchIncoming(@RequestParam(value = "title") String title,
                                 @RequestParam(value = "deploymentId") String deploymentId,
                                 @RequestParam(value = "incomingUrgent") int incomingUrgent,
                                 @RequestParam(value = "userId") int userId) {
        JSONObject jsonObject = new JSONObject();
        List<HashMap<String, Object>> searchResult = documentService.searchIncoming(title, deploymentId, incomingUrgent, userId);
        jsonObject.put("msg", 201);
        jsonObject.put("searchResult", searchResult);
        return jsonObject.toString();
    }

    @GetMapping("/documentMessage")
    public String documentMessage(String id,int sign){
        JSONObject jsonObject = new JSONObject();
        if(sign==0){
            OutgoingMessage outgoingMessage = outgoingMessageMapper.selectByPrimaryKey(Integer.parseInt(id));
            jsonObject.put("documentMessage",outgoingMessage);
        }else if(sign==1){
            Incoming incoming = incomingMapper.selectByPrimaryKey(Integer.parseInt(id));
            jsonObject.put("documentMessage",incoming);
        }
        jsonObject.put("msg",201);
        return jsonObject.toString();
    }
}
