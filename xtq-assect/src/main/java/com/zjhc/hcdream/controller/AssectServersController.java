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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.zjhc.hcdream.model.V_HardDetails;
import com.zjhc.hcdream.model.V_HardServerSummary;
import com.zjhc.hcdream.model.XTQAmbariServer;
import com.zjhc.hcdream.service.AssectServerService;
import com.zjhc.hcdream.service.SequenceService;
import com.zjhc.hcdream.util.RequestUtil;
import com.zjhc.hcdream.view.Category;
import com.zjhc.hcdream.view.ServerDetailWindow;
import com.zjhc.hcdream.view.WindowManager;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class AssectServersController {

    @RequestMapping(
            value="/ast"
            ,produces = {"application/json;charset=UTF-8"}
            ,method = RequestMethod.POST
            ,params="action=server_detail")
    public String redictToServerDetail(ModelMap model,@RequestParam("host_id") String host_id) {
        ArrayList<ServerDetailWindow> windows = WindowManager.newDetailWindows();
        V_HardDetails parentDetail = AssectServerService.selectHardDetailHost(host_id, Category.HOST.dbId);
        windows.add(WindowManager.newHostWindow(host_id, Category.HOST.nameId, Category.HOST.dbId,parentDetail));
        windows.add(WindowManager.newMemWindow(host_id, Category.MEM.nameId, Category.MEM.dbId, parentDetail));
        windows.add(WindowManager.newDiskWindow(host_id, Category.DISK.nameId, Category.DISK.dbId, parentDetail));
        windows.add(WindowManager.newCpuWindow(host_id, Category.CPU.nameId, Category.CPU.dbId,parentDetail));
        windows.add(WindowManager.newNetCardWindow(host_id, Category.NETCARD.nameId, Category.NETCARD.dbId,parentDetail));
        windows.add(WindowManager.newPowerWindow(host_id, Category.POWER.nameId, Category.POWER.dbId,parentDetail));
//        List<String> memBrandList = AssectServerDetailService.selectBrandList(Category.MEM.dbId);
//        List<String> diskBrandList = AssectServerDetailService.selectBrandList(Category.DISK.dbId);
        model.addAttribute("windows", windows);
        model.addAttribute("this_host_id", host_id);
        model.addAttribute("this_machine_id", parentDetail.machine_entity_id);
//        model.addAttribute("memBrandList", memBrandList);
//        model.addAttribute("diskBrandList", diskBrandList);
        return "server_detail";
    }

    @ResponseBody
    @RequestMapping(
            value="/ast"
            ,produces = {"application/json;charset=UTF-8"}
            ,method = RequestMethod.POST
            ,params="action=refreshSlaves")
    public String refreshSlaves(ModelMap model) {
        String result = AssectServerService.refreshSlaves();
        return result ;
    }

    @ResponseBody
    @RequestMapping(
            value="/ast"
            ,produces = {"application/json;charset=UTF-8"}
            ,method = RequestMethod.POST
            ,params="action=newHost_m")
    public String newHost_m(ModelMap model,HttpServletRequest request) {
        Map<String, String> requestMap= RequestUtil.getMapByRequest(request);
        String ip = requestMap.get("ip");
        String hostname = requestMap.get("hostname");

        XTQAmbariServer server = new XTQAmbariServer();
        server.setHost_id(SequenceService.nextSeq_mhost_id());
        server.setHost_ip(ip);
        server.setHost_name(hostname);
        String result = AssectServerService.newHostServer(server);
        return result ;
    }


    @ResponseBody
    @RequestMapping(
            value="/ast"
            ,produces = {"application/json;charset=UTF-8"}
            ,method = RequestMethod.POST
            ,params="action=expireHost")
    public String expireHost(ModelMap model,HttpServletRequest request) {
        Map<String, String> requestMap= RequestUtil.getMapByRequest(request);
        String host_id = requestMap.get("host_id");
        boolean result = false;
        String errorMsg  = null;
        try {
            result = AssectServerService.expireHost(host_id);
        } catch (Exception e) {
            errorMsg = e.getMessage();
            e.printStackTrace();
        }
        return result ? "1" : errorMsg;
    }


    @ResponseBody
    @RequestMapping(
            value="/ast"
            ,produces = {"application/json;charset=UTF-8"}
            ,method = RequestMethod.POST
            ,params="action=servers"
    )
    public String selectServers(ModelMap model, HttpServletRequest request) {
        Map<String, String> requestMap= RequestUtil.getMapByRequest(request);
        String page_str = requestMap.get("page");
        int page = Integer.valueOf(page_str == null ? "1" : page_str);
        String rp_str = requestMap.get("rp");
        int rp = Integer.valueOf(rp_str==null ? "1": rp_str);
            List<V_HardServerSummary> servers = AssectServerService.selectServerSummary(new PageBounds(page, rp));
            JSONArray items = new JSONArray();
            for (V_HardServerSummary server : servers) {
                boolean isExistHostEntity = (server.getMachine_entity_id() != 0) ;
                JSONObject item = new JSONObject();
                item.put("cell", new Object[]{
                        server.getHost_id(),
                        server.getHost_ip(),
                        server.getHost_name(),
                        server.getRole(),
                        server.getMem(),
                        server.getDisk(),
                        server.getCpu_cores(),
                        server.getPrincipal_info(),
                        isExistHostEntity ? "<a href='#' onclick=\"to_server_detail('"+server.getHost_id()+"');\" style='color:blue'>主机详情</a>"
                                : "<a href='#' onclick=\"to_server_detail('"+server.getHost_id()+"');\" style='font-weight:bold;color:red'>未定义主机!</a> "
                });
                items.add(item);
            }
            JSONObject json = new JSONObject();
			json.put("page", page);
			json.put("total", ((PageList)servers).getPaginator().getTotalCount());
            json.put("rows", items);
        return json.toString();
    }
}
