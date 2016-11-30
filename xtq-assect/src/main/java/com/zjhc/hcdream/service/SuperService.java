package com.zjhc.hcdream.service;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;

/**
 * Desc:
 * email: zhangqj@zjhcsoft.com
 * Created by ZhangQingJing on 2015/12/22 9:04
 */
public class SuperService {
    private static SqlSessionFactory sqlSessionFactory;
    private static Reader reader;
    private static JdbcTransactionFactory transactionFactory;

    static{
        try{
            reader    = Resources.getResourceAsReader("orm_xtq_ast.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
             transactionFactory = new JdbcTransactionFactory();
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

    public static Transaction newTrans(SqlSession session){
        return transactionFactory.newTransaction(session.getConnection());
    }

}
