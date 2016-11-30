package com.zjhc.hcdream.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.zjhc.hcdream.model.*;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Desc:
 * email: zhangqj@zjhcsoft.com
 * Created by ZhangQingJing on 2015/12/25 14:41
 */
public interface XTQAssectDao {

    //类别表
    @Select("select * from hard_assect_category")
    public List<HardAssectCategory> selectAsssectCategory();

    //类型表
//    @Select("select * from hard_assect_type")
//    public List<HardAssectType> selectAsssectType();

    //
    @Select("select * from v_hard_type")
    public List<HardAssectType> selectAsssectType();

    //添加类型到hard_assect_type表
    @Insert("insert into hard_assect_type(category_id,type_id,type_name,type_brand,type_version,type_info1,type_info2,type_info3,type_info4,type_info5,reference_price)  values(#{category_id},#{type_id},#{type_name},#{type_brand},#{type_version},#{type_info1},#{type_info2},#{type_info3},#{type_info4},#{type_info5},#{reference_price})")
    public boolean addXTQAmbaritype(HardAssectType type);


    //修改类型到hard_assect_type表


    //添加新的类别
    @Insert("insert into hard_assect_category(category_id,category_name) " + "values(#{category_id},#{category_name})")
    public boolean addCategory(HardAssectCategory category);

    //查询主机总览表集合
    @Select("select * from v_hard_server_summary")
    public List<V_HardServerSummary> selectServerSummary(PageBounds pageBounds);


    //从Amabri 元数据库查询
    @Select("select host_id,ipv4 host_ip,host_name from hosts")
    public List<XTQAmbariServer> selectXTQAmbariServers();

    //查询 v_hard_details 视图
    @Select("select * from v_hard_details where host_id = #{0} and( category_id = #{1} or category_id is null);")
    public V_HardDetails selectHardDetailHost(String host_id,String category_id);

    @Insert("insert into hard_assect_entity_history(host_id,host_ip,host_name,belong_id,machine_entity_id,entity_id,entity_name,entity_info1,entity_info2,entity_info3,state,category_id,category_name,type_id,type_name,type_brand,type_version,type_info1,type_info2,type_info3,type_info4,type_info5,reference_price,belong_info1,entity_on_time,entity_off_time,entity_break_time,principal_info)\n" +
            "values(#{detail.host_id},#{detail.host_ip},#{detail.host_name},#{detail.belong_id},#{detail.machine_entity_id},#{detail.entity_id},#{detail.entity_name},#{detail.entity_info1},#{detail.entity_info2},#{detail.entity_info3},#{detail.state},#{detail.category_id},#{detail.category_name},#{detail.type_id},#{detail.type_name},#{detail.type_brand},#{detail.type_version},#{detail.type_info1},#{detail.type_info2},#{detail.type_info3},#{detail.type_info4},#{detail.type_info5},#{detail.reference_price},#{detail.belong_info1},#{detail.entity_on_time},#{entity_off_time},#{entity_break_time},#{detail.principal_info})")
    public boolean addEntityHistoty(HardAssectEntityHistory entityHistory);

    @Select("select * from hard_assect_entity_history")
    public List<HardAssectEntityHistory_s> selectEntityHistoryList(PageBounds pageBounds);

    //查询 v_hard_details 视图
    @Select("select * from v_hard_details where host_id = #{0} and( category_id = #{1} or category_id is null);")
    public List<V_HardDetails> selectHardDetailList(String host_id,String category_id,PageBounds page);

    //查询 v_hard_details 视图
    @Select("select * from v_hard_details where host_id = #{0} and machine_entity_id = #{1} and entity_id = #{2};")
    public V_HardDetails selectExpireHardDetail(String host_id,String machien_entity_id,String  entity_id);

    //查询品牌集合
    @Select("select distinct type_brand as value from hard_assect_type where category_id = #{category_id};")
    public List<String> selectBrandList(String category_id);

    //查询型号集合
    @Select("select type_version as value from hard_assect_type  where category_id=#{0} and type_brand=#{1}")
    public List<String> selectVersionList(String category_id,String type_brand);

    @Select("select distinct combined_type_name as value from hard_assect_combined_type")
    public List<String> selectCombinedTypeNameList();

    //根据品牌和型号查询 hard_assect_type 记录
    @Select("select * from hard_assect_type where type_brand = #{0} and type_version =#{1}")
    public HardAssectType selectHardAssectType(String type_brand, String type_version);

    //添加主机到hard_assect_servers表
    @Insert("insert into hard_assect_servers(host_id,host_ip,host_name)  values(#{host_id},#{host_ip},#{host_name})")
    public boolean addXTQAmbariServer(XTQAmbariServer server);

    @Select("select machine_entity_id from hard_assect_servers where host_id = #{host_id}")
    public String selectMchIdFromServer(String host_id);

    @Delete("delete  from hard_assect_servers where host_id = #{host_id}")
    public boolean deleteServer(String host_id);

    @Insert("insert into hard_assect_entity(entity_id,entity_name,type_id,category_id,entity_info1,entity_info2,entity_info3,state)  " +
                                                        "values(#{entity_id},#{entity_name},#{type_id},#{category_id},#{entity_info1},#{entity_info2},#{entity_info3},#{state})")
    public boolean addAssectEntity(HardAssectEntity entity);

    @Delete("delete from hard_assect_entity where entity_id = #{entity_id}")
    public boolean deleteAssectEntity(String entity_id);

    @Insert("insert into hard_assect_machine_entity(belong_id,machine_entity_id,entity_id,belong_info1,entity_on_time) " +
                "values(#{belong_id},#{machine_entity_id},#{entity_id},#{belong_info1},#{entity_on_time})")
    public boolean addMachineEty(HardAssectMachineEntity ety);

    @Delete("delete from hard_assect_machine_entity where machine_entity_id = #{0} and entity_id = #{1}")
    public boolean deleteMachineEty(String machine_entity_id,String entity_id);

    @Select("select entity_id from hard_assect_machine_entity where machine_entity_id = #{machine_entity_id}")
    public List<String> selectEtyIdsFromMchEty(String machine_entity_id);

    @Insert("insert into hard_assect_servers(host_id,host_ip,host_name,machine_entity_id,principal_info)  " +
            "values(#{host_id},#{host_ip},#{host_name},#{machine_entity_id},#{machine_entity_id})")
    public boolean addAssectServer(HardAssectServers server);

    @Select("select * from v_hard_extend")
    public List<V_HardExtend> selectV_HardExtendList(PageBounds page);

    @Select("select * from v_hard_broken")
    public List<V_HardBroken> selectBrokenStat(PageBounds page);

    public boolean updateHardAssectEntity(HardAssectEntity entity);
    public boolean updateMachineEty(HardAssectMachineEntity mchety);

    public boolean updateServer(HardAssectServers server);

    public boolean updateHardAssectType(HardAssectType xtqtype);
}
