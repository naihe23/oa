package com.design.oa.activiti.vo;

import org.activiti.engine.history.HistoricProcessInstance;

import java.util.Date;

public class MyProcessInstance {
    private String id;
    private String ProcessDefinitionId;
    private String ProcessDefinitionName;
    private String DeploymentId;
    private String BusinessKey;
    private Date startTime;
    private Date endTime;

    public String getProcessDefinitionId() {
        return ProcessDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        ProcessDefinitionId = processDefinitionId;
    }

    public String getProcessDefinitionName() {
        return ProcessDefinitionName;
    }

    public void setProcessDefinitionName(String processDefinitionName) {
        ProcessDefinitionName = processDefinitionName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDeploymentId() {
        return DeploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        DeploymentId = deploymentId;
    }

    public String getBusinessKey() {
        return BusinessKey;
    }

    public void setBusinessKey(String businessKey) {
        BusinessKey = businessKey;
    }



    public MyProcessInstance(HistoricProcessInstance instance) {
        ProcessDefinitionId = instance.getProcessDefinitionId();
        ProcessDefinitionName = instance.getProcessDefinitionName();
        startTime = instance.getStartTime();
        endTime = instance.getEndTime();
        id = instance.getId();
        DeploymentId = instance.getDeploymentId();
        BusinessKey = instance.getBusinessKey();

    }


}
