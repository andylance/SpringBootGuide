package com.ad.system.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Andylance
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRoleResources implements Serializable {
    private static final long serialVersionUID = 6265196654259238748L;
    private Integer id;

    private Integer roleId;

    private Integer resourcesId;


}