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

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.zjhc.hcdream.model.*;
import com.zjhc.hcdream.service.AssectServerDetailService;
import com.zjhc.hcdream.service.AssectServerService;
import com.zjhc.hcdream.util.DateUtil;
import com.zjhc.hcdream.util.RequestUtil;
import com.zjhc.hcdream.view.Category;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Desc:
 * email: zhangqj@zjhcsoft.com
 * Created by ZhangQingJing on 2016/1/6 16:27
 */
@Controller
public class AssectServersDetailController {

    @ResponseBody
    @RequestMapping(
            value="/ast"
            ,produces = {"application/json;charset=UTF-8"}
            ,method = RequestMethod.POST
            ,params="action=getReadTextData")
    public String getReadTextData(ModelMap model,@RequestParam("brand_value") String brand_value,@RequestParam("version_value") String version_value) {
        HardAssectType type = AssectServerDetailService.selectHardAssectType(brand_value, version_value);
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(type.getType_info1());
        jsonArray.add(type.getType_info2());
        jsonArray.add(type.getType_info3());
        jsonArray.add(type.getType_info4());
        jsonArray.add(type.getType_info5());
        jsonObject.put("type_id", type.getType_id());
        jsonObject.put("valueArr", jsonArray);
        return jsonObject.toString();
    }

    @ResponseBody
    @RequestMapping(
            value="/ast"
            ,produces = {"application/json;charset=UTF-8"}
            ,method = RequestMethod.POST
            ,params="action=getSelectLevel2Data")
    public String getSelectLevel2Data(ModelMap model,@RequestParam("category_id") String category_id,
                                      @RequestParam("brand_value") String brand_value) {
        List<String> versionList = AssectServerDetailService.selectVersionList(category_id, brand_value);
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (String version : versionList) {
            jsonArray.add(version);
        }
        jsonObject.put("valueArr", jsonArray);
        return jsonObject.toString();
    }

    @ResponseBody
    @RequestMapping(
            value="/ast"
            ,produces = {"application/json;charset=UTF-8"}
            ,method = RequestMethod.POST
            ,params="action=updateAssectEntity")
    public String updateAssectEntity(ModelMap mode,HttpServletRequest request) {
        boolean result = false;
        String errorMsg  = null;
        try{
                //TODO 增加事务 test
                Map<String, String> reqMap= RequestUtil.getMapByRequest(request);
                HardAssectEntity entity = new HardAssectEntity();
                String entity_id = reqMap.get("entity_id");
                String type_id= reqMap.get("type_id");
                String entity_info1= reqMap.get("1");
                String entity_info2= reqMap.get("2");
                String entity_info3= reqMap.get("3");
                String belong_info1= reqMap.get("b1");
                String machine_id= reqMap.get("machine_id");

                entity.entity_id = entity_id;
                entity.type_id = type_id;
                entity.entity_info1 = entity_info1;
                entity.entity_info2 = entity_info2;
                entity.entity_info3 = entity_info3;

                result = AssectServerDetailService.updateHardAssectEntity(entity, machine_id, belong_info1);
                /*result = AssectServerDetailService.updateHardAssectEntity(entity);
                if(machine_id != null) {
                    HardAssectMachineEntity mchety = new HardAssectMachineEntity();
                    mchety.machine_entity_id = machine_id;
                    mchety.entity_id = entity_id;
                    mchety.belong_info1 = belong_info1;
        //            mchety.entity_on_time = DateUtil.newDateStr();
                    result  = result && AssectServerDetailService.updateMachineEty(mchety);
                }*/
        }catch (Exception e){
            e.printStackTrace();
            errorMsg = e.getMessage();
        }
    return result ? "1" : errorMsg;
    }

    @ResponseBody
    @RequestMapping(
            value="/ast"
            ,produces = {"application/json;charset=UTF-8"}
            ,method = RequestMethod.POST
            ,params="action=newHost")
    public String newHost(ModelMap mode,HttpServletRequest request) {

        Map<String, String> reqMap= RequestUtil.getMapByRequest(request);
        String host_id = reqMap.get("host_id");
        String category_id = reqMap.get("category_id");
        String type_id = reqMap.get("type_id");
        String entity_info1 = reqMap.get("1");
        String entity_info2 = reqMap.get("2");
        String entity_info3 = reqMap.get("3");
        String machineEntityId = null;
        try {
                machineEntityId = AssectServerDetailService.newHost(host_id, category_id, type_id, entity_info1, entity_info2, entity_info3);
        }catch (Exception e){
            e.printStackTrace();
        }
        JSONObject object = new JSONObject();
        object.put("result", (machineEntityId!=null && !machineEntityId.equals("")) ? "1" : "error");
        object.put("machine_id", machineEntityId);
        return object.toString();
    }

    @ResponseBody
    @RequestMapping(
            value="/ast"
            ,produces = {"application/json;charset=UTF-8"}
            ,method = RequestMethod.POST
            ,params="action=newEntityToHost")
    public String newEntityToHost(ModelMap mode,HttpServletRequest request) {
        boolean result = false;
        String errorMsg  = null;
        try{
            Map<String, String> reqMap= RequestUtil.getMapByRequest(request);
            String machine_id = reqMap.get("machine_id");
            String category_id = reqMap.get("category_id");
            String type_id = reqMap.get("type_id");
            String entity_info1 = reqMap.get("1");
            String entity_info2 = reqMap.get("2");
            String entity_info3 = reqMap.get("3");
            String belong_info1 = reqMap.get("b1");
            result = AssectServerDetailService.newEntityToHost(machine_id, category_id, type_id, entity_info1, entity_info2, entity_info3,belong_info1);
        }catch (Exception e){
            e.printStackTrace();
            errorMsg = e.getMessage();
        }
        return result ? "1" : errorMsg;
    }


    @ResponseBody
    @RequestMapping(
            value="/ast"
            ,produces = {"application/json;charset=UTF-8"}
            ,method = RequestMethod.POST
            ,params="action=expireEntity")
    public String expireEntity(ModelMap mode,HttpServletRequest request) {
        boolean result = false;
        String errorMsg  = null;
        try{
            Map<String, String> reqMap= RequestUtil.getMapByRequest(request);
            String host_id = reqMap.get("host_id");
            String machine_id = reqMap.get("machine_id");
            String entity_id = reqMap.get("entity_id");
            if(host_id != null && machine_id != null && entity_id != null){
                result = AssectServerDetailService.expireEntity(host_id, machine_id, entity_id);
            }
        }catch (Exception e){
            e.printStackTrace();
            errorMsg = e.getMessage();
        }
        return result ? "1" : errorMsg;
    }


    @ResponseBody
    @RequestMapping(
            value="/ast"
            ,produces = {"application/json;charset=UTF-8"}
            ,method = RequestMethod.POST
            ,params="action=server_detail_mem_list"
    )
    public String server_detail_mem_list(ModelMap model, HttpServletRequest request) {
        Map<String, String> requestMap= RequestUtil.getMapByRequest(request);
        String host_id = requestMap.get("host_id");
//        System.out.println("host_id:  " + host_id);
       /* String page_str = requestMap.get("page");
        int page = Integer.valueOf(page_str == null ? "1" : page_str);
        String rp_str = requestMap.get("rp");
        int rp = Integer.valueOf(rp_str == null ? "50" : rp_str);
        PageBounds page = new PageBounds(page, rp);*/
        PageBounds pageBounds = new PageBounds();
        List<V_HardDetails> mem_list =
                AssectServerDetailService.selectHardDetailList(host_id, Category.MEM.dbId, pageBounds);
        JSONArray items = new JSONArray();
        if(mem_list != null && mem_list.size() != 0 && mem_list.get(0).machine_entity_id != null) {
            for (V_HardDetails detail : mem_list) {
                JSONObject item = new JSONObject();
                //            machine_id entity_id 插槽序号 品牌 型号 大小 序列号  上线时间
                item.put("cell", new Object[]{
                        //                    detail.host_id,
                        detail.entity_id,
                        detail.type_id,
                        detail.belong_info1,
                        detail.type_brand,
                        detail.type_version,
                        detail.type_info2 + detail.type_info3,
                        detail.entity_info1,
                        detail.entity_on_time
                });
                items.add(item);
            }
        }
        JSONObject json = new JSONObject();
//        json.put("page", page);
//        json.put("total", ((PageList)servers).getPaginator().getTotalCount());
        json.put("rows", items);
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(
            value="/ast"
            ,produces = {"application/json;charset=UTF-8"}
            ,method = RequestMethod.POST
            ,params="action=server_detail_disk_list"
    )
    public String server_detail_disk_list(ModelMap model, HttpServletRequest request) {
        Map<String, String> requestMap= RequestUtil.getMapByRequest(request);
        String host_id = requestMap.get("host_id");
//        System.out.println(" from disk :  host_id:  " + host_id);
       /* String page_str = requestMap.get("page");
        int page = Integer.valueOf(page_str == null ? "1" : page_str);
        String rp_str = requestMap.get("rp");
        int rp = Integer.valueOf(rp_str == null ? "50" : rp_str);
        PageBounds page = new PageBounds(page, rp);*/
        PageBounds pageBounds = new PageBounds();
        List<V_HardDetails> disk_list =
                AssectServerDetailService.selectHardDetailList(host_id,Category.DISK.dbId,pageBounds);
        JSONArray items = new JSONArray();
        if(disk_list != null && disk_list.size() != 0 && disk_list.get(0).machine_entity_id != null) {
            for (V_HardDetails detail : disk_list) {
                JSONObject item = new JSONObject();
                //            machine_id entity_id 插槽序号 品牌 型号 大小 序列号  上线时间
                item.put("cell", new Object[]{
                        //                    detail.host_id,
                        detail.entity_id,
                        detail.type_id,
                        detail.belong_info1,
                        detail.type_brand,
                        detail.type_version,
                        detail.type_info2 + detail.type_info3,
                        detail.entity_info1,
                        detail.entity_on_time
                });
                items.add(item);
            }
        }
        JSONObject json = new JSONObject();
//        json.put("page", page);
//        json.put("total", ((PageList)servers).getPaginator().getTotalCount());
        json.put("rows", items);
        return json.toString();
    }
}
