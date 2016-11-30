package com.zjhc.hcdream.service;

import com.zjhc.hcdream.dao.SequenceDao;
import org.apache.ibatis.session.SqlSession;

/**
 * Desc:
 * email: zhangqj@zjhcsoft.com
 * Created by ZhangQingJing on 2015/12/22 9:20
 */
public class SequenceService extends SuperService{

    public static  String nextSeq_belong_id() {
        SqlSession session = getSqlSession();
        SequenceDao sequenceDao = session.getMapper(SequenceDao.class);
        String id= sequenceDao.nextSeq_belong_id();
        closeSqlSession(session);
        return id;
    }

    public static  String nextSeq_category_id() {
        SqlSession session = getSqlSession();
        SequenceDao sequenceDao = session.getMapper(SequenceDao.class);
        String id= sequenceDao.nextSeq_category_id();
        closeSqlSession(session);
        return id;
    }

    public static  String nextSeq_combined_id() {
        SqlSession session = getSqlSession();
        SequenceDao sequenceDao = session.getMapper(SequenceDao.class);
        String id= sequenceDao.nextSeq_combined_id();
        closeSqlSession(session);
        return id;
    }

  /*  public static  String nextSeq_host_id( ) {
        SqlSession session = getSqlSession();
        SequenceDao sequenceDao = session.getMapper(SequenceDao.class);
        String id= sequenceDao.nextSeq_host_id();
        closeSqlSession(session);
        return id;
    }*/

    public static  String nextSeq_mhost_id() {
        SqlSession session = getSqlSession();
        SequenceDao sequenceDao = session.getMapper(SequenceDao.class);
        String id= sequenceDao.nextSeq_mhost_id();
        closeSqlSession(session);
        return "m" + id;
    }

    public static  String nextSeq_type_id() {
        SqlSession session = getSqlSession();
        SequenceDao sequenceDao = session.getMapper(SequenceDao.class);
        String id= sequenceDao.nextSeq_type_id();
        closeSqlSession(session);
        return id;
    }

    public static  String nextSeq_entity_id() {
        SqlSession session = getSqlSession();
        SequenceDao sequenceDao = session.getMapper(SequenceDao.class);
        String id= sequenceDao.nextSeq_entity_id();
        closeSqlSession(session);
        return id;
    }

    public static  String nextSeq_combined_type_id() {
        SqlSession session = getSqlSession();
        SequenceDao sequenceDao = session.getMapper(SequenceDao.class);
        String id= sequenceDao.nextSeq_combined_type_id();
        closeSqlSession(session);
        return id;
    }
}
