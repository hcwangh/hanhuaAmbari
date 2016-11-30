package com.zjhc.hcdream.view;

import com.zjhc.hcdream.model.V_HardDetails;
import com.zjhc.hcdream.service.AssectServerDetailService;
import com.zjhc.hcdream.service.AssectServerService;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc:
 * email: zhangqj@zjhcsoft.com
 * Created by ZhangQingJing on 2016/1/8 1:58
 */
public class WindowManager {

    public static ArrayList<ServerDetailWindow> newDetailWindows(){
        return new ArrayList<ServerDetailWindow>();
    }
    /**
     * 设置hostWindow规则
     * @param host_id
     * @return
     */
    public static ServerDetailWindow newHostWindow(String host_id,String windowId,String belongCategoryId,V_HardDetails parentDetail) {
        //belongCategoryId = 1
        V_HardDetails hostDetail = parentDetail;
//        V_HardDetails hostDetail = AssectServerService.selectHardDetailHost(host_id, belongCategoryId);
        boolean isSet = hostDetail != null ? (hostDetail.category_id != null) : false;
        List<String> typeBrandSelectList = AssectServerDetailService.selectBrandList(isSet ? hostDetail.category_id : belongCategoryId);
        List<String> typeVersionSelectList = null;
        if (isSet) {
            typeVersionSelectList = AssectServerDetailService.selectVersionList(hostDetail.category_id, hostDetail.type_brand);
        }
        ServerDetailWindow hostWindow = new ServerDetailWindow();
        hostWindow.id = windowId;
        hostWindow.name = "主机信息";
        hostWindow.belongCategoryId = belongCategoryId;//定义所属类别
        hostWindow.detail = hostDetail;
        hostWindow.isSet = isSet;

        ServerDetailWindowRow row_host_ip = newBasicRow("IP", hostDetail.host_ip);
        ServerDetailWindowRow row_host_name = newBasicRow("名称", hostDetail.host_name);
        ServerDetailWindowRow row_brand_select = newSelectRow(windowId, "品牌", "1", typeBrandSelectList, hostDetail.type_brand);
        ServerDetailWindowRow row_version_select = newSelectRow(windowId, "型号", "2", typeVersionSelectList, hostDetail.type_version);
        ServerDetailWindowRow row_entity_info3 = newNoEventSelectRow(windowId, "角色", hostDetail.entity_info3, "entity_info3",
                AssectServerDetailService.selectCombinedTypeNameList());
        ServerDetailWindowRow row_mem_count = newReadRow(windowId, "内存槽位数", hostDetail.type_info1, "type_info1");
        ServerDetailWindowRow row_disk_count = newReadRow(windowId, "硬盘槽位数", hostDetail.type_info2, "type_info2");
        ServerDetailWindowRow read_bodySize_row = newReadRow(windowId, "大小", hostDetail.type_info3, "type_info3");
        ServerDetailWindowRow read_powerCount_row = newReadRow(windowId, "电源数目", hostDetail.type_info4, "type_info4");
        ServerDetailWindowRow read_netSlotCount_row = newReadRow(windowId, "网口数目", hostDetail.type_info5, "type_info5");
        ServerDetailWindowRow row_entity_info1 = newTextRow(windowId, "序列号", hostDetail.entity_info1, "entity_info1");
        ServerDetailWindowRow row_entity_info2 = newTextRow(windowId, "负责人", hostDetail.entity_info2, "entity_info2");

        /*设置read 类型id 数组到 select类型对象中 提供后续事件处理: read 内容随着下拉框改变而改变*/
//        String[] readIds = {row_mem_count.id, row_disk_count.id}; //页面上 通过下标赋值:  1 -- typeInfo1, 2 -- typeInfo2, 3 -- typeInfo3
//        row_brand_select.initReadIdArr(readIds);
//        row_version_select.initReadIdArr(readIds);
        row_version_select.setReadIdArr(row_mem_count.id + "," +row_disk_count.id + ","
                + read_bodySize_row.id + "," + read_powerCount_row.id + "," + read_netSlotCount_row.id);

        hostWindow.setSubmitTextIdArr(row_entity_info1.id + "," + row_entity_info2.id + "," + row_entity_info3.id);

        hostWindow.add(row_host_ip);
        hostWindow.add(row_host_name);
        hostWindow.add(row_brand_select);
        hostWindow.add(row_version_select);
        hostWindow.add(row_entity_info3);
        hostWindow.add(row_mem_count);
        hostWindow.add(row_disk_count);
        hostWindow.add(read_bodySize_row);
        hostWindow.add(read_powerCount_row);
        hostWindow.add(read_netSlotCount_row);
        hostWindow.add(row_entity_info1);
        hostWindow.add(row_entity_info2);
        return hostWindow;
    }

    /**
     * 设置powerWindow规则
     * @param host_id
     * @return
     */
    public static ServerDetailWindow newPowerWindow(String host_id,String windowId,String belongCategoryId,V_HardDetails parentDetail) {
        //belongCategoryId = 4
        V_HardDetails powerDetail = AssectServerService.selectHardDetailHost(host_id, belongCategoryId);
        boolean isSet = false;
        if(parentDetail.category_id != null && powerDetail != null) {
            isSet = true;
        }else{
            powerDetail = new V_HardDetails();
            powerDetail.host_id = parentDetail.host_id;
            powerDetail.machine_entity_id = parentDetail.entity_id;
        }

        List<String> typeBrandSelectList = AssectServerDetailService.selectBrandList(isSet ? powerDetail.category_id : belongCategoryId);
        List<String> typeVersionSelectList = null;
        if (isSet) {
            typeVersionSelectList = AssectServerDetailService.selectVersionList(powerDetail.category_id, powerDetail.type_brand);
        }
        ServerDetailWindow powerWindow = new ServerDetailWindow();
        powerWindow.id = windowId;
        powerWindow.name = "电源信息";
        powerWindow.belongCategoryId = belongCategoryId;//定义所属类别
        powerWindow.detail = powerDetail;
        powerWindow.isSet = isSet;

//        ServerDetailWindowRow basic_row = newBasicRow("名称", powerDetail.entity_name);
        ServerDetailWindowRow row_brand_select =  newSelectRow(windowId, "品牌", "1", typeBrandSelectList, powerDetail.type_brand);
        ServerDetailWindowRow row_version_select = newSelectRow(windowId, "型号", "2", typeVersionSelectList, powerDetail.type_version);

        ServerDetailWindowRow read_row_1 = newReadRow(windowId, "功率", powerDetail.type_info1, "type_info1");
//        ServerDetailWindowRow read_row_2 = newReadRow(windowId, "价格", powerDetail.type_info2, "type_info2");

//        ServerDetailWindowRow text_row_1 = newTextRow(windowId, "序列号1", powerDetail.entity_info1, "entity_info1");
//        ServerDetailWindowRow text_row_2 = newTextRow(windowId, "序列号2", powerDetail.entity_info2, "entity_info2");

        /*设置read 类型id 数组到 select类型对象中 提供后续事件处理: read 内容随着下拉框改变而改变*/

//        row_version_select.setReadIdArr(read_row_1.id + "," + read_row_2.id);
        row_version_select.setReadIdArr(read_row_1.id);

//        powerWindow.setSubmitTextIdArr(text_row_1.id + "," + text_row_2.id);

//        powerWindow.add(basic_row);
        powerWindow.add(row_brand_select);
        powerWindow.add(row_version_select);
        powerWindow.add(read_row_1);
//        powerWindow.add(read_row_2);
//        powerWindow.add(text_row_1);
//        powerWindow.add(text_row_2);
        return powerWindow;
    }

    /**
     * 设置cpuWindow规则
     * @param host_id
     * @return
     */
    public static ServerDetailWindow newCpuWindow(String host_id,String windowId,String belongCategoryId,V_HardDetails parentDetail) {
        //belongCategoryId = 5
        V_HardDetails cpuDetail = AssectServerService.selectHardDetailHost(host_id, belongCategoryId);
        boolean isSet = false;
        if(parentDetail.category_id != null && cpuDetail != null) {
            isSet = true;
        }else{
            cpuDetail = new V_HardDetails();
            cpuDetail.host_id = parentDetail.host_id;
            cpuDetail.machine_entity_id = parentDetail.entity_id;
        }

        List<String> typeBrandSelectList = AssectServerDetailService.selectBrandList(belongCategoryId);
        List<String> typeVersionSelectList = null;
        if (isSet) {
            typeVersionSelectList = AssectServerDetailService.selectVersionList(cpuDetail.category_id, cpuDetail.type_brand);
        }
        ServerDetailWindow cpuWindow = new ServerDetailWindow();
        cpuWindow.id = windowId;
        cpuWindow.name = "CPU信息";
        cpuWindow.belongCategoryId = belongCategoryId;//定义所属类别
        cpuWindow.detail = cpuDetail;
        cpuWindow.isSet = isSet;

//        ServerDetailWindowRow basic_row = newBasicRow("名称", powerDetail.entity_name);
        ServerDetailWindowRow row_brand_select =  newSelectRow(windowId, "品牌", "1", typeBrandSelectList, cpuDetail.type_brand);
        ServerDetailWindowRow row_version_select = newSelectRow(windowId, "型号", "2", typeVersionSelectList, cpuDetail.type_version);

        ServerDetailWindowRow read_row_1 = newReadRow(windowId, "核数", cpuDetail.type_info1, "type_info1");
        ServerDetailWindowRow read_row_2 = newReadRow(windowId, "主频", cpuDetail.type_info2, "type_info2");
//        ServerDetailWindowRow read_row_3 = newReadRow(windowId, "价格", cpuDetail.type_info3, "type_info3");

        ServerDetailWindowRow text_row_1 = newTextRow(windowId, "序列号", cpuDetail.entity_info1, "entity_info1");
        /*设置read 类型id 数组到 select类型对象中 提供后续事件处理: read 内容随着下拉框改变而改变*/
        row_version_select.setReadIdArr(read_row_1.id +"," + read_row_2.id);

        cpuWindow.setSubmitTextIdArr(text_row_1.id);

//        powerWindow.add(basic_row);
        cpuWindow.add(row_brand_select);
        cpuWindow.add(row_version_select);
        cpuWindow.add(read_row_1);
        cpuWindow.add(read_row_2);
        cpuWindow.add(text_row_1);
        return cpuWindow;
    }

    /**
     * 设置netCardWindow规则
     * @param host_id
     * @return
     */
    public static ServerDetailWindow newNetCardWindow(String host_id,String windowId,String belongCategoryId,V_HardDetails parentDetail ) {
        //belongCategoryId = 6
        V_HardDetails cpuDetail = AssectServerService.selectHardDetailHost(host_id, belongCategoryId);
        boolean isSet = false;
        if(parentDetail.category_id != null && cpuDetail != null) {
            isSet = true;
        }else{
            cpuDetail = new V_HardDetails();
            cpuDetail.host_id = parentDetail.host_id;
            cpuDetail.machine_entity_id = parentDetail.entity_id;
        }

        List<String> typeBrandSelectList = AssectServerDetailService.selectBrandList(belongCategoryId);
        List<String> typeVersionSelectList = null;
        if (isSet) {
            typeVersionSelectList = AssectServerDetailService.selectVersionList(cpuDetail.category_id, cpuDetail.type_brand);
        }
        ServerDetailWindow cpuWindow = new ServerDetailWindow();
        cpuWindow.id = windowId;
        cpuWindow.name = "网卡信息";
        cpuWindow.belongCategoryId = belongCategoryId;//定义所属类别
        cpuWindow.detail = cpuDetail;
        cpuWindow.isSet = isSet;

//        ServerDetailWindowRow basic_row = newBasicRow("名称", powerDetail.entity_name);
        ServerDetailWindowRow row_brand_select =  newSelectRow(windowId, "品牌", "1", typeBrandSelectList, cpuDetail.type_brand);
        ServerDetailWindowRow row_version_select = newSelectRow(windowId, "型号", "2", typeVersionSelectList, cpuDetail.type_version);

        ServerDetailWindowRow read_row_1 = newReadRow(windowId, "速率", cpuDetail.type_info1, "type_info1");
//        ServerDetailWindowRow read_row_2 = newReadRow(windowId, "价格", cpuDetail.type_info2, "type_info2");

        ServerDetailWindowRow text_row_1 = newTextRow(windowId, "序列号", cpuDetail.entity_info1, "entity_info1");

        /*设置read 类型id 数组到 select类型对象中 提供后续事件处理: read 内容随着下拉框改变而改变*/
        row_version_select.setReadIdArr(read_row_1.id);

        cpuWindow.setSubmitTextIdArr(text_row_1.id);

//        powerWindow.add(basic_row);
        cpuWindow.add(row_brand_select);
        cpuWindow.add(row_version_select);
        cpuWindow.add(read_row_1);
//        cpuWindow.add(read_row_2);
        cpuWindow.add(text_row_1);
        return cpuWindow;
    }


    /**
     * 设置memWindow规则
     * @param host_id
     * @return
     */
    public static ServerDetailWindow newMemWindow(String host_id,String windowId,String belongCategoryId,V_HardDetails parentDetail) {
        //belongCategoryId = 2

        List<String> typeBrandSelectList = AssectServerDetailService.selectBrandList(belongCategoryId);

        ServerDetailWindow memWindow = new ServerDetailWindow();
        memWindow.type = "list"; // list window type!
        memWindow.id = windowId;
        memWindow.name = "内存信息";
        memWindow.belongCategoryId = belongCategoryId;//定义所属类别
        memWindow.actionUrl = "server_detail_mem_list";

        ServerDetailWindowRow belong_select_1 = new ServerDetailWindowRow();
        belong_select_1.setTypeBelongSelect1();
        belong_select_1.id = windowId + "_belong_info1";
        belong_select_1.labelName = "槽位序号";
        belong_select_1.value = parentDetail.type_info1;

        ServerDetailWindowRow row_brand_select =  newSelectRow(windowId,"品牌", "1", typeBrandSelectList, null );
        ServerDetailWindowRow row_version_select = newSelectRow(windowId, "型号", "2", null, null);
        ServerDetailWindowRow read_row_1 = newReadRow(windowId, "大小", null, "type_info2");
        ServerDetailWindowRow text_row_1 = newTextRow(windowId, "序列号", null, "entity_info1");

        /*设置read 类型id 数组到 select类型对象中 提供后续事件处理: read 内容随着下拉框改变而改变*/
        //页面上 通过下标赋值:  1 -- typeInfo1, 2 -- typeInfo2, 3 -- typeInfo3 不显示的通过none进行忽略
        row_version_select.setReadIdArr("none," + read_row_1.id);

        memWindow.setSubmitTextIdArr(text_row_1.id);
        memWindow.setBelongIdArr(belong_select_1.id);

//        powerWindow.add(basic_row);
        memWindow.add(belong_select_1);
        memWindow.add(row_brand_select);
        memWindow.add(row_version_select);
        memWindow.add(read_row_1);
        memWindow.add(text_row_1);
        return memWindow;
    }

    /**
     * 设置diskWindow规则
     * @param host_id
     * @return
     */
    public static ServerDetailWindow newDiskWindow(String host_id, String windowId, String belongCategoryId, V_HardDetails parentDetail) {
        //belongCategoryId = 3

        List<String> typeBrandSelectList = AssectServerDetailService.selectBrandList(belongCategoryId);

        ServerDetailWindow diskWindow = new ServerDetailWindow();
        diskWindow.type = "list"; // list window type!
        diskWindow.id = windowId;
        diskWindow.name = "硬盘信息";
        diskWindow.belongCategoryId = belongCategoryId;//定义所属类别
        diskWindow.actionUrl = "server_detail_disk_list";

        ServerDetailWindowRow belong_select_1 = new ServerDetailWindowRow();
        belong_select_1.setTypeBelongSelect1();
        belong_select_1.id = windowId + "_belong_info1";
        belong_select_1.labelName = "槽位序号";
        belong_select_1.value = parentDetail.type_info2;

        ServerDetailWindowRow row_brand_select =  newSelectRow(windowId,"品牌", "1", typeBrandSelectList, null );
        ServerDetailWindowRow row_version_select = newSelectRow(windowId, "型号", "2", null, null);
        ServerDetailWindowRow read_row_1 = newReadRow(windowId, "大小", null, "type_info2");
        ServerDetailWindowRow text_row_1 = newTextRow(windowId, "序列号", null, "entity_info1");

        /*设置read 类型id 数组到 select类型对象中 提供后续事件处理: read 内容随着下拉框改变而改变*/
        //页面上 通过下标赋值:  1 -- typeInfo1, 2 -- typeInfo2, 3 -- typeInfo3  不显示的通过none进行忽略
        row_version_select.setReadIdArr("none," + read_row_1.id);

        diskWindow.setSubmitTextIdArr(text_row_1.id);
        diskWindow.setBelongIdArr(belong_select_1.id);

//        powerWindow.add(basic_row);
        diskWindow.add(belong_select_1);
        diskWindow.add(row_brand_select);
        diskWindow.add(row_version_select);
        diskWindow.add(read_row_1);
        diskWindow.add(text_row_1);
        return diskWindow;
    }


    public static ServerDetailWindowRow newSelectRow(String windowId,String labelName,String select_level,List<String> optionList,String  value){
        ServerDetailWindowRow selectRow = new ServerDetailWindowRow();
        selectRow.setTypeSelect();
        selectRow.labelName = labelName;
        selectRow.id =  windowId+ ((select_level.equals("1") )?  "_brand_select": "_version_select");
        selectRow.select_level = select_level;
        selectRow.select_id1 = windowId + "_brand_select";
        selectRow.select_id2 = windowId + "_version_select";
        selectRow.value = value;
        selectRow.selectValueList = optionList;
        return selectRow;
    }

    public static ServerDetailWindowRow newReadRow(String windowId,String labelName,String value,String subffix){
        ServerDetailWindowRow readRow = new ServerDetailWindowRow();
        readRow.setTypeRead();
        readRow.labelName = labelName;
        readRow.id = windowId + "_" + subffix;
        readRow.value = value;
        return readRow;
    }

    public static ServerDetailWindowRow newNoEventSelectRow(String windowId,String labelName,String value,String subffix,List<String> optionList){
        ServerDetailWindowRow noEventSelectRow = new ServerDetailWindowRow();
        noEventSelectRow.setTypeSelectNoEvent();
        noEventSelectRow.labelName = labelName;
        noEventSelectRow.id = windowId + "_" + subffix;
        noEventSelectRow.value = value;
        noEventSelectRow.selectValueList = optionList;
        return noEventSelectRow;
    }


    public static ServerDetailWindowRow newTextRow(String windowId,String labelName,String value,String subffix){
        ServerDetailWindowRow textRow = new ServerDetailWindowRow();
        textRow.setTypeText();
        textRow.labelName = labelName;
        textRow.id = windowId + "_" + subffix;
        textRow.value = value;
        return textRow;
    }

    public static ServerDetailWindowRow newBasicRow(String labelName,String value){
        ServerDetailWindowRow basicRow = new ServerDetailWindowRow();
        basicRow.setTypeBasic();
        basicRow.labelName = labelName;
        basicRow.value = value;
        return basicRow;
    }

}
