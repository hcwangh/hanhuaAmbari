<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8" %>
<% request.setCharacterEncoding("utf-8"); %>
<% response.setCharacterEncoding("utf-8"); %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head lang="">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <%@include file="../commonJs.jsp" %>
    <%@include file="../commonCss.jsp" %>
    <script type="text/javascript">
        var path = "<%=basePath%>";
        var actionType;
        var yScroll = 420;
        var xScroll = 970;
        layer.config({extend: 'extend/layer.ext.js'});

        function newCategory() {
            layer.prompt({
                title: '请输入名称',
                formType: 0,
                value: '名称'
            }, function (name) {
                $.ajax({
                    url: path + "ast",
                    type: 'post', dataType: 'text', cache: false, async: false,
                    data: {category_name: name, action: 'addtion'},
                    success: function (data) {
                        if (data == "1") {
                            _$('#flex_category').flexReload();
                        }
                    },
                    error: function () {
                        alert("异常!");
                    }
                })
            })
        }

        function newType() {
            actionType = 'add';
            $('#actionType').val('newType');
            $('#type_name').val('');
            $('#type_brand').val('');
            $('#type_version').val('');
            $('#type_info1').val('');
            $('#type_info2').val('');
            $('#type_info3').val('');
            $('#type_info4').val('');
            $('#type_info5').val('');
            $('#newTypeModal').modal('show');
            typeChange("1");
        }

        function changeType() {
            actionType = 'update';
            $('#actionType').val('changeType');
            var type_id = getSingleSelected_nth("flex_type", 1);
            if (typeof(type_id) == "undefined") {
                alert("请先选中一行!");
                return;
            }
            var type_name = getSingleSelected_nth("flex_type", 2);
            var category_id = getSingleSelected_nth("flex_type", 3);
            var type_brand = getSingleSelected_nth("flex_type", 4);
            var type_version = getSingleSelected_nth("flex_type", 5);
            var type_info1 = getSingleSelected_nth("flex_type", 6);
            var type_info2 = getSingleSelected_nth("flex_type", 7);
            var type_info3 = getSingleSelected_nth("flex_type", 8);
            var type_info4 = getSingleSelected_nth("flex_type", 9);
            var type_info5 = getSingleSelected_nth("flex_type", 10);
            var reference_price = getSingleSelected_nth("flex_type", 11);
//            $('#category_id').val(category_id);
            $('#type_id').val(type_id);
            $('#type_name').val(type_name);
            $('#type_brand').val(type_brand);
            $('#type_version').val(type_version);
            $('#type_info1').val(type_info1);
            $('#type_info2').val(type_info2);
            $('#type_info3').val(type_info3);
            $('#type_info4').val(type_info4);
            $('#type_info5').val(type_info5);
            $('#reference_price').val(reference_price);
            $('#newTypeModal').modal('show');
            typeChange(category_id);
            $('#category_select').selectpicker('val', category_id);
        }
    </script>
</head>
<body>
<div align="left" style="margin-left:20px">
    <table id="flex_type"></table>
</div>
<script type="text/javascript">
    _$("#flex_category").flexigrid
    (
            {
                url: path + 'ast',
                action: 'category',
                dataType: 'json',
                colModel: [
                    {display: 'id', name: 'category_id', width: 100, align: 'center'},
                    {display: '类别', name: 'category_name', width: 100, align: 'center'},
                ],
                buttons: [
                    {name: '新增', bclass: 'refresh', onpress: newCategory},
                ],
                showToggleBtn: true,
                width: xScroll,
                height: yScroll,
                singleSelect: true
            }
    );

    _$("#flex_type").flexigrid
    (
            {
                url: path + 'ast',
                action: 'type',
                dataType: 'json',
                colModel: [
                    {display: '类型', name: 'category_name', width: 100, align: 'center'},
                    {display: '类型id', name: 'type_id', width: 100, align: 'center',hide:'bool'},
                    {display: '名称', name: 'type_name', width: 150, align: 'center'},
                    {display: 'id', name: 'category_id', width: 100, align: 'center', hide: 'bool'},
                    {display: '品牌', name: 'type_brand', width: 100, align: 'center'},
                    {display: '型号', name: 'type_version', width: 100, align: 'center'},
                    {display: 'type_info1', name: 'type_info1', width: 100, align: 'center', hide: 'bool'},
                    {display: 'type_info2', name: 'type_info2', width: 100, align: 'center', hide: 'bool'},
                    {display: 'type_info3', name: 'type_info3', width: 100, align: 'center', hide: 'bool'},
                    {display: 'type_info4', name: 'type_info4', width: 100, align: 'center', hide: 'bool'},
                    {display: 'type_info5', name: 'type_info5', width: 100, align: 'center', hide: 'bool'},
                    {display: 'reference_price', name: 'reference_price', width: 100, align: 'center', hide: 'bool'},
                ],
                buttons: [
                    {name: '新增', bclass: 'typeAdd', onpress: newType},
                    {name: '修改', bclass: 'typeChange', onpress: changeType},
                ],
                showToggleBtn: true,
                width: xScroll,
                height: yScroll,
                singleSelect: true
            }
    );

    function typeSubmit() {
        if (actionType == 'add') {
            var result = 'ok';
            $.ajax({
                url: path + "ast",
                type: 'post', dataType: 'text', cache: false, async: false,
                data: $('#new_type_form').serialize(),
                success: function (data) {
                    result = data;
                },
                error: function (data) {
                    result = data;
                }
            });
            if (result != 'ok') {
                layer.alert(result, {
                    icon: 6,
                    skin: 'layui-layer-lan',
                    offset: 200
                });
            } else {
                _$('#flex_type').flexReload();
                $('#newTypeModal').modal('hide');
            }
        } else {/*update*/
            var result = 'ok';
            $.ajax({
                url: path + "ast",
                type: 'post', dataType: 'text', cache: false, async: false,
                data: $('#new_type_form').serialize(),
                success: function (data) {
                    result = data;
                },
                error: function (data) {
                    result = data;
                }
            });
            if (result != 'ok') {
                layer.alert(result, {
                    icon: 6,
                    skin: 'layui-layer-lan',
                    offset: 200
                });
            } else {
                _$('#flex_type').flexReload();
                $('#newTypeModal').modal('hide');
            }
        }
    }

    function showall() {
        $('#type_info1_display').show();
        $('#type_info2_display').show();
        $('#type_info3_display').show();
        $('#type_info4_display').show();
        $('#type_info5_display').show();
        $('#type_info1').show();
        $('#type_info2').show();
        $('#type_info3').show();
        $('#type_info4').show();
        $('#type_info5').show();
    }

    function typeChange(option) {
        showall();
        if (option == '1') {
            showall();
            $('#type_info1_display').html('内存槽位数');
            $('#type_info1').show();
            $('#type_info2_display').html('硬盘槽位数');
            $('#type_info2').show();
            $('#type_info3_display').html('大小');
            $('#type_info3').show();
            $('#type_info4_display').html('电源数目');
            $('#type_info4').show();
            $('#type_info5_display').html('网口数目');
            $('#type_info5').show();
        } else if (option == '2') {
            showall();
            $('#type_info1_display').hide();
            $('#type_info1').hide();
            $('#type_info2_display').html('大小（数字）');
            $('#type_info3_display').html('单位（G）');
            $('#type_info4_display').hide();
            $('#type_info4').hide();
            $('#type_info5_display').hide();
            $('#type_info5').hide();
            $('#type_info6_display').hide();
        } else if (option == '3') {
            showall();
            $('#type_info1_display').hide();
            $('#type_info2_display').html('大小（数字）');
            $('#type_info3_display').html('单位（G）');
            $('#type_info4_display').hide();
            $('#type_info5_display').hide();
            $('#type_info6_display').hide();
            $('#type_info1').hide();
            $('#type_info4').hide();
            $('#type_info5').hide();
        } else if (option == '4') {
            showall();
            $('#type_info1_display').html('功率(w)');
            $('#type_info2_display').hide();
            $('#type_info3_display').hide();
            $('#type_info4_display').hide();
            $('#type_info5_display').hide();
            $('#type_info2').hide();
            $('#type_info3').hide();
            $('#type_info4').hide();
            $('#type_info5').hide();
        } else if (option == '5') {
            showall();
            $('#type_info1_display').html('核心数');
            $('#type_info2_display').html('主频（Hz）');
            $('#type_info3_display').hide();
            $('#type_info4_display').hide();
            $('#type_info5_display').hide();
            $('#type_info3').hide();
            $('#type_info4').hide();
            $('#type_info5').hide();
        } else if (option == '6') {
            showall();
            $('#type_info1_display').html('速率(Mbps)');
            $('#type_info2_display').hide();
            $('#type_info3_display').hide();
            $('#type_info4_display').hide();
            $('#type_info5_display').hide();
            $('#type_info2').hide();
            $('#type_info3').hide();
            $('#type_info4').hide();
            $('#type_info5').hide();

        } else {
            $('#type_info1_display').hide();
            $('#type_info1').hide();
            $('#type_info2').hide();
            $('#type_info2_display').hide();
            $('#type_info3').hide();
            $('#type_info3_display').hide();
            $('#type_info4').hide();
            $('#type_info4_display').hide();
            $('#type_info5').hide();
            $('#type_info5_display').hide();
        }
    }
</script>
<%--参考 servers.jsp 的 newHostModal元素的相关方法--%>
<div class="modal fade" id="newTypeModal" tabindex="-1" role="dialog"
     aria-labelledby="newTypeLable" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="newTypeLable">
                    新增/修改
                </h4>
            </div>
            <div class="modal-body">
                <div class="container">
                    <form id="new_type_form">
                        <input id="actionType" type="hidden" name="action" value=""/>
                        <input id="type_id" type="hidden" name="type_id" value=""/>
                        <%--<input id="category_id" type="hidden" name="category_id" value=""/>--%>
                        <div class="row" style="margin-bottom: 10px;">
                            <div class="col-sm-2"><label>类型 </label></div>
                            <div class="col-sm-4">
                                <div class="input-group input-group-sm">
                                    <input id="type_name" name="type_name" type="text" class="form-control"
                                           style="width: 150px">
                                </div>
                            </div>
                        </div>
                        <%--/row--%>
                        <div class="row" style="margin-bottom: 10px;">
                            <div class="col-sm-2"><label>品牌: </label></div>
                            <div class="col-sm-4">
                                <div class="input-group input-group-sm">
                                    <input id="type_brand" name="type_brand" type="text" class="form-control"
                                           style="width: 150px">
                                </div>
                            </div>
                        </div>
                        <%--/row--%>
                        <div class="row" style="margin-bottom: 10px;">
                            <div class="col-sm-2"><label>型号: </label></div>
                            <div class="col-sm-4">
                                <div class="input-group input-group-sm">
                                    <input id="type_version" name="type_version" type="text" class="form-control"
                                           style="width: 150px">
                                </div>
                            </div>
                        </div>
                        <%--/row--%>
                        <div class="row" style="margin-bottom: 10px;">
                            <div class="col-sm-2"><label>类别: </label></div>
                            <div class="col-sm-4">
                                <div class="input-group input-group-sm">
                                    <select id="category_select" name="category_id" class="selectpicker" title=" "
                                            data-width="100px" onchange="typeChange(this.value)">
                                    </select>
                                </div>
                            </div>
                        </div>
                        <%--/row--%>
                        <div class="row" style="margin-bottom: 10px;">
                            <div class="col-sm-2"><label id="type_info1_display">type_info1: </label></div>
                            <div class="col-sm-4">
                                <div class="input-group input-group-sm">
                                    <input id="type_info1" name="type_info1" type="text" class="form-control"
                                           style="width: 150px">
                                </div>
                            </div>
                        </div>
                        <%--/row--%>
                        <div class="row" style="margin-bottom: 10px;">
                            <div class="col-sm-2"><label id="type_info2_display">type_info2: </label></div>
                            <div class="col-sm-4">
                                <div class="input-group input-group-sm">
                                    <input id="type_info2" name="type_info2" type="text" class="form-control"
                                           style="width: 150px">
                                </div>
                            </div>
                        </div>
                        <%--/row--%>
                        <div class="row" style="margin-bottom: 10px;">
                            <div class="col-sm-2"><label id="type_info3_display">type_info3: </label></div>
                            <div class="col-sm-4">
                                <div class="input-group input-group-sm">
                                    <input id="type_info3" name="type_info3" type="text" class="form-control"
                                           style="width: 150px">
                                </div>
                            </div>
                        </div>
                        <%--/row--%>
                        <div class="row" style="margin-bottom: 10px;">
                            <div class="col-sm-2"><label id="type_info4_display">type_info4: </label></div>
                            <div class="col-sm-4">
                                <div class="input-group input-group-sm">
                                    <input id="type_info4" name="type_info4" type="text" class="form-control"
                                           style="width: 150px">
                                </div>
                            </div>
                        </div>
                        <%--/row--%>
                        <div class="row" style="margin-bottom: 10px;">
                            <div class="col-sm-2"><label id="type_info5_display">type_info5: </label></div>
                            <div class="col-sm-4">
                                <div class="input-group input-group-sm">
                                    <input id="type_info5" name="reference_price" type="text" class="form-control"
                                           style="width: 150px">
                                </div>
                            </div>
                        </div>
                </div>
                <%--/container--%>
                </form>
            </div>
            <%-- /modal-body --%>
            <div class="modal-footer">
                <button type="button" class="btn btn-default"
                        data-dismiss="modal">关闭
                </button>
                <button type="button" class="btn btn-primary"
                        onclick="typeSubmit()">
                    提交
                </button>
            </div>
        </div>
    </div>
</div>
<script>
    $.ajax({
                url: path + "ast",
                type: 'post', dataType: 'text', cache: false, async: false,
                data: "action=getCategorySelectData",
                success: function (data) {
                    var result = JSON.parse(data);
                    for (var i = 0; i < result.length; i++) {
                        $("#category_select").append("<option value='" + result[i]['category_id'] + "'>" + result[i]['category_name'] + '</option>');
                        $("#category_select_change").append("<option value='" + result[i]['category_id'] + "'>" + result[i]['category_name'] + '</option>');
                    }
                    $('#category_select').selectpicker('refresh');
                },
                error: function (data) {
                    alert("发生错误 --->selectChange_1：" + data);
                }
            }
    )


    typeChange('1');
</script>
</body>
</html>

