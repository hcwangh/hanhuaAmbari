/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zjhc.hcdream.controller;

//  在SpringMVC 中，控制器Controller 负责处理由DispatcherServlet 分发的请求，
// 它把用户请求的数据经过业务处理层处理之后封装成一个Model ，然后再把该Model 返回给对应的View 进行展示
// 参考@ResponseBody
import com.zjhc.hcdream.model.HardAssectCategory;
import com.zjhc.hcdream.model.HardAssectType;
import com.zjhc.hcdream.service.AssectCategoryService;
import com.zjhc.hcdream.service.SequenceService;
import com.zjhc.hcdream.util.RequestUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

// 就是一个SpringMVC Controller 对象了
@Controller
public class AssectCategoryController {
    @ResponseBody
    @RequestMapping(
            value = "/ast"
            , produces = {"application/json;charset=UTF-8"}
            , method = RequestMethod.POST
            , params = "action=category"
    )
    //   public String selectServers(ModelMap model, HttpServletRequest request) {
    public String selectCategory(ModelMap model, HttpServletRequest request) {
        Map<String, String> requestMap = RequestUtil.getMapByRequest(request);
       /* String page_str = requestMap.get("page");
        int page = Integer.valueOf(page_str == null ? "1" : page_str);
        String rp_str = requestMap.get("rp");
        int rp = Integer.valueOf(rp_str==null ? "1": rp_str);*/
//        List<HardAssectCategory> servers = AssectCategoryService.selectAsssectCategory(new PageBounds(page, rp));
        List<HardAssectCategory> servers = AssectCategoryService.selectAsssectCategory();
        JSONArray items = new JSONArray();
        for (HardAssectCategory server : servers) {
            JSONObject item = new JSONObject();
            Object cell = item.put("cell", new Object[]{
                    server.getCategory_id(),
                    server.getCategory_name(),});
            items.add(item);
        }
        JSONObject json = new JSONObject();
//        json.put("page", page);
//        json.put("total", ((PageList)servers).getPaginator().getTotalCount());
        json.put("rows", items);
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(
            value = "/ast"
            , produces = {"application/json;charset=UTF-8"}
            , method = RequestMethod.POST
            , params = "action=type"
    )
    //   public String selectServers(ModelMap model, HttpServletRequest request) {
    public String selectType(ModelMap model, HttpServletRequest request) {
        Map<String, String> requestMap = RequestUtil.getMapByRequest(request);
        List<HardAssectType> servers = AssectCategoryService.selectAsssectType();
        JSONArray items = new JSONArray();
        for (HardAssectType server : servers) {
            JSONObject item = new JSONObject();
            Object cell = item.put("cell", new Object[]{
                    server.getCategory_name(),
                    server.getType_id(),
                    server.getType_brand() + server.getType_version(),
                    server.getCategory_id(),
                    server.getType_brand(),
                    server.getType_version(),
                    server.getType_info1(),
                    server.getType_info2(),
                    server.getType_info3(),
                    server.getType_info4(),
                    server.getType_info5(),
                    server.getReference_price(),
            });
            items.add(item);
        }
        JSONObject json = new JSONObject();
//        json.put("page", page);
//        json.put("total", ((PageList)servers).getPaginator().getTotalCount());
        json.put("rows", items);
        return json.toString();
    }


    @ResponseBody
    @RequestMapping(
            value = "/ast"
            , produces = {"application/json;charset=UTF-8"}
            , method = RequestMethod.POST
            , params = "action=addtion")
    public String addtion(ModelMap mode, HttpServletRequest request) {
        Map<String, String> reqMap = RequestUtil.getMapByRequest(request);
        String category_name = reqMap.get("category_name");
        String category_id = SequenceService.nextSeq_category_id();
        boolean result = AssectCategoryService.newCategory(category_id, category_name);
        return result ? "1" : "error";
    }


    @ResponseBody
    @RequestMapping(
            value = "/ast"
            , produces = {"application/json;charset=UTF-8"}
            , method = RequestMethod.POST
            , params = "action=newType")
    public String newType(ModelMap model, HttpServletRequest request) {
        Map<String, String> requestMap = RequestUtil.getMapByRequest(request);
        String type_name = requestMap.get("type_name");
        String type_brand = requestMap.get("type_brand");
        String type_version = requestMap.get("type_version");
        String category_id = requestMap.get("category_id");
        String type_id = SequenceService.nextSeq_type_id();
        String type_info1= requestMap.get("type_info1");
        String type_info2= requestMap.get("type_info2");
        String type_info3= requestMap.get("type_info3");
        String type_info4= requestMap.get("type_info4");
        String type_info5= requestMap.get("type_info5");
  //      String reference_price= requestMap.get("reference_price");

        HardAssectType newtype = new HardAssectType();
  //      server.setHost_id(SequenceService.nextSeq_mhost_id());
        newtype.setType_brand(type_brand);
        newtype.setType_name(type_name);
        newtype.setCategory_id(category_id);
    //    newtype.setReference_price(reference_price);
        newtype.setType_id(type_id);
        newtype.setType_info1(type_info1);
        newtype.setType_info2(type_info2);
        newtype.setType_info3(type_info3);
        newtype.setType_info4(type_info4);
        newtype.setType_info5(type_info5);
        newtype.setType_version(type_version);
        //String result = AssectServerService.newHostServer(server);
        String result = AssectCategoryService.newType(newtype);
        System.out.println(result);
        return result;
    }

    @ResponseBody
    @RequestMapping(
            value = "/ast"
            , produces = {"application/json;charset=UTF-8"}
            , method = RequestMethod.POST
            , params = "action=changeType")
    public String changeType(ModelMap model, HttpServletRequest request) {
        Map<String, String> requestMap = RequestUtil.getMapByRequest(request);
        String type_name = requestMap.get("type_name");
        String type_brand = requestMap.get("type_brand");
        String type_version = requestMap.get("type_version");
        String category_id = requestMap.get("category_id");
        String type_id = requestMap.get("type_id");
        String type_info1= requestMap.get("type_info1");
        String type_info2= requestMap.get("type_info2");
        String type_info3= requestMap.get("type_info3");
        String type_info4= requestMap.get("type_info4");
        String type_info5= requestMap.get("type_info5");
        System.out.println(type_name);
        System.out.println(type_brand);
        System.out.println(type_id);
        //      String reference_price= requestMap.get("reference_price");

        HardAssectType changetype = new HardAssectType();
        //      server.setHost_id(SequenceService.nextSeq_mhost_id());
        changetype.setType_brand(type_brand);
        changetype.setType_name(type_name);
        changetype.setCategory_id(category_id);
        //    changetype.setReference_price(reference_price);
        changetype.setType_id(type_id);
        changetype.setType_info1(type_info1);
        changetype.setType_info2(type_info2);
        changetype.setType_info3(type_info3);
        changetype.setType_info4(type_info4);
        changetype.setType_info5(type_info5);
        changetype.setType_version(type_version);
        System.out.println(changetype.type_id);
        //String result = AssectServerService.newHostServer(server);
        String result = AssectCategoryService.changeType(changetype);
        System.out.println(result);
        return result;
    }



    @ResponseBody
    @RequestMapping(
            value = "/ast"
            , produces = {"application/json;charset=UTF-8"}
            , method = RequestMethod.POST
            , params = "action=getCategorySelectData")
    public String getCategorySelectData(ModelMap model, HttpServletRequest request) {
        List<HardAssectCategory> lists = AssectCategoryService.selectAsssectCategory();
        JSONArray categoryArray = new JSONArray();
        for (HardAssectCategory list: lists ){
            JSONObject object = new JSONObject();
            object.put("category_id",list.category_id);
            object.put("category_name",list.category_name);
            categoryArray.add(object);
        }
        return categoryArray.toString();
}




///返回类型需要的数据
    @ResponseBody
    @RequestMapping(
            value = "/ast"
            , produces = {"application/json;charset=UTF-8"}
            , method = RequestMethod.POST
            , params = "action=getTypeSelectData")
    public String getTypeNameSelectData(ModelMap model, HttpServletRequest request) {
        List<HardAssectType> lists = AssectCategoryService.selectAsssectType();
        JSONArray categoryArray = new JSONArray();
        for (HardAssectType list: lists ){
            JSONObject object = new JSONObject();
            object.put("category_id",list.category_id);
            object.put("type_id",list.type_id);
            object.put("type_name",list.type_name);
            object.put("type_brand",list.type_brand);
            object.put("type_version",list.type_version);
            object.put("type_info1",list.type_info1);
            object.put("type_info2",list.type_info2);
            object.put("type_info3",list.type_info3);
            object.put("type_info4",list.type_info4);
            object.put("type_info5",list.type_info5);
            object.put("reference_price",list.reference_price);
            categoryArray.add(object);
        }
        return categoryArray.toString();
    }
}
