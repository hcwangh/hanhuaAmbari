package com.zjhc.hcdream.model;

/**
 * Desc:
 * email: zhangqj@zjhcsoft.com
 * Created by ZhangQingJing on 2015/12/25 14:16
 */
public class HardAssectCategory extends AbstractServer{
/*    private int machine_entity_id;
    private String mem;
    private String disk;
    private String power;
    private String principal_info; */
    public  String category_id;
    public String category_name;



 /*   public String getPrincipal_info() {
        return principal_info;
    }

    public void setPrincipal_info(String principal_info) {
        this.principal_info = principal_info;
    }

    public int getMachine_entity_id() {
        return machine_entity_id;
    }

    public void setMachine_entity_id(int machine_entity_id) {
        this.machine_entity_id = machine_entity_id;
    }

    public String getMem() {
        return mem;
    }

    public void setMem(String mem) {
        this.mem = mem;
    }

    public String getDisk() {
        return disk;
    }

    public void setDisk(String disk) {
        this.disk = disk;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }
   */
    public String getCategory_id(){ return category_id;}

    public void setCategory_id(String category_id){this.category_id = category_id;}

    public String getCategory_name(){ return category_name;}

    public void setCategory_name(String category_name){this.category_name = category_name;}
}
