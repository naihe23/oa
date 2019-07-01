package com.design.oa.mailModel;

public class JamesUser {
    private String userName;

    private String passwordHashAlgorithm;

    private String password;

    private Integer version;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPasswordHashAlgorithm() {
        return passwordHashAlgorithm;
    }

    public void setPasswordHashAlgorithm(String passwordHashAlgorithm) {
        this.passwordHashAlgorithm = passwordHashAlgorithm == null ? null : passwordHashAlgorithm.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}