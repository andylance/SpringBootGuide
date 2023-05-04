package com.ad.system.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *
 * @author Andylance
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserRole implements Serializable {
    private static final long serialVersionUID = 8192994587511635568L;
    private Integer id;

    private Integer userId;

    private Integer roleId;
}