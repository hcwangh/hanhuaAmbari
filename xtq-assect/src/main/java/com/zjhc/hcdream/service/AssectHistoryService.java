package com.zjhc.hcdream.service;

import java.util.List;

import com.zjhc.hcdream.model.HardAssectEntityHistory_s;
import org.apache.ibatis.session.SqlSession;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.zjhc.hcdream.dao.XTQAssectDao;
import com.zjhc.hcdream.model.V_HardBroken;

/**
 * Desc:
 * email: zhangqj@zjhcsoft.com
 * Created by ZhangQingJing on 2016/1/11 1:16
 */
public class AssectHistoryService extends SuperService {

    public static List<V_HardBroken> selectBrokenStat(PageBounds page) {
        SqlSession session = getSqlSession();
        XTQAssectDao xtqAssectdao = session.getMapper(XTQAssectDao.class);
        List<V_HardBroken> list = xtqAssectdao.selectBrokenStat(page);
        closeSqlSession(session);
        return list;
    }

    public static List<HardAssectEntityHistory_s> selectEntityHistoryList(PageBounds page) {
        SqlSession session = getSqlSession();
        XTQAssectDao xtqAssectdao = session.getMapper(XTQAssectDao.class);
        List<HardAssectEntityHistory_s> list = xtqAssectdao.selectEntityHistoryList(page);
        closeSqlSession(session);
        return list;
    }
}
