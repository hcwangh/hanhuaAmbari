package com.zjhc.hcdream.view;

import com.zjhc.hcdream.model.V_HardDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc:
 * email: zhangqj@zjhcsoft.com
 * Created by ZhangQingJing on 2016/1/4 23:01
 */
public class ServerDetailWindow {
    //唯一ID
    public String id;
    //是否已经设置
    public boolean isSet;
    //显示title名称
    public String name;
    //type ="list"  -->  新加有用属性:　actionUrl belongIdArr 相对普通type类型(type为空) 无用的属性为 isSet及detail
    public String type;
    public String actionUrl;
    public String belongCategoryId;
    //对应的数据库 实体详情
    public V_HardDetails detail;
    //窗口中所有的行
    public List<ServerDetailWindowRow> list = new ArrayList<ServerDetailWindowRow>();
    //要提交的Text属性html的Id
    public String submitTextIdArr;
    public String belongIdArr;

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBelongIdArr() {
        return belongIdArr;
    }

    public void setBelongIdArr(String belongIdArr) {
        this.belongIdArr = belongIdArr;
    }

    public String getBelongCategoryId() {
        return belongCategoryId;
    }

    public void setBelongCategoryId(String belongCategoryId) {
        this.belongCategoryId = belongCategoryId;
    }

    public V_HardDetails getDetail() {
        return detail;
    }

    public void setDetail(V_HardDetails detail) {
        this.detail = detail;
    }

    public String getId() {
        return id;
    }

    public void setList(List<ServerDetailWindowRow> list) {
        this.list = list;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void add(ServerDetailWindowRow row) {
        list.add(row);
    }

    public boolean isSet() {
        return isSet;
    }

    public void setIsSet(boolean isSet) {
        this.isSet = isSet;
    }

    public boolean getIsSet() {
        return isSet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ServerDetailWindowRow> getList() {
        return list;
    }

    public void setList(ArrayList<ServerDetailWindowRow> list) {
        this.list = list;
    }

    public String getSubmitTextIdArr() {
        return submitTextIdArr;
    }

    public void setSubmitTextIdArr(String submitTextIdArr) {
        this.submitTextIdArr = submitTextIdArr;
    }

}
