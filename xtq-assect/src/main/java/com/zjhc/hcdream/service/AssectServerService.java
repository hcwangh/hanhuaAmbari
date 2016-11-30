package com.zjhc.hcdream.service;

import java.util.List;
import com.zjhc.hcdream.dao.XTQAssectDao;
import com.zjhc.hcdream.model.HardAssectEntityHistory;
import com.zjhc.hcdream.model.V_HardDetails;
import com.zjhc.hcdream.model.V_HardServerSummary;
import com.zjhc.hcdream.model.XTQAmbariServer;
import com.zjhc.hcdream.util.DateUtil;
import org.apache.ibatis.session.SqlSession;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.apache.ibatis.transaction.Transaction;

/**
 * Desc:
 * email: zhangqj@zjhcsoft.com
 * Created by ZhangQingJing on 2015/12/22 9:20
 */
public class AssectServerService extends SuperService{

    /**
     * 主机总览
     * @param pageBounds
     * @return
     */
    public static  List<V_HardServerSummary> selectServerSummary(PageBounds pageBounds) {
        SqlSession session = getSqlSession();
        XTQAssectDao serverDao = session.getMapper(XTQAssectDao.class);
        List<V_HardServerSummary> userList = serverDao.selectServerSummary(pageBounds);
        closeSqlSession(session);
        return userList;
    }

    public static V_HardDetails selectHardDetailHost(String host_id,String category_id){
        SqlSession session = getSqlSession();
        XTQAssectDao serverDao = session.getMapper(XTQAssectDao.class);
        V_HardDetails hardDetail = serverDao.selectHardDetailHost(host_id, category_id);
        closeSqlSession(session);
        return hardDetail;
    }

    public static boolean expireHost(String host_id) throws Exception {
        SqlSession session = getSqlSession();
        XTQAssectDao xtqDao = session.getMapper(XTQAssectDao.class);
        Transaction transaction = newTrans(session);
        boolean result = false;
        try {
            //查询并删除依赖关系
            String machine_entity_id = xtqDao.selectMchIdFromServer(host_id);

            List<String> entityIds = xtqDao.selectEtyIdsFromMchEty(machine_entity_id);

            boolean result1 = true;
            for (String entityId : entityIds) {
                V_HardDetails detail = xtqDao.selectExpireHardDetail(host_id, machine_entity_id, entityId);
                HardAssectEntityHistory entityHistory = new HardAssectEntityHistory();
                entityHistory.setDetail(detail);
                entityHistory.setEntity_break_time(DateUtil.newDateStr());
                boolean b1 = xtqDao.addEntityHistoty(entityHistory);
                boolean b2 = xtqDao.deleteMachineEty(machine_entity_id, entityId);
                boolean b3 = xtqDao.deleteAssectEntity(entityId);
                if( (b1&& b2&&b3) == false){
                    result1 = false;
                    break;
                }
            }
            boolean result3 = xtqDao.deleteServer(host_id);
            result = result1  && result3;
            if(!result){
                transaction.rollback();
            }
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            transaction.close();
        }
        return result;
    }

    /**
     * 主机发现
     * 同步数据到资产表: hard_assect_servers
     * 即 :  从哮天犬Ambari源数据库 查询hosts表  -  资产表 = insert 到 资产表的 数据
     */
    public static String refreshSlaves() {
        String result = "ok";
        try {
            List<XTQAmbariServer> xtqServers = XTQAmbariService.selectXTQAmbariServers();
            List<V_HardServerSummary> nowServers = selectServerSummary(new PageBounds());

            xtqServers.removeAll(nowServers);
            SqlSession session = getSqlSession();
            XTQAssectDao serverDao = session.getMapper(XTQAssectDao.class);
            for (XTQAmbariServer xtqServer : xtqServers) {
                serverDao.addXTQAmbariServer(xtqServer);
            }
            session.commit();
            closeSqlSession(session);
        } catch (Exception e) {
            e.printStackTrace();
            result = e.getMessage();
        } finally {
            return result;
        }
    }

    /**
     * 主机发现
     * 同步数据到资产表: hard_assect_servers
     * 即 :  从哮天犬Ambari源数据库 查询hosts表  -  资产表 = insert 到 资产表的 数据
     */
    public static String newHostServer(XTQAmbariServer xtqServer) {
        String result = "ok";
        try {
            SqlSession session = getSqlSession();
            XTQAssectDao serverDao = session.getMapper(XTQAssectDao.class);
            serverDao.addXTQAmbariServer(xtqServer);
            session.commit();
            closeSqlSession(session);
        } catch (Exception e) {
            e.printStackTrace();
            result = e.getMessage();
        } finally {
            return result;
        }
    }

    public static void main(String[] args) {
        try {
            expireHost("m103");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
