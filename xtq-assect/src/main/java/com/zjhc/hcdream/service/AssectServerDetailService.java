package com.zjhc.hcdream.service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.zjhc.hcdream.dao.XTQAssectDao;
import com.zjhc.hcdream.model.*;
import com.zjhc.hcdream.util.DateUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Desc:  对应server_detail.jsp 相关的调用方法
 * email: zhangqj@zjhcsoft.com
 * Created by ZhangQingJing on 2016/1/5 16:06
 */
public class AssectServerDetailService extends SuperService {

    public static List<String> selectBrandList(String category_id) {
        SqlSession session = getSqlSession();
        XTQAssectDao xtqAssectdao = session.getMapper(XTQAssectDao.class);
        List<String> list = xtqAssectdao.selectBrandList(category_id);
        closeSqlSession(session);
        return list;
    }

    public static List<String> selectCombinedTypeNameList() {
        SqlSession session = getSqlSession();
        XTQAssectDao xtqAssectdao = session.getMapper(XTQAssectDao.class);
        List<String> list = xtqAssectdao.selectCombinedTypeNameList();
        closeSqlSession(session);
        return list;
    }

    public static List<String> selectVersionList(String category_id,String type_brand) {
        SqlSession session = getSqlSession();
        XTQAssectDao xtqAssectdao = session.getMapper(XTQAssectDao.class);
        List<String> list = xtqAssectdao.selectVersionList(category_id, type_brand);
        closeSqlSession(session);
        return list;
    }

    public static List<V_HardDetails> selectHardDetailList(String host_id,String category_id,PageBounds page){
        SqlSession session = getSqlSession();
        XTQAssectDao serverDao = session.getMapper(XTQAssectDao.class);
        List<V_HardDetails> hardDetailList = serverDao.selectHardDetailList(host_id, category_id, page);
        closeSqlSession(session);
        return hardDetailList;
    }

    public static V_HardDetails selectExpireHardDetail(String host_id,String machine_entity_id,String entity_id){
        SqlSession session = getSqlSession();
        XTQAssectDao serverDao = session.getMapper(XTQAssectDao.class);
        V_HardDetails detail = serverDao.selectExpireHardDetail(host_id, machine_entity_id, entity_id);
        closeSqlSession(session);
        return detail;
    }

    public static HardAssectType selectHardAssectType(String brand_value,String version_value){
        SqlSession session = getSqlSession();
        XTQAssectDao xtqAssectdao = session.getMapper(XTQAssectDao.class);
        HardAssectType hardAssectType = xtqAssectdao.selectHardAssectType(brand_value, version_value);
        closeSqlSession(session);
        return hardAssectType;
    }


    public static boolean addEntityHistoty(HardAssectEntityHistory entityHistory){
        SqlSession session = getSqlSession();
        XTQAssectDao xtqAssectdao = session.getMapper(XTQAssectDao.class);
        boolean result = xtqAssectdao.addEntityHistoty(entityHistory);
        session.commit();//增 删 改 之后 commit()
        closeSqlSession(session);
        return result;
    }


    public static boolean updateHardAssectEntity(HardAssectEntity entity){
        SqlSession session = getSqlSession();
        XTQAssectDao xtqAssectdao = session.getMapper(XTQAssectDao.class);
        boolean result = xtqAssectdao.updateHardAssectEntity(entity);
        session.commit();//增 删 改 之后 commit()
        closeSqlSession(session);
        return result;
    }

    public static boolean updateHardAssectEntity(HardAssectEntity entity,String machine_id,String belong_info1) throws SQLException {

        SqlSession session = getSqlSession();
        XTQAssectDao xtqAssectdao = session.getMapper(XTQAssectDao.class);
        Transaction transaction = newTrans(session);
        boolean result = false;
        try{
            boolean result1 = xtqAssectdao.updateHardAssectEntity(entity);
            boolean result2 = true;
            if(machine_id != null) {
                HardAssectMachineEntity mchety = new HardAssectMachineEntity();
                mchety.machine_entity_id = machine_id;
                mchety.entity_id = entity.entity_id;
                mchety.belong_info1 = belong_info1;
                //            mchety.entity_on_time = DateUtil.newDateStr();
                result2  =xtqAssectdao.updateMachineEty(mchety);
            }
            result = result1 && result2;
            if(!result){
                transaction.rollback();
            }
        }catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
        }finally {
            transaction.close();
        }
        return result;
    }
    public static boolean updateMachineEty(HardAssectMachineEntity mchety){
        SqlSession session = getSqlSession();
        XTQAssectDao xtqAssectdao = session.getMapper(XTQAssectDao.class);
        boolean result = xtqAssectdao.updateMachineEty(mchety);
        session.commit();//增 删 改 之后 commit()
        closeSqlSession(session);
        return result;
    }

    public static boolean addMachineEty(HardAssectMachineEntity entity){
        SqlSession session = getSqlSession();
        XTQAssectDao xtqAssectdao = session.getMapper(XTQAssectDao.class);
        boolean result = xtqAssectdao.addMachineEty(entity);
        session.commit();//增 删 改 之后 commit()
        closeSqlSession(session);
        return result;
    }

    /**
     *
     * @param entity
     * @return entity_id
     */
    public static String newEntity(HardAssectEntity entity) {
        String entity_id = SequenceService.nextSeq_entity_id();
        entity.entity_id = entity_id;
        if(entity.state == null || entity.state.equals("")) {
            entity.state = "2";
        }
        SqlSession session = getSqlSession();
        XTQAssectDao xtqAssectdao = session.getMapper(XTQAssectDao.class);
        boolean result = xtqAssectdao.addAssectEntity(entity);
        session.commit();//增 删 改 之后 commit()
        closeSqlSession(session);
        return entity_id;
    }

    public  static boolean newMachine(String  machineEntityId,String entity_on_time) {
        String belong_id = SequenceService.nextSeq_belong_id();
        SqlSession session = getSqlSession();
        XTQAssectDao xtqAssectdao = session.getMapper(XTQAssectDao.class);
        HardAssectMachineEntity entity = new HardAssectMachineEntity();
        entity.belong_id = belong_id;
        entity.entity_id = machineEntityId;
        entity.machine_entity_id = machineEntityId;
        entity.belong_info1 = "host";
        entity.entity_on_time = (entity_on_time == null || entity_on_time.equals("")) ? DateUtil.newDateStr() : entity_on_time;
        boolean result = xtqAssectdao.addMachineEty(entity);
        session.commit();//增 删 改 之后 commit()
        closeSqlSession(session);
        return result;
    }

    public static boolean updateServer(HardAssectServers server) {
        SqlSession session = getSqlSession();
        XTQAssectDao xtqAssectdao = session.getMapper(XTQAssectDao.class);
        boolean result = xtqAssectdao.updateServer(server);
        session.commit();//增 删 改 之后 commit()
        closeSqlSession(session);
        return result;
    }

    public static boolean expireEntity(String host_id,String machine_entity_id,String entity_id) throws SQLException {

        SqlSession session = getSqlSession();
        Transaction newTransaction = newTrans(session);
        XTQAssectDao xtqAssectdao = session.getMapper(XTQAssectDao.class);

        boolean result = false;
        try {
            V_HardDetails detail = selectExpireHardDetail(host_id, machine_entity_id, entity_id);

            HardAssectEntityHistory entityHistory = new HardAssectEntityHistory();
            entityHistory.setDetail(detail);
            entityHistory.setEntity_break_time(DateUtil.newDateStr());

            boolean result1 = xtqAssectdao.addEntityHistoty(entityHistory);
            boolean result2 = xtqAssectdao.deleteAssectEntity(entity_id);
            boolean result3 = xtqAssectdao.deleteMachineEty(machine_entity_id, entity_id);
            result = result1 && result2 && result3;
            if( !result){
                newTransaction.rollback();
            }
        }catch (Exception e){
            newTransaction.rollback();
            e.printStackTrace();
        }finally {
            newTransaction.close();
        }
        /*V_HardDetails detail = selectExpireHardDetail(host_id, machine_entity_id, entity_id);

        HardAssectEntityHistory entityHistory = new HardAssectEntityHistory();
        entityHistory.setDetail(detail);
        entityHistory.setEntity_break_time(DateUtil.newDateStr());

        boolean b1 = addEntityHistoty(entityHistory);
        boolean b2 = deleteAssectEntity(entity_id);
        boolean b3 = deleteMachineEty(machine_entity_id, entity_id);
        boolean result  = b1 && b2 && b3;*/
        return result;
    }

    public static boolean deleteAssectEntity(String entity_id) {
        SqlSession session = getSqlSession();
        XTQAssectDao xtqAssectdao = session.getMapper(XTQAssectDao.class);
        boolean result = xtqAssectdao.deleteAssectEntity(entity_id);
        session.commit();//增 删 改 之后 commit()
        closeSqlSession(session);
        return result;
    }

    public static boolean deleteMachineEty(String machine_entity_id,String entity_id) {
        SqlSession session = getSqlSession();
        XTQAssectDao xtqAssectdao = session.getMapper(XTQAssectDao.class);
        boolean result = xtqAssectdao.deleteMachineEty(machine_entity_id, entity_id);
        session.commit();//增 删 改 之后 commit()
        closeSqlSession(session);
        return result;
    }

    /**
     *  新增Host
     * @param host_id
     * @param category_id
     * @param type_id
     * @param entity_info1
     * @param entity_info2
     * @param entity_info3
     * @return boolean
     */
    public static String newHost(String host_id,String category_id,String type_id,
                                  String entity_info1,String entity_info2,String entity_info3) throws SQLException {
        boolean result = false;
        HardAssectEntity entity = new HardAssectEntity();
        entity.type_id = type_id;
        entity.category_id = category_id;
        entity.entity_info1 = entity_info1;
        entity.entity_info2 = entity_info2;
        entity.entity_info3 = entity_info3;
        SqlSession session = getSqlSession();
        XTQAssectDao xtqAssectdao = session.getMapper(XTQAssectDao.class);
        Transaction transaction = newTrans(session);
        String entity_id = null;
        try{
                entity_id = SequenceService.nextSeq_entity_id();
                entity.entity_id = entity_id;
                if(entity.state == null || entity.state.equals("")) {
                    entity.state = "2";
                }
                boolean result1 = xtqAssectdao.addAssectEntity(entity);

                String machineEntityId = entity_id;
                String entity_on_time = null;
                String belong_id = SequenceService.nextSeq_belong_id();
                HardAssectMachineEntity machety = new HardAssectMachineEntity();
                machety.belong_id = belong_id;
                machety.entity_id = machineEntityId;
                machety.machine_entity_id = machineEntityId;
                machety.belong_info1 = "host";
                machety.entity_on_time = (entity_on_time == null || entity_on_time.equals("")) ? DateUtil.newDateStr() : entity_on_time;
                boolean result2 = xtqAssectdao.addMachineEty(machety);

                HardAssectServers server = new HardAssectServers();
                server.host_id = host_id;
                server.machine_entity_id = entity_id;
//                AssectServerDetailService.updateServer(server);
                boolean result3 = xtqAssectdao.updateServer(server);

                result = result1 && result2 && result3;
                if(!result){
                    transaction.rollback();
                    entity_id = null;
                }
        }catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
        }finally {
            transaction.close();
        }

        return entity_id;
    }

    public static boolean newEntityToHost(String machineId,String category_id,String type_id,String entity_info1,String entity_info2,String entity_info3,String belong_info1)
            throws SQLException {
        SqlSession session = getSqlSession();
        XTQAssectDao xtqAssectdao = session.getMapper(XTQAssectDao.class);
        Transaction transaction = newTrans(session);
        boolean result  = false;
        try{

                HardAssectEntity entity = new HardAssectEntity();
                entity.type_id = type_id;
                entity.category_id = category_id;
                entity.entity_info1 = entity_info1;

        //        String entity_id = AssectServerDetailService.newEntity(entity);
                String entity_id = SequenceService.nextSeq_entity_id();
                entity.entity_id = entity_id;
                if(entity.state == null || entity.state.equals("")) {
                    entity.state = "2";
                }
                boolean result1 = xtqAssectdao.addAssectEntity(entity);

                HardAssectMachineEntity mchety = new HardAssectMachineEntity();
                mchety.belong_id = SequenceService.nextSeq_belong_id();
                mchety.machine_entity_id  = machineId;
                mchety.entity_id = entity_id;
                mchety.belong_info1 = belong_info1;
                mchety.entity_on_time = DateUtil.newDateStr();
        //        boolean result = AssectServerDetailService.addMachineEty(mchety);
                boolean result2 = xtqAssectdao.addMachineEty(mchety);

                result = result1 && result2;
                if(!result){
                    transaction.rollback();
                }

        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
        }finally {
            transaction.close();
        }
        return result;
    }

    public static  void transactionTest() throws SQLException {

        SqlSession session = getSqlSession();
        Transaction newTransaction = newTrans(session);

        XTQAssectDao xtqAssectdao = session.getMapper(XTQAssectDao.class);

        String host_id = "1";
        String machine_entity_id = "121";
        String entity_id = "190";
        boolean result = false;
        try {
            V_HardDetails detail = selectExpireHardDetail(host_id, machine_entity_id, entity_id);

            HardAssectEntityHistory entityHistory = new HardAssectEntityHistory();
            entityHistory.setDetail(detail);
            entityHistory.setEntity_break_time(DateUtil.newDateStr());

            boolean result1 = xtqAssectdao.addEntityHistoty(entityHistory);
            boolean result2 = xtqAssectdao.deleteAssectEntity(entity_id);
            boolean result3 = xtqAssectdao.deleteMachineEty(machine_entity_id, entity_id);
            result = result1 && result2 && result3;
            if( !result){
                newTransaction.rollback();
            }
        }catch (Exception e){
            newTransaction.rollback();
            e.printStackTrace();
        }finally {
            newTransaction.close();
        }
    }
}
