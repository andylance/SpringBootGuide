package com.ad.system.dao;

import com.ad.system.model.SysResources;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

/**
 * @author Andylance
 * 系统资源
 */
@Mapper
public interface SysResourcesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysResources record);

    int insertSelective(SysResources record);

    SysResources selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysResources record);

    int updateByPrimaryKey(SysResources record);

    Set<String> findPermissionsByUserId(Integer userId);
}