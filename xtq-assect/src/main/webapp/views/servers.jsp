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

        function to_server_detail(host_id){
            _$("#redirect_form").attr('action', path + 'ast');
            _$("#form_host_id").val(host_id);
            _$("#redirect_form").submit();
        }

        function expireHost(){
            var host_id = getSingleSelected_nth("flex_servers", 1);
            if(typeof(host_id)  == "undefined"){
                alert("请先选中一行!");
                return;
            }

            var confirm2 = confirm("将删除相关数据,确认下架?");
            if(!confirm2){
                return;
            }

            _$.ajax({
                url:  path+"ast",
                type: 'post', dataType: 'text', cache: false, async: false,
                data: "action=expireHost&host_id="+host_id,
                success: function (data) {
                    _$("#flex_servers").flexReload();
                    console.log("servers --> expireHost -->  " + data);
                },

                error: function (data) {
                    layer.alert("[系统错误!!]  servers --> expireHost --> " + data, {
                        icon: 6,
                        skin: 'layui-layer-lan',
                        offset: 200
                    });
                }
            });
        }

        function refreshSlaves(){
            _$.ajax({
                url:  path+"ast",
                type: 'post', dataType: 'text', cache: false, async: false,
                data: "action=refreshSlaves",
                //                random: Math.random(),
                success: function (data) {
                    _$("#flex_servers").flexReload();
                    console.log("servers --> refreshSlaves -->  " + data);
                },
                error: function (data) {
                    layer.alert("[系统错误!!]  servers --> refreshSlaves --> " + data, {
                        icon: 6,
                        skin: 'layui-layer-lan',
                        offset: 200
                    });
                }
            });
        }

        function newHostSubmit(){
            var result = 'ok';
            $.ajax({
                url:  path+"ast",
                type: 'post', dataType: 'text', cache: false, async: false,
                data:  $('#new_host_form').serialize(),
                success: function (data) {
                    result = data;
                },
                error: function (data) {
                    result = data;
                }
            });
            if(result != 'ok'){
                layer.alert(result, {
                    icon: 6,
                    skin: 'layui-layer-lan',
                    offset: 200
                });
            }else{
                _$("#flex_servers").flexReload();
            }
            $('#newHostModal').modal('hide');
        }

        function newHostShow(){
            $('#newHostModal').modal('show');
        }
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
    <table id="flex_servers" ></table>
</div>
<%--<div align="center"></div>--%>
<script type="text/javascript">
    _$("#flex_servers").flexigrid
    (
            {
                url: path + 'ast',
                action:'servers',
                dataType: 'json',
                colModel : [
                    {display: 'id', name : 'host_id', width : 0,  align: 'center',hide:'bool'},
                    {display: 'IP地址', name : 'host_ip', width : 96,  align: 'center'},
                    {display: '主机名', name : 'host_name', width : 96,  align: 'center'},
                    {display: '主机角色', name : 'role', width : 90,  align: 'center'},
                    {display: '内存容量', name : 'mem', width : 99,  align: 'center'},
                    {display: '硬盘容量', name : 'disk', width : 99,  align: 'center'},
                    {display: 'CPU核数', name : 'cpu', width : 90,  align: 'center'},
                    {display: '联系人', name : 'principal_info', width : 160,  align: 'center'},
                    {display: '操作', name : 'js_operation', width : 120,  align: 'center'}
                ],
                buttons : [
                    {name: '主机发现', bclass: 'magnifier', onpress : refreshSlaves},
                    {separator: true},
                    {name: '主机录入', bclass: 'serverAdd', onpress : newHostShow},
                    {separator: true},
                    {name: '下架', bclass: 'delete', onpress : expireHost},
                ],
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

<div class="modal fade" id="newHostModal" tabindex="-1" role="dialog"
     aria-labelledby="newHostLable" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="newHostLable">
                    主机录入
                </h4>
            </div>
            <div class="modal-body">
                <div class="container">
                    <form id="new_host_form">
                        <input type="hidden" name="action" value="newHost_m" />
                        <div class="row" style="margin-bottom: 10px;">
                            <div class="col-sm-2"><label>IP地址: </label></div>
                            <div class="col-sm-4">
                                <div class="input-group input-group-sm">
                                    <input  id="ip" name="ip" type="text" class="form-control" style="width: 150px">
                                </div>
                            </div>
                        </div><%--/row--%>
                        <div class="row" style="margin-bottom: 10px;">
                            <div class="col-sm-2"><label>主机名: </label></div>
                            <div class="col-sm-4">
                                <div class="input-group input-group-sm">
                                    <input  id="hostname" name="hostname" type="text" class="form-control" style="width: 150px">
                                </div>
                            </div>
                        </div><%--/row--%>
                </div><%--/container--%>
                </form>
            </div><%-- /modal-body --%>
            <div class="modal-footer">
                <button type="button" class="btn btn-default"
                        data-dismiss="modal">关闭
                </button>
                <button type="button" class="btn btn-primary"
                        onclick="newHostSubmit()">
                    提交
                </button>
            </div>
        </div>
    </div>
</div>
</body>
</html>

