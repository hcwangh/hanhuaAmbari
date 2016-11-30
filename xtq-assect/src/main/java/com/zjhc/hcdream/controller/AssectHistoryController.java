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

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zjhc.hcdream.model.HardAssectEntityHistory_s;
import com.zjhc.hcdream.model.V_HardBroken;
import com.zjhc.hcdream.service.AssectHistoryService;
import com.zjhc.hcdream.view.Category;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.zjhc.hcdream.util.RequestUtil;

/**
 * Desc:
 * email: zhangqj@zjhcsoft.com
 * Created by ZhangQingJing on 2016/1/11 1:17
 */
@Controller
public class AssectHistoryController {

    @ResponseBody
    @RequestMapping(
            value="/ast"
            ,produces = {"application/json;charset=UTF-8"}
            ,method = RequestMethod.POST
            ,params="action=historyBrokenStat"
    )
    public String brokenInfos(ModelMap model, HttpServletRequest request) {
        Map<String, String> requestMap= RequestUtil.getMapByRequest(request);
        String page_str = requestMap.get("page");
        int page = Integer.valueOf(page_str == null ? "1" : page_str);
        String rp_str = requestMap.get("rp");
        int rp = Integer.valueOf(rp_str == null ? "1" : rp_str);
        List<V_HardBroken> extendInfos = AssectHistoryService.selectBrokenStat(new PageBounds(page, rp));
        JSONArray items = new JSONArray();
        for (V_HardBroken broken : extendInfos) {
            JSONObject item = new JSONObject();
            item.put("cell", new Object[]{
                    broken.host_ip,
                    broken.host_name,
                    broken.membrokenCount == null ? 0 : broken.membrokenCount,
                    broken.diskbrokenCount == null ?  0 : broken.diskbrokenCount
            });
            items.add(item);
        }
        JSONObject json = new JSONObject();
        json.put("page", page);
        json.put("total", ((PageList)extendInfos).getPaginator().getTotalCount());
        json.put("rows", items);
        return json.toString();
    }

    @ResponseBody
    @RequestMapping(
            value="/ast"
            ,produces = {"application/json;charset=UTF-8"}
            ,method = RequestMethod.POST
            ,params="action=historyList"
    )
    public String historyList(ModelMap model, HttpServletRequest request) {
        Map<String, String> requestMap= RequestUtil.getMapByRequest(request);
        String page_str = requestMap.get("page");
        int page = Integer.valueOf(page_str == null ? "1" : page_str);
        String rp_str = requestMap.get("rp");
        int rp = Integer.valueOf(rp_str == null ? "1" : rp_str);
        List<HardAssectEntityHistory_s> historyList = AssectHistoryService.selectEntityHistoryList(new PageBounds(page, rp));
        JSONArray items = new JSONArray();
        for (HardAssectEntityHistory_s history : historyList) {
            JSONObject item = new JSONObject();

            StringBuilder displayInfo_sb = new StringBuilder();
            if(history.category_id.equals(Category.MEM.dbId) || history.category_id.equals((Category.DISK.dbId))) {
                displayInfo_sb.append("大小: ").append(history.type_info2).append(history.type_info3)
                        .append(", 槽位号: ").append(history.belong_info1);
            }else if(history.category_id.equals(Category.CPU.dbId)){
                displayInfo_sb.append("核心数: ").append(history.type_info1)
                        .append(", 主频: ").append(history.type_info2);
            }else if(history.category_id.equals(Category.POWER.dbId)){
                displayInfo_sb.append("功率: ").append(history.type_info1);
            }else if(history.category_id.equals(Category.NETCARD.dbId)){
                displayInfo_sb.append("速率: ").append(history.type_info1);
            }else if(history.category_id.equals(Category.HOST.dbId)){
                displayInfo_sb.append("内存槽位数: ").append(history.type_info1);
                displayInfo_sb.append(", 硬盘槽位数: ").append(history.type_info2);
                displayInfo_sb.append(", 大小: ").append(history.type_info3);
                displayInfo_sb.append(", 电源数目: ").append(history.type_info4);
                displayInfo_sb.append(", 网口数目: ").append(history.type_info5);
            }

            item.put("cell", new Object[]{
                    history.host_ip,
                    history.host_name,
                    history.category_name,
                    history.type_brand,
                    history.type_version,
                    displayInfo_sb.toString(),
                    history.entity_info1,
                    history.entity_on_time,
                    history.entity_break_time
            });
            items.add(item);
        }
        JSONObject json = new JSONObject();
        json.put("page", page);
        json.put("total", ((PageList)historyList).getPaginator().getTotalCount());
        json.put("rows", items);
        return json.toString();
    }
}
