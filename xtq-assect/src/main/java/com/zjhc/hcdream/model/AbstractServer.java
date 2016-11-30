package com.zjhc.hcdream.model;

/**
 * Desc:
 * email: zhangqj@zjhcsoft.com
 * Created by ZhangQingJing on 2015/12/28 11:38
 */
public abstract class AbstractServer {
    private String host_id;
    private String host_ip;
    private String host_name;

    public String getHost_id() {
        return host_id;
    }

    public void setHost_id(String host_id) {
        this.host_id = host_id;
    }

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

    @Override
    public boolean equals(Object obj) {

        AbstractServer server = null;
        if(obj != null  && obj instanceof AbstractServer){
            server = (AbstractServer) obj;
        }else{
            return false;
        }

        boolean bln_host_id = host_id.equals(server.getHost_id());
        boolean bln_host_ip = (host_ip).equals(server.getHost_ip());
        boolean bln_host_name = (host_name).equals(server.getHost_name());

    if( !bln_host_id || !bln_host_ip || !bln_host_name){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return host_id.hashCode() + host_ip.hashCode() + host_name.hashCode();
    }
}
