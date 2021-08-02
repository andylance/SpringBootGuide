package com.ad.system.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 系统用户用户
 */
@Data
public class SysUser implements Serializable {

    private static final long serialVersionUID = 4397314180209164600L;
    private Integer id;

    private String userName;

    private String passWord;

    private Integer userEnable;
}
