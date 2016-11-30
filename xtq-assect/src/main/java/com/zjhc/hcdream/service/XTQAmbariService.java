package com.zjhc.hcdream.service;

import com.zjhc.hcdream.dao.XTQAssectDao;
import com.zjhc.hcdream.model.XTQAmbariServer;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * Desc:
 * email: zhangqj@zjhcsoft.com
 * Created by ZhangQingJing on 2015/12/28 14:00
 */
public class XTQAmbariService {
    private static SqlSessionFactory sqlSessionFactory;
    private static Reader reader;

    static{
        try{
            reader    = Resources.getResourceAsReader("orm_ambari.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static SqlSession getSqlSession(){
        SqlSession session = sqlSessionFactory.openSession();
        return session;
    }

    public static void closeSqlSession(SqlSession session){
        session.close();
    }

    public static List<XTQAmbariServer> selectXTQAmbariServers() {
        SqlSession session = getSqlSession();
        XTQAssectDao serverDao = session.getMapper(XTQAssectDao.class);
        List<XTQAmbariServer> servers = serverDao.selectXTQAmbariServers();
        closeSqlSession(session);
        return servers;
    }

}
