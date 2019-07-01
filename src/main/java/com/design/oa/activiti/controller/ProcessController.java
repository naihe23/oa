package com.design.oa.activiti.controller;


import com.alibaba.fastjson.JSONObject;
import com.design.oa.activiti.vo.DeploymentResponse;
import com.design.oa.activiti.vo.MyProcessDefinition;
import com.design.oa.activiti.vo.MyProcessInstance;
import com.design.oa.activiti.vo.MyTask;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/process")
public class ProcessController {

    @Autowired
    ProcessEngine processEngine;

    /**
     * 根据id启动流程
     * @param keyName
     * @return
     */
    @PostMapping("/startProcess")
    public String startProcess(String keyName){
        JSONObject jsonObject = new JSONObject();
        ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceById(keyName);
        jsonObject.put("msg",201);
        return jsonObject.toString();
    }

    /**
     * 获取所有已启动的流程实例
     * @param page
     * @param rowSize
     * @return
     */
    @GetMapping("/processList")
    public String processList(@RequestParam(value = "page",defaultValue = "1",required = false) int page,
                              @RequestParam(value = "rowSize",defaultValue = "4",required = false) int rowSize){
        JSONObject jsonObject = new JSONObject();
        PageHelper.startPage(rowSize,page);
        List<HistoricProcessInstance> list = processEngine.getHistoryService().createHistoricProcessInstanceQuery().notDeleted().list();
       // List<Execution> list1 = processEngine.getRuntimeService().createExecutionQuery().list();
        PageInfo<HistoricProcessInstance> pageList = new PageInfo<>(list);
        List<MyProcessInstance> res = new ArrayList<>();
        pageList.getList().forEach(f->{
            MyProcessInstance myProcessInstance = new MyProcessInstance(f);
            res.add(myProcessInstance);
        });
        PageInfo<MyProcessInstance> pageInfo = new PageInfo<>(res);
        PageHelper.clearPage();
        jsonObject.put("msg",201);
        jsonObject.put("processList",pageInfo);
        return jsonObject.toString();
    }

    @PostMapping("/deleteProcess")
    public String deleteProcess(String processId){
        JSONObject jsonObject = new JSONObject();
        processEngine.getRuntimeService().deleteProcessInstance(processId,"结束");
        jsonObject.put("msg",201);
        return jsonObject.toString();
    }


    @GetMapping("/docDeployments")
    public String getDocWorkflow(int sign){
        JSONObject jsonObject = new JSONObject();
        List<Deployment> deploymentList = null;
        RepositoryService repositoryService = processEngine.getRepositoryService();
        if(sign == 0){
            deploymentList = repositoryService.createDeploymentQuery().orderByDeploymenTime()
                    .desc().processDefinitionKeyLike("document").list();
        }else if(sign == 1){
            deploymentList = repositoryService.createDeploymentQuery().orderByDeploymenTime()
                    .desc().processDefinitionKeyLike("incoming").list();
        }
        List<DeploymentResponse> res = new ArrayList<>();
        deploymentList.forEach(f -> {
            DeploymentResponse myTask = new DeploymentResponse(f);
            res.add(myTask);
        });
        jsonObject.put("msg",201);
        jsonObject.put("docDeploymenys",res);
        return jsonObject.toString();
    }
}
