package com.ad.system.dao;

import com.ad.system.model.SysRoleResources;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysRoleResourcesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRoleResources record);

    int insertSelective(SysRoleResources record);

    SysRoleResources selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRoleResources record);

    int updateByPrimaryKey(SysRoleResources record);
}