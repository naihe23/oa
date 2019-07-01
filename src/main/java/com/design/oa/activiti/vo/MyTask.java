package com.design.oa.activiti.vo;

import org.activiti.engine.task.Task;

import java.util.Date;

public class MyTask {
    private String id;
    private String assignee;
    private String name;
    private Date createTime;
    private String processInstanceId;
    private String deploymentName;
    private String proDefKey;

    public String getProDefKey() {
        return proDefKey;
    }

    public void setProDefKey(String proDefKey) {
        this.proDefKey = proDefKey;
    }

    public String getDeploymentName() {
        return deploymentName;
    }

    public void setDeploymentName(String deploymentName) {
        this.deploymentName = deploymentName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public MyTask(Task task) {
        this.id = task.getId();
        this.assignee = task.getAssignee();
        this.name = task.getName();
        this.createTime = task.getCreateTime();
        this.processInstanceId = task.getProcessInstanceId();
    }

    @Override
    public String toString() {
        return "MyTask{" +
                "id='" + id + '\'' +
                ", assignee='" + assignee + '\'' +
                ", name='" + name + '\'' +
                ", createTime='" + createTime + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                '}';
    }
}
