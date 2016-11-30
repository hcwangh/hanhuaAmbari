package com.zjhc.hcdream.dao;

import org.apache.ibatis.annotations.Select;

/**
 * Desc:
 * email: zhangqj@zjhcsoft.com
 * Created by ZhangQingJing on 2016/1/4 20:44
 */
public interface SequenceDao {
    @Select("select nextval('seq_category_id') as id;")
    String nextSeq_category_id();

    @Select("select nextval('seq_type_id') as id;")
    String nextSeq_type_id();

    @Select("select nextval('seq_entity_id') as id;")
    String nextSeq_entity_id();

    @Select("select nextval('seq_combined_id') as id;")
    String nextSeq_combined_id();

    @Select("select nextval('seq_combined_type_id') as id;")
    String nextSeq_combined_type_id();

    @Select("select nextval('seq_belong_id') as id;")
    String nextSeq_belong_id();

    @Select("select nextval('seq_host_id') as id;")
    String nextSeq_host_id();

    @Select("select nextval('seq_mhost_id') as id;")
    String nextSeq_mhost_id();
}
