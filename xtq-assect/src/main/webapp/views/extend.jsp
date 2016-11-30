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
    <%@include file="../commonCss.jsp" %>
    <%@include file="../commonJs.jsp" %>
    <script type="text/javascript">
        var path = "<%=basePath%>";
        //得到页面高度, 宽度
        /* var yScroll = (document.documentElement.scrollHeight >document.documentElement.clientHeight)
         ? document.documentElement.scrollHeight : document.documentElement.clientHeight;
         var xScroll=(document.documentElement.scrollWidth>document.documentElement.clientWidth)
         ? document.documentElement.scrollWidth : document.documentElement.scrollWidth;*/
        //        alert(yScroll);
        var yScroll = 420;
        var xScroll= 970;

    </script>
</head>
<body>
<div id="form_div" style="display: none;">
    <form id="redirect_form" name="redirect_form" method="post">
        <input id="form_host_id" type="hidden" name="host_id" value="">
        <input type="hidden" name="action" value="server_detail">
    </form>
</div>
<div align="left" style="margin-left:20px">
    <table id="extendInfos"></table>
</div>
<%--<div align="center"></div>--%>
<script type="text/javascript">
    _$("#extendInfos").flexigrid
    (
            {
                url: path + 'ast',
                action:'extendInfos',
                dataType: 'json',
                colModel : [
                    {display: 'IP地址', name : 'host_ip', width : 96,  align: 'center'},
                    {display: '主机名', name : 'host_name', width : 96,  align: 'center'},
                    {display: '已用内存槽数', name : 'memInUse', width : 110,  align: 'center'},
                    {display: '空闲内存槽数', name : 'memIdle', width : 110,  align: 'center'},
                    {display: '已用硬盘槽数', name : 'diskInUse', width : 130,  align: 'center'},
                    {display: '空闲硬盘槽数', name : 'diskIdle', width : 160,  align: 'center'}
                ],
                /*  buttons : [
                 {name: '主机发现', bclass: 'refresh', onpress : refreshSlaves},
                 ],*/
                showToggleBtn: false,
                usepager: true,
                useRp: true,
                rp: 15,
                width: xScroll,
                height: yScroll,
                singleSelect: true
            }
    );
</script>
</body>
</html>

