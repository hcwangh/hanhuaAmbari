package com.zjhc.hcdream.model;

/**
 * Desc:
 * email: zhangqj@zjhcsoft.com
 * Created by ZhangQingJing on 2016/1/11 14:17
 */
public class V_HardBroken {
    public String host_ip;
    public String host_name;
    public String membrokenCount;
    public String diskbrokenCount;

    public String getHost_name() {
        return host_name;
    }

    public void setHost_name(String host_name) {
        this.host_name = host_name;
    }

    public String getHost_ip() {
        return host_ip;
    }

    public void setHost_ip(String host_ip) {
        this.host_ip = host_ip;
    }

    public String getMembrokenCount() {
        return membrokenCount;
    }

    public void setMembrokenCount(String membrokenCount) {
        this.membrokenCount = membrokenCount;
    }

    public String getDiskbrokenCount() {
        return diskbrokenCount;
    }

    public void setDiskbrokenCount(String diskbrokenCount) {
        this.diskbrokenCount = diskbrokenCount;
    }
}
