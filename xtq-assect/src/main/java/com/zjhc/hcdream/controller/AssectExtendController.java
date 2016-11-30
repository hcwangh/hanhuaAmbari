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
import com.zjhc.hcdream.model.V_HardExtend;
import com.zjhc.hcdream.service.AssectExtendService;
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

/**
 * Desc:
 * email: zhangqj@zjhcsoft.com
 * Created by ZhangQingJing on 2016/1/11 1:17
 */
@Controller
public class AssectExtendController {

    @ResponseBody
    @RequestMapping(
            value="/ast"
            ,produces = {"application/json;charset=UTF-8"}
            ,method = RequestMethod.POST
            ,params="action=extendInfos"
    )
    public String extendInfos(ModelMap model, HttpServletRequest request) {
        Map<String, String> requestMap= RequestUtil.getMapByRequest(request);
        String page_str = requestMap.get("page");
        int page = Integer.valueOf(page_str == null ? "1" : page_str);
        String rp_str = requestMap.get("rp");
        int rp = Integer.valueOf(rp_str == null ? "1" : rp_str);
        List<V_HardExtend> extendInfos = AssectExtendService.selectV_HardExtendList(new PageBounds(page, rp));
        JSONArray items = new JSONArray();
        for (V_HardExtend extend : extendInfos) {
            JSONObject item = new JSONObject();
            Integer memCountInuse = Integer.valueOf(extend.memCountInUse == null ? "0" : extend.memCountInUse);
            Integer diskCountInuse = Integer.valueOf(extend.diskCountInUse == null ? "0" : extend.diskCountInUse);
            item.put("cell", new Object[]{
                    extend.host_ip,
                    extend.host_name,
                    memCountInuse,
                    extend.totalMemCount == null ? "<div style='color: red'>未定义主机!</div>" : Integer.valueOf(extend.totalMemCount) - memCountInuse,
                    diskCountInuse,
                    extend.totalDiskCount == null ? "<div style='color: red'>未定义主机!</div>" :  Integer.valueOf(extend.totalDiskCount) - diskCountInuse,
            });
            items.add(item);
        }
        JSONObject json = new JSONObject();
        json.put("page", page);
        json.put("total", ((PageList)extendInfos).getPaginator().getTotalCount());
        json.put("rows", items);
        return json.toString();
    }
}
