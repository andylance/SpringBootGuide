package com.ad.system.service;

import com.ad.system.model.SysUser;

import java.util.Set;

public interface UserService {
    public SysUser getUser(SysUser user);

    public Set<String> findPermissionsByUserId(Integer userId);
}
