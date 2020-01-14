package com.ad.system.model;

import java.io.Serializable;

public class SysRole implements Serializable {
    private static final long serialVersionUID = 1383391516427315432L;
    private Integer id;

    private String roleDesc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc == null ? null : roleDesc.trim();
    }
}