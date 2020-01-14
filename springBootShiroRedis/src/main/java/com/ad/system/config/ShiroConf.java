package com.ad.system.config;

import com.ad.system.secutity.cache.RedisCacheManager;
import com.ad.system.secutity.filter.AnonFilter;
import com.ad.system.secutity.filter.KickoutSessionControlFilter;
import com.ad.system.secutity.filter.PermissionAuthcFilter;
import com.ad.system.secutity.realm.AuthRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro 配置
 */
@Configuration
public class ShiroConf {
    private static final Logger log = LoggerFactory.getLogger(ShiroConf.class);

    @Bean(name = "authRealm")
    public AuthRealm authRealm(RedisCacheManager redisCacheManager) {
        AuthRealm authRealm = new AuthRealm();
        authRealm.setCacheManager(redisCacheManager);
        return authRealm;
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(AuthRealm authRealm, DefaultWebSessionManager sessionManager, RedisCacheManager redisCacheManager) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(authRealm);
        // <!-- 用户授权/认证信息Cache, 采用EhCache 缓存 -->
        defaultWebSecurityManager.setCacheManager(redisCacheManager);
        defaultWebSecurityManager.setSessionManager(sessionManager);
        return defaultWebSecurityManager;
    }

    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    @Bean
    public RedisCacheManager redisCacheManager(RedisManager redisManager) {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager);
        return redisCacheManager;
    }

    /**
     * 配置shiro redisManager
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    @Bean
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost("localhost:6379");
//        redisManager.setPort(6379);
//        redisManager.setExpire(1800);// 配置缓存过期时间
        redisManager.setTimeout(0);
        return redisManager;
    }

    /**
     * Session Manager
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public DefaultWebSessionManager sessionManager(RedisSessionDAO redisSessionDAO) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO);
        return sessionManager;
    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO(RedisManager redisManager) {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager);
        return redisSessionDAO;
    }

    /***
     * 使授权注解起作用不如不想配置可以在pom文件中加入
     * <dependency>
     *<groupId>org.springframework.boot</groupId>
     *<artifactId>spring-boot-starter-aop</artifactId>
     *</dependency>
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /***
     * 授权所用配置
     *
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    @Bean
    public RedisCacheManager cacheManager(RedisManager redisManager) {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager);
        return redisCacheManager;
    }

    /**
     * 限制同一账号登录同时登录人数控制
     *
     * @return
     */
    @Bean
    public KickoutSessionControlFilter kickoutSessionControlFilter(RedisCacheManager cacheManager, DefaultWebSessionManager sessionManager) {
        KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
        kickoutSessionControlFilter.setCacheManager(cacheManager);
        kickoutSessionControlFilter.setSessionManager(sessionManager);
        kickoutSessionControlFilter.setKickoutUrl("/auth/kickout");
        return kickoutSessionControlFilter;
    }

    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * ShiroFilter<br/>
     * 注意这里参数中的 StudentService 和 IScoreDao 只是一个例子，因为我们在这里可以用这样的方式获取到相关访问数据库的对象，
     * 然后读取数据库相关配置，配置到 shiroFilterFactoryBean 的访问规则中。实际项目中，请使用自己的Service来处理业务逻辑。
     *
     * @param securityManager 安全管理器
     * @return ShiroFilterFactoryBean
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager, KickoutSessionControlFilter kickoutSessionControlFilter, PermissionAuthcFilter permissionAuthcFilter, AnonFilter anonFilter) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("permissionAuthc", permissionAuthcFilter);
        filterMap.put("anonAuthc", anonFilter);
        filterMap.put("kickout", kickoutSessionControlFilter);
        shiroFilterFactoryBean.setFilters(filterMap);

        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 如果不设置默认会自动寻找Web工程根目录下的"/login"页面
        shiroFilterFactoryBean.setLoginUrl("/auth/initLogin");
        // 登录成功后要跳转的连接
        shiroFilterFactoryBean.setSuccessUrl("/auth/index");
        shiroFilterFactoryBean.setUnauthorizedUrl("/auth/denied");
        loadShiroFilterChain(shiroFilterFactoryBean);
        return shiroFilterFactoryBean;
    }

    @Bean
    public FilterRegistrationBean delegatingFilterProxy(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("shiroFilter");
        filterRegistrationBean.setFilter(proxy);
        return filterRegistrationBean;
    }

    @Bean(name = "permissionAuthcFilter")
    public PermissionAuthcFilter permissionAuthcFilter() {
        PermissionAuthcFilter permissionAuthcFilter = new PermissionAuthcFilter();
        permissionAuthcFilter.setUnauthorizedUrl("/auth/denied");
        return permissionAuthcFilter;
    }

    @Bean(name = "anonFilter")
    public AnonFilter anonFilter() {
        AnonFilter anonFilter = new AnonFilter();
        return anonFilter;
    }

    /**
     * 加载shiroFilter权限控制规则（从数据库读取然后配置）
     */
    private void loadShiroFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean) {
        /////////////////////// 下面这些规则配置最好配置到配置文件中 ///////////////////////
        // TODO 重中之重啊，过滤顺序一定要根据自己需要排序
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        /**
         * 需要验证登录的写 authc 不需要的写 anon
         * 不做权限校验写permissionAuthc[false], 因为配置了所有路径都会经过permissionAuthc的过滤器
         * anon：它对应的过滤器里面是空的,什么都没做
         */
        filterChainDefinitionMap.put("/css/**", "anonAuthc");
        filterChainDefinitionMap.put("/js/**", "anonAuthc");
        filterChainDefinitionMap.put("/img/**", "anonAuthc");
        filterChainDefinitionMap.put("/auth/login", "anonAuthc");
        filterChainDefinitionMap.put("/auth/initLogin", "anonAuthc");
        filterChainDefinitionMap.put("/auth/logout", "logout");
        filterChainDefinitionMap.put("/auth/kickout", "anonAuthc");
        filterChainDefinitionMap.put("/auth/denied", "anonAuthc");
        filterChainDefinitionMap.put("/hello/hello", "anonAuthc");
        filterChainDefinitionMap.put("/favicon.ico", "anonAuthc");
//        filterChainDefinitionMap.put("/auth/index", "authc");

        log.info("##################从数据库读取权限规则，加载到shiroFilter中##################");

        // 做权限校验写permissionAuthc
        filterChainDefinitionMap.put("/**", "authc,permissionAuthc,kickout");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    }
}
