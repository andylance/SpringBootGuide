package com.ad.system.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
/**
 * @author Andylance
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysResources implements Serializable {
    private static final long serialVersionUID = 884043233553807751L;
    private Integer id;

    private String userName;

    private String resUrl;

    private Integer userType;

    private Integer parentId;

    private Integer userSort;
}