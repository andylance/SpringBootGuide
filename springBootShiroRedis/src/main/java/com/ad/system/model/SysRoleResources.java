package com.ad.system.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ting x
 * 系统角色资源
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRoleResources implements Serializable {

    private static final long serialVersionUID = 7419435804582489930L;
    private Integer id;

    private Integer roleId;

    private Integer resourcesId;


}