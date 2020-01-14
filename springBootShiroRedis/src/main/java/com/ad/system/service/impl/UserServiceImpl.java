package com.ad.system.service.impl;

import com.ad.system.dao.SysResourcesMapper;
import com.ad.system.dao.SysUserMapper;
import com.ad.system.model.SysUser;
import com.ad.system.service.SysRoleService;
import com.ad.system.service.UserService;
import com.ad.system.utils.UrlUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysResourcesMapper sysResourcesMapper;

    @Override
    public SysUser getUser(SysUser user) {
        List<SysUser> sysUsers = sysUserMapper.selectBySysUser(user);
        if (!ObjectUtils.isEmpty(sysUsers) && sysUsers.size() == 1) {
            return sysUsers.get(0);
        }
        return null;
    }

    @Override
    public Set<String> findPermissionsByUserId(Integer userId) {
        Set<String> permissions = sysResourcesMapper.findPermissionsByUserId(userId);
        Set<String> result = new HashSet<>();
        for (String permission : permissions) {
            if (StringUtils.isBlank(permission)) {
                continue;
            }
            permission = StringUtils.trim(permission);
            result.add(UrlUtils.getCurrentURI(permission,null));
        }
        result.add((UrlUtils.getCurrentURI("/auth/index",null)));
        return result;
    }
}
