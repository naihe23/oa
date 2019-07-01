package com.design.oa.activiti.vo;

import org.activiti.engine.history.HistoricTaskInstance;

public class MyHistoricTaskInstance {

    private String id;

    private String name;


    public MyHistoricTaskInstance(HistoricTaskInstance task) {
        this.id = task.getId();
        this.name = task.getName();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
