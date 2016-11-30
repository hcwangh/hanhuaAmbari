package com.zjhc.hcdream.view;

/**
 * Desc:
 * email: zhangqj@zjhcsoft.com
 * Created by ZhangQingJing on 2016/1/8 19:04
 */
public class Category {
    public static final Attr HOST = new Attr("xtq_host_wdw","1");
    public static final Attr MEM = new Attr("xtq_mem_wdw","2");
    public static final Attr DISK= new Attr("xtq_disk_wdw","3");
    public static final Attr POWER = new Attr("xtq_power_wdw","4");
    public static final Attr CPU = new Attr("xtq_cpu_wdw","5");
    public static final Attr NETCARD = new Attr("xtq_netcard_wdw","6");


    public static class Attr{
        public String nameId;
        public String dbId;

        public Attr(String nameId, String dbId) {
            this.nameId = nameId;
            this.dbId = dbId;
        }
    }
}
