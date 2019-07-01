package com.design.oa.activiti.controller;

import com.alibaba.fastjson.JSONObject;
import com.design.oa.activiti.vo.DeploymentResponse;
import com.design.oa.activiti.vo.MyProcessDefinition;
import com.design.oa.common.RestServiceController;
import com.design.oa.util.ToWeb;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuruijie on 2017/4/20.
 */
@RestController
@RequestMapping("deployments")
public class DeploymentController implements RestServiceController<Deployment, String> {
    @Autowired
    RepositoryService repositoryService;

    @Override
    public Object getOne(@PathVariable("id") String id) {
        Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(id).singleResult();
        return ToWeb.buildResult().setObjData(new DeploymentResponse(deployment));
    }

    /**
     * 获取所有已部署流程
     * @param rowSize 一页数据大小
     * @param page 当前页码
     * @return
     */
    @Override
    public Object getList(@RequestParam(value = "rowSize", defaultValue = "1000", required = false) Integer rowSize, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page) {
        JSONObject jsonObject  = new JSONObject();
        PageHelper.startPage(4,page);
//        List<ProcessDefinition> processDefinitionLast = repositoryService.createProcessDefinitionQuery()
//                .orderByProcessDefinitionVersion().desc().latestVersion().list();
        List<Deployment> deploymentList = repositoryService.createDeploymentQuery().orderByDeploymenTime()
                .desc().list();
        PageInfo<Deployment> pageInfo = new PageInfo<>(deploymentList);
        List<DeploymentResponse> res = new ArrayList<>();
        pageInfo.getList().forEach(f -> {
            DeploymentResponse pf = new DeploymentResponse(f);
            res.add(pf);
        });
        PageInfo<DeploymentResponse> pageInfo1 = new PageInfo<>(res);
        PageHelper.clearPage();
        jsonObject.put("msg",201);
        jsonObject.put("deploymentList",pageInfo1);
        return jsonObject.toString();
    }


    @Override
    public Object deleteOne(@PathVariable("modelId") String id) {
        JSONObject jsonObject = new JSONObject();
        repositoryService.deleteDeployment(id,true);
        jsonObject.put("msg",201);
        return jsonObject.toString();
    }

    @PostMapping("/deleteDeploymentById")
    public Object deleteDeploymentById(@RequestParam("modelId") String id) {
        JSONObject jsonObject = new JSONObject();
        repositoryService.deleteDeployment(id,true);
        jsonObject.put("msg",201);
        return jsonObject.toString();
    }

    @Override
    public Object postOne(@RequestBody Deployment entity) {
        return null;
    }

    @Override
    public Object putOne(@PathVariable("id") String s, @RequestBody Deployment entity) {
        return null;
    }

    @Override
    public Object patchOne(@PathVariable("id") String s, @RequestBody Deployment entity) {
        return null;
    }
}
