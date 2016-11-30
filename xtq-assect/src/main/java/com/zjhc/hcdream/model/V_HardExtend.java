package com.zjhc.hcdream.model;

/**
 * Desc:
 * email: zhangqj@zjhcsoft.com
 * Created by ZhangQingJing on 2016/1/11 1:12
 */
public class V_HardExtend {
    public String host_ip;
    public String host_name;
    public String memCountInUse;
    public String totalMemCount;
    public String diskCountInUse;
    public String totalDiskCount;

    public String getHost_ip() {
        return host_ip;
    }

    public void setHost_ip(String host_ip) {
        this.host_ip = host_ip;
    }

    public String getHost_name() {
        return host_name;
    }

    public void setHost_name(String host_name) {
        this.host_name = host_name;
    }

    public String getMemCountInUse() {
        return memCountInUse;
    }

    public void setMemCountInUse(String memCountInUse) {
        this.memCountInUse = memCountInUse;
    }

    public String getTotalMemCount() {
        return totalMemCount;
    }

    public void setTotalMemCount(String totalMemCount) {
        this.totalMemCount = totalMemCount;
    }

    public String getDiskCountInUse() {
        return diskCountInUse;
    }

    public void setDiskCountInUse(String diskCountInUse) {
        this.diskCountInUse = diskCountInUse;
    }

    public String getTotalDiskCount() {
        return totalDiskCount;
    }

    public void setTotalDiskCount(String totalDiskCount) {
        this.totalDiskCount = totalDiskCount;
    }
}
