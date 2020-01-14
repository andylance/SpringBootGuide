package com.ad.system.service.impl;

import com.ad.system.dao.SysRoleMapper;
import com.ad.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public Set<String> findRoleNameByUserId(Integer userId) {
        Set<String> userRoleNameSet = sysRoleMapper.findRoleNameByUserId(userId);
        return userRoleNameSet;
    }
}
