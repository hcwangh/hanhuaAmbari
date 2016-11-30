
/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zjhc.hcdream.util;

import java.util.ArrayList;
import java.util.List;
/*
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;*/

/**
 * Desc:
 * email: zhangqj@zjhcsoft.com
 * Created by ZhangQingJing on 2015/7/5 1:20
 */
public class Flex {

    private int page=1;
    private int total=0;
    private List<RowData> rows=new ArrayList<RowData>();

    private String rowid=null;
    private List<String> coldatas=null;

    /**
     * @return
     */
    public int getPage() {
        return page;
    }
    /**
     * 设置页码。
     * @param page
     */
    public void setPage(int page) {
        this.page = page;
    }


    public int getTotal() {
        return this.total;
    }

    /**
     * 设置总记录数
     * @param total
     */
    public void setTotal(int total) {
        this.total = total;
    }


    public List<RowData> getRows() {
        return rows;
    }



    public void setRows(List<RowData> rows) {
        this.rows = rows;
    }


    public void addRow(String rowid,List<String> coldatas){
        RowData rd=new RowData(rowid,coldatas);
        this.rows.add(rd);
    }

    /**
     * 设置每一行的id。
     * 配合addColdata(),commitData()方法是用。
     * 例：setRowId("row1");
     *     addColdata("1");
     *     addColdata("2");
     *
     *     setRowId("row2");
     *     addColdata("a");
     *     addColdata("b");
     *
     *     commitData();
     *   表示 1，2两个数据都为行row1中第一列，第二列的数据。
     *  a,b 两个数据都为row2中第一列，第二列的数据。
     *  commitData()的调用表示，row2行的数据已经组织完成。
     *
     *  在设置row2行的数据时，会自动提交row1行的数据。
     *
     * @param rowid
     */
    public void setRowId(String rowid){
        commitData();
        this.rowid=rowid;
        this.coldatas=null;
    }

    /**
     * 该方法配合setRowId和commitData()使用。该方法必须在调用setRowId()后调用，否则会抛出NullPointerException
     * 请参考setRowId的说明
     * @param coldata 每一列数据
     */
    public void addColdata(String coldata){
        if(rowid==null) throw new NullPointerException("please set rowid");
        if(coldatas==null) coldatas=new ArrayList<String>();

        coldatas.add(coldata);
    }

    public void commitData(){
        if(this.rowid!=null && this.coldatas!=null){
            addRow(this.rowid, this.coldatas);
            this.rowid=null;
            this.coldatas=null;
        }
    }


    /*
     * 这里生成的是符合flexgrid for jquery 的json格式字符串
     * 其格式如下：
     * {
        page:1,
        total:0,
        rows:[
          {id:'row2',cell:['col','col','col','col','col','col']},
          {id:'row3',cell:['col','col','col','col','col','col']},
          {id:'row1',cell:['col','col','col','col','col','col']}
           ]
        }
     */
    public String toString(){
        StringBuffer sb=new StringBuffer();
        sb.append("{");
        sb.append("page:").append(page).append(",");
        sb.append("total:").append(total).append(",");
        sb.append("rows:[");

        int keynum=1;
        List<RowData> rowdatalist=this.rows;
        for(RowData rowdata:rowdatalist){
//            sb.append("{id:'").append(rowdata.getRowid()).append("',").append("cell:[");
            sb.append("{cell:[");
            int i=1;
            List<String> coldatalist=rowdata.getColdata();
            for(String data:coldatalist){
                sb.append("'").append(data).append("'");
                if(i<coldatalist.size()){
                    sb.append(",");
                }
                i++;
            }

            if(keynum<rowdatalist.size()){
                sb.append("]},");
            }else{
                sb.append("]}");
            }

            keynum++;
        }
        sb.append("]}");
        return sb.toString();
    }

    public class RowData{
        String rowid=null;
        List<String> coldata=null;

        public RowData(){

        }

        public RowData(String rowid,List<String> coldata){
            this.rowid=rowid;
            this.coldata=coldata;
        }

        public List<String> getColdata() {
            return coldata;
        }
        public String getRowid() {
            return rowid;
        }
        public void setColdata(List<String> coldata) {
            this.coldata = coldata;
        }
        public void setRowid(String rowid) {
            this.rowid = rowid;
        }
    }
    public static String getTestJsonData(){
        ArrayList<String> list=new ArrayList<String>();
        list.add("col");
        list.add("col");
        list.add("col");
        list.add("col");
        list.add("col");
        list.add("col");

        Flex fgjd=new Flex();
        fgjd.setRowId("row1");
        fgjd.addColdata("cols");
        fgjd.addColdata("cols");
        fgjd.addColdata("cols");
        fgjd.addColdata("cols");
        fgjd.addColdata("cols");
        fgjd.addColdata("cols");
        fgjd.addColdata("cols");
        fgjd.addColdata("cols");

        fgjd.setRowId("row2");
        fgjd.addColdata("cols");
        fgjd.addColdata("cols");
        fgjd.addColdata("cols");
        fgjd.addColdata("cols");
        fgjd.addColdata("cols");
        fgjd.addColdata("cols");
        fgjd.addColdata("cols");
        fgjd.addColdata("cols");

        fgjd.setRowId("row3");
        fgjd.addColdata("cols");
        fgjd.addColdata("cols");
        fgjd.addColdata("cols");
        fgjd.addColdata("cols");
        fgjd.addColdata("cols");
        fgjd.addColdata("cols");
        fgjd.addColdata("cols");
        fgjd.addColdata("cols");

        fgjd.commitData();
        return fgjd.toString();
    }
   /* public static String getJsonTestData1(){
        JSONArray items = new JSONArray();
        for (int i = 0; i < 100; i++) {
            JSONObject item = new JSONObject();
            item.put("cell", new Object[] {
                    "col",
                    "col",
                    "col",
                    "col",
                    "col",
                    "col",
                    "col",
                    "col"
            });
            items.add(item);
        }
        JSONObject json = new JSONObject();
        json.put("page", 1);
        json.put("total", 3);
        json.put("rows", items);
        return json.toString();
    }*/
    public static void main(String args[]){
//        System.out.println(getTestJsonData());
    }
}
