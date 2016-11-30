package com.zjhc.hcdream.service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.zjhc.hcdream.dao.XTQAssectDao;
import com.zjhc.hcdream.model.HardAssectCategory;
import com.zjhc.hcdream.model.HardAssectType;
import com.zjhc.hcdream.model.XTQAmbariServer;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * Desc:
 * email: zhangqj@zjhcsoft.com
 * Created by ZhangQingJing on 2015/12/22 9:20
 */
public class AssectCategoryService extends SuperService{

    /**
     * 主机总览
     * @return
     */
    //都是mybait  @return 是返回列表
    //服务， @param pageBounds 分页对象  V_HardServerSummary----对应的是CategoryTypeManager---不知道是什么

    //定义了三个类,不知道是干啥的
  //  public static  List<V_HardServerSummary> selectServerSummary(PageBounds pageBounds) {
    public static  List<HardAssectCategory> selectAsssectCategory() {
        //与数据库建立一个会话
        //在 MyBatis 中,你可以使用 SqlSessionFactory 来创建 SqlSession。一旦你获得一个 session 之后,你可以使用它来执行映射语句,提交或回滚连接,最后,当不再需要它的时 候, 你可以关闭 session
        SqlSession session = getSqlSession();
        //在model中定义了映射语句
        // SqlSessionTemplate 是线程安全的, 可以被多个 DAO 所共享使用

        //调用Dao中的类
        //XTQAssectDao serverDao = session.getMapper(XTQAssectDao.class);
        XTQAssectDao xtqAssectdao = session.getMapper(XTQAssectDao.class);

        //使用列
        //List<V_HardServerSummary> userList = serverDao.selectServerSummary(pageBounds);
        List<HardAssectCategory> userList = xtqAssectdao.selectAsssectCategory();
        closeSqlSession(session);
        return userList;
    }

    public static  List<HardAssectType> selectAsssectType() {
        SqlSession session = getSqlSession();
        XTQAssectDao xtqAssectdao = session.getMapper(XTQAssectDao.class);
        List<HardAssectType> userList = xtqAssectdao.selectAsssectType();
        closeSqlSession(session);
        return userList;
    }


/*  不太清楚，先不写了
    public static V_HardDetails selectHardDetailHost(String host_id,String category_id){
        SqlSession session = getSqlSession();
        XTQAssectDao serverDao = session.getMapper(XTQAssectDao.class);
        V_HardDetails hardDetail = serverDao.selectHardDetailHost(host_id,category_id);
        closeSqlSession(session);
        return hardDetail;
    }
*/


    public static boolean newCategory(String category_id, String category_name){
        HardAssectCategory category = new HardAssectCategory();
        category.category_id=category_id;
        category.category_name=category_name;
        SqlSession session = getSqlSession();
        XTQAssectDao xtqAssectdao = session.getMapper(XTQAssectDao.class);
        boolean result = xtqAssectdao.addCategory(category);
        session.commit();//增 删 改 之后 commit()
//        Transac
        closeSqlSession(session);
        return result;
    }


    public static String newType(HardAssectType xtqtype) {
        String result = "ok";
        try {
            SqlSession session = getSqlSession();
            XTQAssectDao serverDao = session.getMapper(XTQAssectDao.class);
            serverDao.addXTQAmbaritype(xtqtype);
            session.commit();
            closeSqlSession(session);
        } catch (Exception e) {
            e.printStackTrace();
            result = e.getMessage();
        } finally {
            return result;
        }
    }

    public static String changeType(HardAssectType xtqtype) {
        String result = "ok";
        try {
            SqlSession session = getSqlSession();
            XTQAssectDao serverDao = session.getMapper(XTQAssectDao.class);
            serverDao.updateHardAssectType(xtqtype);
            session.commit();
            closeSqlSession(session);
        } catch (Exception e) {
            e.printStackTrace();
            result = e.getMessage();
        } finally {
            return result;
        }
    }



}
