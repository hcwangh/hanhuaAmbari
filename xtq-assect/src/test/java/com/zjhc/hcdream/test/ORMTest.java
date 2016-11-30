package com.zjhc.hcdream.test;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.zjhc.hcdream.controller.AssectServersDetailController;
import com.zjhc.hcdream.model.*;
import com.zjhc.hcdream.service.AssectCategoryService;
import com.zjhc.hcdream.service.AssectServerDetailService;
import com.zjhc.hcdream.service.AssectServerService;
import com.zjhc.hcdream.service.SequenceService;
import com.zjhc.hcdream.util.DateUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * Desc:
 * email: zhangqj@zjhcsoft.com
 * Created by ZhangQingJing on 2015/12/28 11:50
 */
public class ORMTest {
    public static void main(String[] args) {
//        selectXTQAssectServers();
//        System.out.println(SequenceService.nextSeq_belong_id());
//        selectHardDetailHost();
//        selectBrandList();
//        selectVersionList();
//        updateHardAssectEntity();
//        addMachineEty();
//        newHost();
//           addEntityToHost();
//          expireEntity();
//        selectCombinedTypeNameList();

           transactionTest();
    }

    //TODO Mybatis 事务操作 test
    private static void transactionTest(){
        try {
            AssectServerDetailService.transactionTest();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   /*     List<HardAssectCategory> list = AssectCategoryService.selectAsssectCategory();
        JSONArray array  = new JSONArray();
        for (HardAssectCategory category : list) {
            JSONObject object = new JSONObject();
            object.put("category_id",category.category_id);
            object.put("category_name",category.category_name);
            array.add(object);
        }
*/    //    System.out.println(array.toString());
      //  System.out.println(getCategorySelectData());
//    }

    public static JSONArray getCategorySelectData() {
        List<HardAssectCategory> lists = AssectCategoryService.selectAsssectCategory();
        JSONArray categoryArray = new JSONArray();
        for (HardAssectCategory list : lists) {
            JSONObject object = new JSONObject();
            object.put("category_id", list.category_id);
            object.put("category_name", list.category_name);
            categoryArray.add(object);
        }
        return categoryArray;
    }
    private  static void selectCombinedTypeNameList() {
        List<String> strings = AssectServerDetailService.selectCombinedTypeNameList();
        for (String string : strings) {
            System.out.println(string);
        }
    }
    private static void expireEntity() {
        String host_id = "m1";
        String machine_entity_id = "1";
        String entity_id = "5";

        V_HardDetails detail = AssectServerDetailService.selectExpireHardDetail(host_id, machine_entity_id, entity_id);

        HardAssectEntityHistory entityHistory = new HardAssectEntityHistory();
        entityHistory.setDetail(detail);
        entityHistory.setEntity_break_time(DateUtil.newDateStr());

        boolean b = AssectServerDetailService.addEntityHistoty(entityHistory);
        AssectServerDetailService.deleteAssectEntity(entity_id);
        AssectServerDetailService.deleteMachineEty(machine_entity_id,entity_id);

    }


    private static void addEntityToHost(){
        String type_id = "7";
        String category_id = "4";
        String entity_info1 = "zqjjjjjjj---";
        String entity_info2 = "2w元";
        String entity_info3 = "";
        String machineId = "23";

        HardAssectEntity entity = new HardAssectEntity();
        entity.type_id = type_id;
        entity.category_id = category_id;
        entity.entity_info1 = entity_info1;
        String entity_id = AssectServerDetailService.newEntity(entity);
        HardAssectMachineEntity mchety = new HardAssectMachineEntity();
        mchety.belong_id = SequenceService.nextSeq_belong_id();
        mchety.machine_entity_id  = machineId;
        mchety.entity_id = entity_id;
        mchety.entity_on_time = DateUtil.newDateStr();
        boolean b = AssectServerDetailService.addMachineEty(mchety);
        System.out.println(b);
    }
    private static void newHost() {
        String type_id = "6";
        String category_id = "1";
        String entity_info1 = "IBM1111111111";
        String host_id = "7";
        HardAssectEntity entity = new HardAssectEntity();
        entity.type_id = type_id;
        entity.category_id = category_id;
        entity.entity_info1 = entity_info1;
        String entity_id = AssectServerDetailService.newEntity(entity);
        AssectServerDetailService.newMachine(entity_id, null);
        HardAssectServers server = new HardAssectServers();
        server.host_id = host_id;
        server.machine_entity_id = entity_id;
        AssectServerDetailService.updateServer(server);
    }


    private static void addMachineEty() {
        HardAssectMachineEntity entity = new HardAssectMachineEntity();
        String id = SequenceService.nextSeq_belong_id();
        entity.belong_id = id;
        boolean b = AssectServerDetailService.addMachineEty(entity);
        System.out.println(b);
    }
    private static void updateHardAssectEntity() {
        HardAssectEntity entity = new HardAssectEntity();
        entity.entity_id = "1";
        entity.type_id = "1";
        entity.entity_info2 = "info22222222222";

        boolean b = AssectServerDetailService.updateHardAssectEntity(entity);
        System.out.println(b);
    }

    private static void selectVersionList() {
        List<String> version = AssectServerDetailService.selectVersionList("1", "华为");
        for (String s : version) {
            System.out.println(s);
        }
    }

    private static void selectBrandList() {
        List<String> brandList = AssectServerDetailService.selectBrandList("1");
        for (String s : brandList) {
            System.out.println(s);
        }
    }

    private static void selectHardDetailHost() {
        V_HardDetails v_hardDetails = AssectServerService.selectHardDetailHost("1","1");
        System.out.println(v_hardDetails.category_id);
    }

    private static void selectXTQAssectServers() {
        List<V_HardServerSummary> servers = AssectServerService.selectServerSummary(new PageBounds());
        for (V_HardServerSummary server : servers) {
            System.out.println(server.getHost_id());
            System.out.println(server.getPrincipal_info());
        }
    }

    private static void updateType(){

    }
}
