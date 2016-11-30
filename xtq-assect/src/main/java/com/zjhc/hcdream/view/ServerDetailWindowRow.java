package com.zjhc.hcdream.view;

import java.util.List;

/**
 * Desc:
 * email: zhangqj@zjhcsoft.com
 * Created by ZhangQingJing on 2016/1/4 23:24
 */
public class ServerDetailWindowRow {
    public final static String TYPE_BASIC = "basic";
    public final static String TYPE_SELECT = "select";
    public final static String TYPE_SELECT_NO_EVENT= "selectNoEvent";
    public final static String TYPE_READ = "read";
    public final static String TYPE_TEXT = "text";
    public final static String BELONG_SELECT_1 = "belong_select_1";

    public String labelName;
    public String typeName;
    public String id;
    public String value;
    public String select_level;
    public String select_id1;
    public String select_id2;
    public List<String> selectValueList;
    public String readTypeVersionMethod;
    public String readTypeInfoMethod;
    public String readIdArr;

    public List<String> getSelectValueList() {
        return selectValueList;
    }

    public void setSelectValueList(List<String> selectValueList) {
        this.selectValueList = selectValueList;
    }

    public void initReadIdArr(String[] readIds) {
       /* StringBuilder sb= new StringBuilder("new Array(");
        for (int i = 0; i < readIds.length; i++) {
            if (i == readIds.length - 1) {
                sb.append("'" + readIds[i] + "');");
            }else{
                sb.append("'" + readIds[i] + "',");
            }
        }*/
         StringBuilder sb= new StringBuilder();
        for (int i = 0; i < readIds.length; i++) {
            if (i == readIds.length - 1) {
                sb.append(readIds[i]);
            }else{
                sb.append(readIds[i] + ",");
            }
        }
        readIdArr = sb.toString();
    }

    public  void setTypeBasic() {
        typeName = TYPE_BASIC;
    }

    public  void setTypeSelect() {
        typeName = TYPE_SELECT;
    }

    public  void setTypeRead() {
        typeName = TYPE_READ;
    }

    public  void setTypeText() {
        typeName = TYPE_TEXT;
    }

    public  void setTypeBelongSelect1() {
        typeName = BELONG_SELECT_1;
    }

    public  void setTypeSelectNoEvent() {
        typeName = TYPE_SELECT_NO_EVENT;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSelect_level() {
        return select_level;
    }

    public void setSelect_level(String select_level) {
        this.select_level = select_level;
    }

    public String getSelect_id1() {
        return select_id1;
    }

    public void setSelect_id1(String select_id1) {
        this.select_id1 = select_id1;
    }

    public String getSelect_id2() {
        return select_id2;
    }

    public void setSelect_id2(String select_id2) {
        this.select_id2 = select_id2;
    }

    public String getReadTypeVersionMethod() {
        return readTypeVersionMethod;
    }

    public void setReadTypeVersionMethod(String readTypeVersionMethod) {
        this.readTypeVersionMethod = readTypeVersionMethod;
    }

    public String getReadTypeInfoMethod() {
        return readTypeInfoMethod;
    }

    public void setReadTypeInfoMethod(String readTypeInfoMethod) {
        this.readTypeInfoMethod = readTypeInfoMethod;
    }

    public String getReadIdArr() {
        return readIdArr;
    }

    public void setReadIdArr(String readIdArr) {
        this.readIdArr = readIdArr;
    }
}
