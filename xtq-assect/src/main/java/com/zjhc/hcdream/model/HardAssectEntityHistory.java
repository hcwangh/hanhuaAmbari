package com.zjhc.hcdream.model;

/**
 * Desc:
 * email: zhangqj@zjhcsoft.com
 * Created by ZhangQingJing on 2016/1/10 22:50
 */
public class HardAssectEntityHistory {
    public V_HardDetails detail;
    public String entity_off_time;
    public String entity_break_time;

    public V_HardDetails getDetail() {
        return detail;
    }

    public void setDetail(V_HardDetails detail) {
        this.detail = detail;
    }

    public String getEntity_off_time() {
        return entity_off_time;
    }

    public void setEntity_off_time(String entity_off_time) {
        this.entity_off_time = entity_off_time;
    }

    public String getEntity_break_time() {
        return entity_break_time;
    }

    public void setEntity_break_time(String entity_break_time) {
        this.entity_break_time = entity_break_time;
    }
}
