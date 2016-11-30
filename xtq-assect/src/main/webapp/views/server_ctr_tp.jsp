<!--
Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements. See the NOTICE file distributed with
this work for additional information regarding copyright ownership.
The ASF licenses this file to You under the Apache License, Version 2.0
(the "License"); you may not use this file except in compliance with
the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License. Kerberos, LDAP, Custom. Binary/Htt
-->

<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.zjhc.hcdream.model.*" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="st" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>--%>

<% request.setCharacterEncoding("utf-8"); %>
<% response.setCharacterEncoding("utf-8"); %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head lang="">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <%@include file="../commonJs.jsp" %>
    <%@include file="../commonCss.jsp" %>
    <script type="text/javascript">
        var path = "<%=basePath%>";
        function parseUrl(){
            var url=location.href;
            var i=url.indexOf('?');
            if(i==-1)return;
            var querystr=url.substr(i+1);
            var arr1=querystr.split('&');
            var arr2=new Object();
            for  (i in arr1){
                var ta=arr1[i].split('=');
                arr2[ta[0]]=ta[1];
            }
            return arr2;
        }
        var v = parseUrl();
        console.log(v['host_id']);

        function add(){
            $("#adf").append("<option>test1</option>");
//            $('.selectpicker').refresh();
            $('#adf').selectpicker('refresh');
        }
        function select(){
            $('#adf').selectpicker('val', 'test1');
        }
        function removeAll(){
            $("#adf").empty();
            $('#adf').selectpicker('refresh');
        }
        function deselect(){
            $('#adf').selectpicker('deselectAll');
        }
        function t(aa){
            alert(aa.value);
        }
        function idetach(){
            alert($("#adf").tagName);
            alert(document.getElementById("12adf").tagName);
            /*var message = $("#adf  option:selected").val();
            alert(message);
            if(message == ""){
                alert("未选中");
            }*/
        }
    </script>
</head>
<body>
<a href="#" onclick="select();">select</a>
<a href="#" onclick="add();">add</a>
<a href="#" onclick="removeAll();">clear</a>
<a href="#" onclick="deselect();">deselect</a>
<a href="#" onclick="idetach();">idetach</a>
<input id="12adf" type="text" value ="111"  />
<select id="adf" class="selectpicker"  title="选择" data-width="100px" onchange="t(this)">
<option>1</option>
<option>2</option>
<option>3</option>
    </select>
<%--<select class="selectpicker"  title="主机型号" data-width="100px">--%>
<%--</select>--%>


<!-- 按钮触发模态框 -->
<button class="btn btn-primary btn-lg" data-toggle="modal"
        data-target="#myModal">
    开始演示模态框
</button>

<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    模态框（Modal）标题
                </h4>
            </div>
            <div class="modal-body">
                在这里添加一些文本
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default"
                        data-dismiss="modal">关闭
                </button>
                <button type="button" class="btn btn-primary">
                    提交更改
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
</body>
</html>

