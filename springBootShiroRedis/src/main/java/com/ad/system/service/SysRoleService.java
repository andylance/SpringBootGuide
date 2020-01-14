package com.ad.system.service;

import java.util.Set;

public interface SysRoleService {
    public Set<String> findRoleNameByUserId(Integer userId);
}
