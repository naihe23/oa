package com.design.oa.activiti.controller;

import com.alibaba.fastjson.JSONObject;
import com.design.oa.common.RestServiceController;
import com.design.oa.util.ToWeb;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by liuruijie on 2017/4/20.
 * 模型管理
 */
@RestController
@RequestMapping("models")
public class ModelerController implements RestServiceController<Model, String> {

    @Autowired
    RepositoryService repositoryService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ProcessEngine processEngine;

    /**
     * 新建一个空模型
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    @GetMapping("newModel")
    public Object newModel(@RequestParam(value = "name") String name,
                           @RequestParam(value = "description") String description,
                           @RequestParam(value = "category") String category,
                           HttpServletResponse response) throws Exception {
        //初始化一个空模型
        Model model = repositoryService.newModel();

        //设置一些默认信息
        String key = name;

        ObjectNode modelNode = objectMapper.createObjectNode();
        modelNode.put(ModelDataJsonConstants.MODEL_NAME, name);
        modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
        modelNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);

        model.setName(name);
        model.setKey(key);
        model.setMetaInfo(modelNode.toString());
        model.setCategory(category);
        repositoryService.saveModel(model);
        String id = model.getId();

        //完善ModelEditorSource
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace",
                "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);
        repositoryService.addModelEditorSource(id, editorNode.toString().getBytes("utf-8"));
        response.sendRedirect("/modeler.html?modelId=" + id);
        return "success";
    }

    /**
     * 修改流程
     *
     * @param modelId
     * @param response
     * @return
     * @throws Exception
     */
    @GetMapping("/editModel")
    public String editModel(@RequestParam(value = "modelId") String modelId,
                            HttpServletResponse response) throws Exception {
        response.sendRedirect("/modeler.html?modelId=" + modelId);
        return "success";
    }


    /**
     * 发布模型为流程定义
     *
     * @param modelId
     * @return
     * @throws Exception
     */
    @GetMapping("/deploy")
    @ResponseBody
    public String deploy(@RequestParam(value = "modelId") String modelId)
            throws Exception {
        JSONObject jsonObject = new JSONObject();
        //类别
        String category = "";

        //获取模型
        Model modelData = repositoryService.getModel(modelId);

        byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());

        if (bytes == null) {
            jsonObject.put("msg", "模型数据为空，请先设计流程并成功保存，再进行发布。");
            return jsonObject.toString();
        }

        JsonNode modelNode = new ObjectMapper().readTree(bytes);

        BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
        if (model.getProcesses().size() == 0) {
            jsonObject.put("msg", "数据模型不符要求，请至少设计一条主线流程。");
            return jsonObject.toString();
        }
        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);

        //发布流程
        String processName = modelData.getName() + ".bpmn20.xml";

        Deployment deployment = repositoryService.createDeployment().name(modelData.getName())
                .addString(processName, new String(bpmnBytes, "UTF-8"))
                .category(modelData.getCategory()).deploy();

        modelData.setDeploymentId(deployment.getId());

        repositoryService.saveModel(modelData);
        jsonObject.put("msg", 201);
        return jsonObject.toString();
    }

    @Override
    public Object getOne(@PathVariable("id") String id) {
        Model model = repositoryService.createModelQuery().modelId(id).singleResult();
        return ToWeb.buildResult().setObjData(model);
    }

    /**
     * 获得全部流程
     *
     * @param rowSize 一页数据大小
     * @param page    当前页码
     * @return
     */
    @Override
    public Object getList(@RequestParam(value = "rowSize", defaultValue = "1000", required = false) Integer rowSize, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page) {
        JSONObject jsonObject = new JSONObject();
        PageHelper.startPage(page, rowSize);
        List<Model> list = repositoryService.createModelQuery().list();
        PageInfo<Model> pageList = new PageInfo<>(list);
        PageHelper.clearPage();
        jsonObject.put("msg", 201);
        jsonObject.put("modelList", pageList);
        return jsonObject.toString();
    }

    @PostMapping("/deleteById")
    public Object deleteOne(@RequestParam("modelId") String id) {
        JSONObject jsonObject = new JSONObject();
        repositoryService.deleteModel(id);
        jsonObject.put("msg", 201);
        return jsonObject.toString();
    }

    @Override
    public Object postOne(@RequestBody Model entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object putOne(@PathVariable("id") String s, @RequestBody Model entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object patchOne(@PathVariable("id") String s, @RequestBody Model entity) {
        throw new UnsupportedOperationException();
    }


}
