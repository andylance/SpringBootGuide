package com.ad.system.secutity.realm;

import com.ad.system.model.SysUser;
import com.ad.system.service.SysRoleService;
import com.ad.system.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.util.Set;

/**
 * 认证领域
 */
public class AuthRealm extends AuthorizingRealm {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(AuthRealm.class);

    @Autowired
    private SysRoleService roleService;
    @Autowired
    private UserService userService;

    /**
     * 认证回调函数,登录时调用
     * 首先根据传入的用户名获取User信息；然后如果user为空，那么抛出没找到帐号异常UnknownAccountException；
     * 如果user找到但锁定了抛出锁定异常LockedAccountException；最后生成AuthenticationInfo信息，
     * 交给间接父类AuthenticatingRealm使用CredentialsMatcher进行判断密码是否匹配，
     * 如果不匹配将抛出密码错误异常IncorrectCredentialsException；
     * 另外如果密码重试此处太多将抛出超出重试次数异常ExcessiveAttemptsException；
     * 在组装SimpleAuthenticationInfo信息时， 需要传入：身份信息（用户名）、凭据（密文密码）、盐（username+salt），
     * CredentialsMatcher使用盐加密传入的明文密码和此处的密文密码进行匹配。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String name = upToken.getUsername();
        String password = String.valueOf(upToken.getPassword());
        SysUser user = new SysUser();
        user.setUserName(name);
//        user.setPassWord(password);

        SysUser userRs = userService.getUser(user);
        if (ObjectUtils.isEmpty(userRs)) {
            throw new UnknownAccountException("不存在该用户");
        }

        if (userRs.getUserEnable() != 1) {
            throw new DisabledAccountException();
        }
        // 从数据库查询出来的账号名和密码,与用户输入的账号和密码对比
        // 当用户执行登录时,在方法处理上要实现 user.login(token)
        // 然后会自动进入这个类进行认证
        // 交给 AuthenticatingRealm 使用 CredentialsMatcher 进行密码匹配，如果觉得人家的不好可以自定义实现
        // TODO 如果使用 HashedCredentialsMatcher 这里认证方式就要改一下 SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(principal, "密码", ByteSource.Util.bytes("密码盐"), getName());
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userRs, userRs.getPassWord(), getName());
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("USER_SESSION", user);
        return authenticationInfo;
    }

    /**
     * 只有需要验证权限时才会调用, 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.在配有缓存的情况下，只加载一次.
     * 如果需要动态权限,但是又不想每次去数据库校验,可以存在ehcache中.自行完善
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        logger.info("---------------- 执行 Shiro 权限获取 ---------------------");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

//        Session session = SecurityUtils.getSubject().getSession();
//        SysUser user = (SysUser) session.getAttribute("USER_SESSION");

//        Object obj = principal.getPrimaryPrincipal();
        Object obj = SecurityUtils.getSubject().getPrincipal();

        if (obj instanceof SysUser) {
            SysUser userLogin = (SysUser) obj;
            Set<String> roles = roleService.findRoleNameByUserId(userLogin.getId());
            authorizationInfo.addRoles(roles);

            Set<String> permissions = userService.findPermissionsByUserId(userLogin.getId());
            authorizationInfo.addStringPermissions(permissions);
        }

        logger.info("---- 获取到以下权限 ----");
        logger.info(authorizationInfo.getStringPermissions().toString());
        logger.info("---------------- Shiro 权限获取成功 ----------------------");
        return authorizationInfo;
    }
}
