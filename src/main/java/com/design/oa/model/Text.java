package com.design.oa.model;

public class Text {
    private Integer textId;

    private Integer userId;

    private String textName;

    private String textType;

    private String textContent;

    private Integer textState;

    public Integer getTextId() {
        return textId;
    }

    public void setTextId(Integer textId) {
        this.textId = textId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTextName() {
        return textName;
    }

    public void setTextName(String textName) {
        this.textName = textName == null ? null : textName.trim();
    }

    public String getTextType() {
        return textType;
    }

    public void setTextType(String textType) {
        this.textType = textType == null ? null : textType.trim();
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent == null ? null : textContent.trim();
    }

    public Integer getTextState() {
        return textState;
    }

    public void setTextState(Integer textState) {
        this.textState = textState;
    }
}