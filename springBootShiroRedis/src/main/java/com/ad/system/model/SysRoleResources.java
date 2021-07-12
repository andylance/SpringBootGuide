package com.ad.system.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysRoleResources implements Serializable {
    private static final long serialVersionUID = 6265196654259238748L;
    private Integer id;

    private Integer roleId;

    private Integer resourcesId;


}