package com.zjhc.hcdream.controller;

import org.apache.ambari.view.ViewContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Desc:
 * email: zhangqj@zjhcsoft.com
 * Created by ZhangQingJing on 2016/1/19 14:03
 */
@Controller
public class AssectUserController {

    /*@RequestMapping(
            value = "/ast"
            , produces = {"application/json;charset=UTF-8"}
            , method = RequestMethod.GET)
    public String getCategorySelectData(ModelMap model, HttpServletRequest request) {
        // get the view context from the servlet context
        ViewContext viewContext = (ViewContext) request.getSession().getServletContext().getAttribute(ViewContext.CONTEXT_ATTRIBUTE);

        // get the current user name from the view context
        String userName = null;
        if(viewContext != null ){
            userName =  viewContext.getUsername();
        }
        model.addAttribute("userName", userName == null ? "adsfa" : userName);
        //获取用户权限
        //http://10.80.12.54:8080/api/v1/users/humidy?fields=privileges/PrivilegeInfo/cluster_name,privileges/PrivilegeInfo/permission_name

        return "index";
    }*/
}
