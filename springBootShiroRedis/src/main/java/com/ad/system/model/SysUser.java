package com.ad.system.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Andylance
 * 系统用户用户
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUser implements Serializable {

    private static final long serialVersionUID = 4397314180209164600L;
    private Integer id;

    private String userName;

    private String passWord;

    private Integer userEnable;
}
