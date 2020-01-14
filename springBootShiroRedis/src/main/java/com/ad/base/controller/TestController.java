package com.ad.base.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class TestController {

    @RequestMapping(value = "system")
    @ResponseBody
    public String system() {
        return "system.....";
    }

    @RequestMapping(value = "usersPage")
    @ResponseBody
    public String usersPage() {
        return "usersPage.....";
    }

    @RequestMapping(value = "rolesPage")
    @ResponseBody
    public String rolesPage() {
        return "rolesPage.....";
    }

    @RequestMapping(value = "resourcesPage")
    @ResponseBody
    public String resourcesPage() {
        return "resourcesPage.....";
    }

    @RequestMapping(value = "users/add")
    @ResponseBody
    public String usersAdd() {
        return "usersAdd.....";
    }

    @RequestMapping(value = "users/delete")
    @ResponseBody
    public String usersDelete() {
        return "usersDelete.....";
    }

    @RequestMapping(value = "roles/add")
    @ResponseBody
    public String rolesAdd() {
        return "rolesAdd.....";
    }

    @RequestMapping(value = "roles/delete")
    @ResponseBody
    public String rolesDelete() {
        return "rolesDelete.....";
    }

    @RequestMapping(value = "resources/add")
    @ResponseBody
    public String resourcesAdd() {
        return "resourcesAdd.....";
    }

    @RequestMapping(value = "resources/delete")
    @ResponseBody
    public String resourcesDelete() {
        return "resourcesDelete.....";
    }

    @RequestMapping(value = "users/saveUserRoles")
    @ResponseBody
    public String usersSaveUserRoles() {
        return "usersSaveUserRoles.....";
    }

    @RequestMapping(value = "roles/saveRoleResources")
    @ResponseBody
    public String rolesSaveRoleResources() {
        return "rolesSaveRoleResources.....";
    }

    @RequestMapping(value = "hello/hello")
    @ResponseBody
    public String helloHello() {
        return "helloHello.....";
    }
}