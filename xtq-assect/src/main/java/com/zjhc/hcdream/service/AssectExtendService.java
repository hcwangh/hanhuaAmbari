package com.zjhc.hcdream.service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.zjhc.hcdream.dao.XTQAssectDao;
import com.zjhc.hcdream.model.V_HardBroken;
import com.zjhc.hcdream.model.V_HardDetails;
import com.zjhc.hcdream.model.V_HardExtend;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * Desc:
 * email: zhangqj@zjhcsoft.com
 * Created by ZhangQingJing on 2016/1/11 1:16
 */
public class AssectExtendService extends SuperService {

    public static List<V_HardExtend> selectV_HardExtendList(PageBounds page) {
        SqlSession session = getSqlSession();
        XTQAssectDao xtqAssectdao = session.getMapper(XTQAssectDao.class);
        List<V_HardExtend> list = xtqAssectdao.selectV_HardExtendList(page);
        closeSqlSession(session);
        return list;
    }
}
