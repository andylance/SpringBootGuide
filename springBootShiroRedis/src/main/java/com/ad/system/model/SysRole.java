package com.ad.system.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 系统用户角色
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @Override
    public java.lang.String toString() {
        return "SysRole{" +
                "id=" + id +
                ", roleDesc='" + roleDesc + '\'' +
                '}';
    }
}
