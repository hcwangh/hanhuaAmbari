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

/**
 * Desc:
 * email: zhangqj@zjhcsoft.com
 * Created by ZhangQingJing on 2015/7/10 11:09
 */
public class Page {

    public static int DEFAULT_PAGESIZE = 15;

    private int pagesizeValue = 15;

    private int pageno; // 当前页码

    private int pagesize;// 每页记录数

    private int total; // 总页数

    private int totalrows; // 总记录数

    private int start; // 起始位置

    private int end; // 结束位置

    private int nextPageNo;// 下页页码

    private int prePageNo;// 上页页码

    private String EntityName;

    private String selectPart = "";

    public Page() {
        setTotal(1);
        setPageno(1);
    }

    public String getEntityName() {
        return EntityName;
    }

    public void setEntityName(String entityName) {
        EntityName = entityName;
    }

    public void addSelectCol(String col){
        if(selectPart.equals("")){
            selectPart = " " + col;
        }else{
            selectPart = selectPart + ", " +  col;
        }
    }

    public String getSelectPart() {
        return selectPart;
    }

    public void setSelectPart(String selectPart) {
        this.selectPart = selectPart;
    }

    public void clearSelectCol(){
        this.selectPart = "";
    }
    public int getTotalrows() {
        return totalrows;
    }

    public void setTotalrows(int totalrows) {
        this.totalrows = totalrows;
    }

    public int getPageno() {
        return pageno;
    }

    public int getTotal() {
        return total;
    }

    public void setPageno(int i) {
        pageno = i;
    }

    public void setTotal(int i) {
        total = i;
    }

    public void init() {
        if (pageno <= 0) {
            pageno = 1;
        }
        if (pagesize <= 0) {
            pagesize = DEFAULT_PAGESIZE;
            if (pagesizeValue != 15) {
                pagesize = pagesizeValue;
            }
        }
        int start = 1;
        if (totalrows <= (pageno - 1) * pagesize) {
            if (totalrows % pagesize == 0) {
                pageno = totalrows / pagesize;
            } else {
                pageno = totalrows / pagesize + 1;
            }
        }
        if (pageno <= 0) {
            pageno = 1;
        }
        start = pagesize * (pageno - 1);
        int totalpage = 1;
        if (totalrows % pagesize == 0) {
            totalpage = totalrows / pagesize;
        } else {
            totalpage = totalrows / pagesize + 1;
        }
        total = totalpage;
        this.start = start;
        this.end = start + pagesize;
        this.nextPageNo = pageno + 1;
        this.prePageNo = pageno - 1;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getNextPageNo() {
        return nextPageNo;
    }

    public void setNextPageNo(int nextPageNo) {
        this.nextPageNo = nextPageNo;
    }

    public int getPrePageNo() {
        return prePageNo;
    }

    public void setPrePageNo(int prePageNo) {
        this.prePageNo = prePageNo;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    // 第一页
    private String getFirst() {
        String result = "";
        if (pageno != 1) {
            result += "<a href=\"javascript:void(0);\" class=\"c\" onclick=\"doPage(1);return false;\">首页</a>";
        }
        return result;
    }

    // 上一页
    private String getPrev() {
        String result = "";
        int prev = pageno - 1;
        if (!(pageno == 1 || prev <= 0)) {
            result += "<a href=\"javascript:void(0);\" class=\"c\" onclick=\"doPage(" + prev + ");return false;\">上一页</a>";
        }
        else {
            result += "<a class=\"e\">上一页</a>";
        }
        return result;
    }

    // 中间页码
    private String getMiddle() {
        StringBuffer result = new StringBuffer();
        int showPages = 9;
        if (total <= showPages)// 实际总页数<=想显示的页数
            for (int i = 1; i <= total; i++) {
                result.append("<a href=\"javascript:void(0);\" onclick=\"doPage(" + String.valueOf(i) + ");return false;\"");
                if (i == pageno)// 如果是当前页
                    result.append(" class=\"d\" >" + i + "</a>");
                else
                    // 如果不是当前页
                    result.append(" class=\"c\">" + i + "</a>");
            }
        else if (total > showPages) {// 实际总页数大于页面总页数
            if (pageno > (showPages + 1) / 2) {// 当前页大于页面显示的页数的一半，如123456789，则当前页为6789
                // 下面主要判断，如果当前页大于页面显示的页数的一半时，考虑当前页是否在页面显示的中间
                // 如123456789总共页数为15当你点第8页时，分页应该4 5 6 7 [8] 9 10 11 12
                // 如果点了12页则分页应为7 8 9 10 11 [12] 13 14 15因为后面没有了所以第12没在中间
                if (pageno <= total - showPages / 2)// 当前页大于页面显示的页数的一半，且最后页数够，显示的分页
                    for (int i = 1; i <= showPages; i++) {
                        result.append("<a href=\"javascript:void(0);\" onclick=\"doPage(" + String.valueOf(pageno + i - (showPages + 1) / 2) + ");return false;\"");
                        if ((pageno + i - (showPages + 1) / 2) == pageno)
                            result.append(" class=\"d\" >" + (pageno + i - (showPages + 1) / 2) + "</a>");
                        else
                            result.append(" class=\"c\" >" + (pageno + i - (showPages + 1) / 2) + "</a>");
                    }
                else if (pageno > total - showPages / 2)// 当前页大于页面显示的页数的一半，最后页数不够，显示的分页
                    for (int i = 1; i <= showPages; i++) {
                        result.append("<a href=\"javascript:void(0);\" onclick=\"doPage(" + String.valueOf(pageno - (showPages - (total - pageno)) + i) + ");return false;\"");// pageno-(showPages-(total-pageno))+i
                        if ((pageno - (showPages - (total - pageno)) + i) == pageno)
                            result.append(" class=\"d\" >" + (pageno - (showPages - (total - pageno)) + i) + "</a>");
                        else
                            result.append(" class=\"c\" >" + (pageno - (showPages - (total - pageno)) + i) + "</a>");
                    }
            } else if (pageno <= (showPages + 1) / 2)// 当前页小于页面显示的页数的一半，如123456789，则当前页为12345
                for (int i = 1; i <= showPages; i++) {
                    result.append("<a href=\"javascript:void(0);\" onclick=\"doPage(" + String.valueOf(i) + ");return false;\"");
                    if (i == pageno)
                        result.append(" class=\"d\" >" + i + "</a>");
                    else
                        result.append(" class=\"c\" >" + i + "</a>");
                }
        }
        return result.toString();
    }

    // 下一页
    private String getNext() {
        String result = "";
        int next = pageno + 1;
        if (!(pageno == total || next > total)) {
            result += "<a href=\"javascript:void(0)\" class=\"c\" onclick=\"doPage(" + next + ");return false;\">下一页</a>";
        }
        else {
            result += "<a class=\"e\">下一页</a>";
        }
        return result;
    }

    // 最后一页
    private String getLast() {
        String result = "";
        if (pageno != total && totalrows > 0) {
            result += "<a href=\"javascript:void(0)\" class=\"c\" onclick=\"doPage(" + total + ");return false;\">尾页</a>";
        }
        return result;
    }

    public String toHtmlText() {
        String content = "";
        content += getPrev() + getMiddle() + getNext() + "&nbsp;&nbsp;";
        return content;
    }

    //包括首页和尾页
    public String toFullHtmlText(){
        String content = "";
        content += getFirst() + getPrev() + getMiddle() + getNext() + getLast();
        return content;
    }

    public String toPrevText() {
        String content = "";
        content += getPrev();
        return content;
    }

    public String toNextText() {
        String content = "";
        content += getNext();
        return content;
    }

    /**
     * 获取增加分页功能的SQL语句
     * @return
     */
    public String getPagedSql(String sql) {
        StringBuffer newSql = new StringBuffer();

        newSql.append(" SELECT ROWALL.* FROM ");
        newSql.append(" (SELECT ROW_.*, ROWNUM AS NUM_  FROM ( ");
        newSql.append(sql);
        newSql.append(" ) ROW_ ) ROWALL ");
        newSql.append(" WHERE NUM_ > " + this.start + " AND NUM_ <=" + this.end);

        return newSql.toString();

    }

    public int getPagesizeValue() {
        return pagesizeValue;
    }

    public void setPagesizeValue(int pagesizeValue) {
        this.pagesizeValue = pagesizeValue;
    }

    public static void main(String[] args) {
        Page pager = new Page();
        pager.setPagesize(15);
        pager.setPageno(2);
        System.out.println(pager.getStart());
        pager.init();
        System.out.println(pager.getStart());
    }
}
