package com.ad.system.model;

import java.io.Serializable;

/**
 * 系统用户角色
 */
public class SysRole implements Serializable {
    private static final long serialVersionUID = 2543291254171962178L;
    /**
     * 角色ID
     */
    private Integer id;

    /**
     * 角色描述
     */
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

    @java.lang.Override
    public java.lang.String toString() {
        return "SysRole{" +
                "id=" + id +
                ", roleDesc='" + roleDesc + '\'' +
                '}';
    }
}
