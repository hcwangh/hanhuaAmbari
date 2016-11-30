package com.zjhc.hcdream.model;

/**
 * Desc:
 * email: zhangqj@zjhcsoft.com
 * Created by ZhangQingJing on 2015/12/25 14:16
 */
public class V_HardServerSummary extends AbstractServer{
    private int machine_entity_id;
    private String mem;
    private String disk;
    private String cpu_cores;
    private String principal_info;
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPrincipal_info() {
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

    public String getCpu_cores() {
        return cpu_cores;
    }

    public void setCpu_cores(String cpu_cores) {
        this.cpu_cores = cpu_cores;
    }
}
