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
<%@taglib prefix="c" uri='http://java.sun.com/jsp/jstl/core' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <%@include file="../commonCss.jsp" %>
    <%@include file="../commonJs.jsp" %>
    <script type="text/javascript" src="<%=basePath%>scripts/ast/server_detail.js"></script>
    <script type="text/javascript">

        var path = "<%=basePath%>";

        function listSelectChange(category_id,windowId,selectedOption,selectLevel,selectId1,selectId2,readIdArrStr){
            if(selectLevel == 1){
                var brand_value = $('#'+selectId1+' option:selected') .val();
                $.ajax({
                    url:  path+"ast",
                    type: 'post', dataType: 'text', cache: false, async: false,
                    data: "action=getSelectLevel2Data&category_id=" +category_id +"&brand_value=" + brand_value,
                    success: function (data) {
                        var result = JSON.parse(data);//结果数据数
                        var select2OptionList = result['valueArr']
                        $("#"+selectId2).empty();
                        for(var i=0; i<select2OptionList.length; i++)
                        {
                            $("#"+selectId2).append("<option>"+select2OptionList[i]+"</option>");
                        }
                        $('#'+selectId2).selectpicker('refresh');
                    },
                    error: function (data) {
                        alert("发生错误  --> selectChange_1 : " + data);
                    }
                });
            }else if(selectLevel == 2){
                var brand_value = $('#'+selectId1+' option:selected') .val();
                var version_value = selectedOption.value;
                $.ajax({
                    url:  path+"ast",
                    type: 'post', dataType: 'text', cache: false, async: false,
                    data: "action=getReadTextData&brand_value=" + brand_value + "&version_value=" + version_value,
                    success: function (data) {
                        var readIdArr = readIdArrStr.split(",");//read 类型div id数组
                        var result = JSON.parse(data);//结果数据
                        for(i=0;i<readIdArr.length;i++){
//                            alert("id: " + readIdArr[i] + "  value:  " +result['valueArr'][i] );
                            if(readIdArr[i] != 'none'){
                                $('#'+readIdArr[i]).html(result['valueArr'][i] + result['valueArr'][i+1]);//等于none为忽略的下标, +1 表示与 +1的下标内容组合
                            }
                        }
                        $('#'+windowId+'_typeId').val(result['type_id']);
                    },
                    error: function (data) {
                        alert("发生错误  --> selectChange_2 : " + data);
                    }
                });

            }
        }

        function listLineAddOrEdit(windowId,type,category_id){
            var fxgdTableId = windowId + "_fList";
            var select2Id = "#" + windowId + "_version_select";
            var select1Id = "#" + windowId + "_brand_select";
            var belongSelect1Id = "#" + windowId + "_belong_info1";
            var textEntityInfo1Id = "#" + windowId + "_entity_info1";
            var readTypeInfo2Id = "#" + windowId + "_type_info2";
            if(type == 'add'){
                var this_machine_id = $('#this_machine_id').val();
                    if(this_machine_id == ''){
                        alert('注意: 先完善主机信息!');
                        return;
                    }
                $('#' + windowId +'_entityId').val('');
                $('#' + windowId +'_typeId').val('');
                $(readTypeInfo2Id).html('');
                $(belongSelect1Id).selectpicker('deselectAll');
                $(select1Id).selectpicker('deselectAll');
                $(select2Id).empty();
                $(select2Id).selectpicker('refresh');
                $(textEntityInfo1Id).val('');

            }else{
                var entity_id = getSingleSelected_nth(fxgdTableId,1);
                if(typeof(entity_id)  == "undefined"){
                    alert("请先选中一行!");
                    return;
                }


                var type_id = getSingleSelected_nth(fxgdTableId,2);
                var belong_info1 = getSingleSelected_nth(fxgdTableId,3);
                var type_brand = getSingleSelected_nth(fxgdTableId,4);
                var type_version = getSingleSelected_nth(fxgdTableId,5);
                var type_info2 = getSingleSelected_nth(fxgdTableId,6);
                var entity_info1 = getSingleSelected_nth(fxgdTableId,7);
                var entity_on_time = getSingleSelected_nth(fxgdTableId,8);
                $('#' + windowId +'_entityId').val(entity_id);
                $('#' + windowId +'_typeId').val(type_id);
                $.ajax({
                    url:  path+"ast",
                    type: 'post', dataType: 'text', cache: false, async: false,
                    data: "action=getSelectLevel2Data&category_id=" +category_id +"&brand_value=" + type_brand,
                    success: function (data) {
                        var result = JSON.parse(data);//结果数据
                        var select2OptionList = result['valueArr']
                        $(select2Id).empty();
                        for(var i=0; i<select2OptionList.length; i++)
                        {
                            $(select2Id).append("<option>"+select2OptionList[i]+"</option>");
                        }
                        $(select2Id).selectpicker('refresh');
                    },
                    error: function (data) {
                        alert("发生错误  --> selectChange_1 : " + data);
                    }
                });

                $(belongSelect1Id).selectpicker('val',belong_info1);
                $(select1Id).selectpicker('val',type_brand);
                $(select2Id).selectpicker('val',type_version);
                $(readTypeInfo2Id).html(type_info2);
                $(textEntityInfo1Id).val(entity_info1);
            }
            $('#' +windowId + '_modal').modal('show');
        }

        function listLineSubmit(windowId,submitTextIdArr,belongIdArr,category_id){

            if($("#" +windowId+"_version_select  option:selected").val() == ""){
                alert("注意: 请先进行填选后再提交!");
                return;
            };

//            var belongSelect1Id = $("#" + windowId + "_belong_info1").val();
//            var textEntityInfo1Id = $("#" + windowId + "_entity_info1").val();
            var textIds = submitTextIdArr.split(",");//text 类型Ids
            var params ="";

            var l = textIds.length;
            for(i=0;i<textIds.length;i++){
                if(i == (l-1)){
                    params = params + (i+1) + '=' +  $('#'+textIds[i]).val();
                }else{
                    params = params + (i+1) + '=' +  $('#'+textIds[i]).val() + '&';
                }
            }

//            alert("params:  " + params);
            var belongIds = belongIdArr.split(",");//text 类型Ids
            var belong_params ="";
            var belong_l = belongIds.length;
            for(i=0;i<belongIds.length;i++){
                if(i == (belong_l-1)){
                    belong_params = belong_params + ('b' + (i+1)) + '=' +  $('#'+belongIds[i]).val();
                }else{
                    belong_params = belong_params + ('b' + (i+1)) + '=' +  $('#'+belongIds[i]).val() + '&';
                }
            }
//            alert("belong_params:  " + belong_params);

            var entity_id = $('#' + windowId +'_entityId').val();
            var type_id = $('#' + windowId +'_typeId').val();
//            alert("type_id: "   + type_id);
            var machine_id   = $('#this_machine_id').val();
//            alert("machineId: " + machine_id  + "   entityId:   " + entity_id);
            var result = "0";
            if(entity_id == ''){//newEntityToHost
                $.ajax({
                    url:  path+"ast",
                    type: 'post', dataType: 'text', cache: false, async: false,
                    data: "action=newEntityToHost&type_id=" + type_id + "&category_id=" +category_id + "&machine_id=" + machine_id +"&" + params + "&" + belong_params,
                    success: function (data) {
                        result = data;
                    },
                    error: function (data) {
                    }
                });
            }else{//updateAssectEntity
                $.ajax({
                    url:  path+"ast",
                    type: 'post', dataType: 'text', cache: false, async: false,
                    data: "action=updateAssectEntity&type_id=" +type_id + "&entity_id=" + entity_id +"&" + params + "&" + belong_params  + "&machine_id=" +machine_id ,
                    success: function (data) {
                        result = data;
                    },
                    error: function (data) {
                    }
                });
            }
            if(result == "1") {
//                $('.' + windowId + '_unset').hide();
//                $('.' + windowId + '_set').show();
                $('#' +windowId + '_modal').modal('hide');
                _$('#' + windowId + '_fList').flexReload();
            }else{
                alert("系统错误!!");
            }
        }

        function exprieEntity(windowId){
            var entity_id = getSingleSelected_nth(windowId + "_fList",1);
            if(typeof(entity_id)  == "undefined"){
                alert("请先选中一行!");
                return;
            }

            var confirm2 = confirm("将删除相关数据,确认下架?");
            if(!confirm2){
                return;
            }

            var host_id = $('#this_host_id').val();
            var machine_id = $('#this_machine_id').val();

            $.ajax({
                url:  path+"ast",
                type: 'post', dataType: 'text', cache: false, async: false,
                data: "action=expireEntity&entity_id=" + entity_id +"&host_id=" + host_id + "&machine_id=" +machine_id ,
                success: function (data) {
                    _$("#" + windowId + "_fList").flexReload();
                },
                error: function (data) {
                }
            });
        }
    </script>
</head>
<body>
<div class="container">
    <input id="this_host_id" type="hidden" value="${this_host_id}">
    <input id="this_machine_id" type="hidden" value="${this_machine_id}">
    <c:forEach var="window" items="${windows}" varStatus="w_i">
        <%--奇数下标 但不是最后一个--%>
        <c:if test="${w_i.count%2 == 1}">
        <div class="row">
        </c:if>

        <c:if test="${window.type == 'list'}" >
        <%--list  window start--%>
            <div class="col-sm-6">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <B>${window.name}</B>
                        <div style="float: right;">
                            <button class="btn btn-primary btn-xs"
                                    onclick="listLineAddOrEdit('${window.id}','update','${window.belongCategoryId}');">修改</button>
                        </div><%--/ button div--%>
                        <div style="float: right;margin-right:10px">
                            <button class="btn btn-primary btn-xs"
                                    onclick="listLineAddOrEdit('${window.id}','add','${window.belongCategoryId}');">添加</button>
                        </div><%--/ button div--%>
                        <div style="float: right;margin-right:10px">
                            <button class="btn btn-primary btn-xs"
                                    onclick="exprieEntity('${window.id}');">下架</button>
                        </div><%--/ button div--%>
                    </div>
                    <div class="panel-body">
                            <%--########  内存列表  ########--%>
                        <div align="left" style="margin-top: -16px;margin-left: -16px;margin-bottom: -18px">
                            <table id="${window.id}_fList" ></table></div>
                            <%--<div align="center"></div>--%>
                        <script type="text/javascript">
                            /*var this_host_id =  $('#this_host_id').val();
                             alert(this_host_id);*/
                            _$("#${window.id}_fList").flexigrid
                            (
                                    {
                                        url: path + 'ast',
                                        action:  '${window.actionUrl}',
                                        host_id: '${this_host_id}',
                                        dataType: 'json',
                                        colModel : [
//                                            {display: 'machine_id', name : 'machine_id', width : 0,  align: 'center',hide:'bool'},
                                            {display: 'entity_id', name : 'entity_id', width : 0,  align: 'center',hide:'bool'},
                                            {display: 'type_id', name : 'type_id', width : 0,  align: 'center',hide:'bool'},
                                            {display: '槽位号', name : 'belong_info1', width : 64,  align: 'center'},
                                            {display: '品牌', name : 'type_brand', width : 64,  align: 'center'},
                                            {display: '型号', name : 'type_version', width : 57    ,  align: 'center'},
                                            {display: '大小', name : 'type_info2', width : 53,  align: 'center'},
                                            {display: '序列号', name : 'entity_info1', width : 71,  align: 'center'},
                                            {display: '上线时间', name : 'entity_on_time', width : 137,  align: 'center'}
                                        ],
                                        /*buttons : [
                                         {name: '主机发现', bclass: 'refresh', onpress : refreshSlaves},
                                         ],*/
                                        showToggleBtn: false,
//                                        usepager: true,
//                                        useRp: true,
//                                        rp: 15,
                                        width: 455,
                                        height: ${window.id == 'xtq_mem_wdw' ?"335":"136"},
                                        singleSelect: true
                                    }
                            );
                        </script>
                        <div class="modal fade" id="${window.id}_modal" tabindex="-1" role="dialog"
                             aria-labelledby="${window.id}_modal_label" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close"
                                                data-dismiss="modal" aria-hidden="true">
                                            &times;
                                        </button>
                                        <h4 class="modal-title" id="${window.id}_modal_label">
                                            添加/修改${window.name}
                                        </h4>
                                    </div>
                                    <div class="modal-body">
                                        <div class="container">
                                            <input id="${window.id}_entityId" type="hidden" value="">
                                            <input id="${window.id}_typeId" type="hidden" value="">
                                                <c:forEach var="row" items="${window.list}">
                                                    <%--######################################   basic row 基础信息   ######################################################--%>
                                                <c:if test="${row.typeName == 'belong_select_1' }">
                                                    <div class="row" style="margin-bottom: 10px;">
                                                        <div class="col-sm-2"><label>${row.labelName}:</label></div>
                                                        <div class="col-sm-4">
                                                            <select id="${row.id}" class="selectpicker" title="选择"
                                                                    data-width="100px" onchange="">
                                                                <c:forEach var="i" begin="0" end="${row.value}" step="1">
                                                                    <option>${i}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </div>
                                                    </div><%--/row--%>
                                                </c:if>
                                                    <c:if test="${row.typeName == 'select' }">
                                                        <div class="row" style="margin-bottom: 10px;">
                                                            <div class="col-sm-2"><label>${row.labelName}:</label></div>
                                                            <div class="col-sm-4">
                                                                <select id="${row.id}" class="selectpicker" title="选择"
                                                                        data-width="100px" onchange="listSelectChange('${window.belongCategoryId}','${window.id}',
                                                                        this,'${row.select_level}','${row.select_id1}','${row.select_id2}','${row.readIdArr}')">
                                                                    <c:forEach var="value" items="${row.selectValueList}">
                                                                        <option>${value}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div><%--/row--%>
                                                    </c:if>
                                                    <c:if test="${row.typeName == 'read' }">
                                                        <div class="row" style="margin-bottom: 10px;">
                                                            <div class="col-sm-2"><label>${row.labelName}:</label></div>
                                                            <div id="${row.id}" class="col-sm-4">
                                                            </div>
                                                        </div><%--/row--%>
                                                    </c:if>
                                                    <c:if test="${row.typeName == 'text' }">
                                                    <div class="row" style="margin-bottom: 10px;">
                                                        <div class="col-sm-2"><label>${row.labelName}:</label></div>
                                                        <div class="col-sm-4">
                                                            <div class="input-group input-group-sm">
                                                                <input  id="${row.id}" type="text" class="form-control" style="width: 150px">
                                                            </div>
                                                        </div>
                                                    </div><%--/row--%>
                                                    </c:if>
                                                </c:forEach>
                                        </div><%--/container--%>
                                    </div><%-- /modal-body --%>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-default"
                                                data-dismiss="modal">关闭
                                        </button>
                                        <button type="button" class="btn btn-primary"
                                                onclick="listLineSubmit('${window.id}','${window.submitTextIdArr}','${window.belongIdArr}','${window.belongCategoryId}')">
                                            提交
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%--/list  window end--%>
        </c:if>

        <c:if test="${window.type == null}" >
        <%--general  window start--%>
        <div class="col-sm-6">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <B>${window.name}</B>

                    <div style="float: right;">
                        <button type="button" class="btn btn-primary btn-xs"
                                onclick="setting('${window.id}');">设置</button>
                    </div>
                </div>
                <input id="${window.id}_typeId" type="hidden" value="${window.detail.type_id}">
                <input id="${window.id}_entityId" type="hidden" value="${window.detail.entity_id}">
                <div class="panel-body">
                    <c:forEach var="row" items="${window.list}">
<%--######################################   basic row 基础信息   ######################################################--%>
                        <c:if test="${row.typeName == 'basic' }">
                            <div class="row" style="margin-bottom: -17px;">
                                <div class="col-sm-3">
                                    <label>${row.labelName}:</label>
                                </div>
                                <div class="col-sm-3">${row.value}</div>
                            </div>
                            　　</c:if>
<%--######################################   select row 显示或下拉框   ##################################################--%>
                        <c:if test="${row.typeName == 'select' }">
                            <div class="row" style="margin-bottom: -17px;">
                                <div class="col-sm-3">
                                    <label>${row.labelName}:</label>
                                </div>
                                        <div class="${window.id}_set" style="display: ${window.isSet?"":"none"}">
                                            <div id="${row.id}_display" class="col-sm-3">${row.value}</div>
                                        </div>
                                        <div class="${window.id}_unset" style="display: ${window.isSet?"none":""}">
                                            <div class="col-sm-3">
                                                <select id="${row.id}" class="selectpicker" title="选择"
                                                        data-width="100px" onchange="selectChange('${window.belongCategoryId}','${window.id}',
                                                        this,'${row.select_level}','${row.select_id1}','${row.select_id2}','${row.readIdArr}')">
                                                    <c:forEach var="value" items="${row.selectValueList}">
                                                        <c:choose>
                                                            <c:when test="${value == row.value}">
                                                                <option selected = selected>${value}</option>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <option>${value}</option>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <c:if test="${!window.isSet}">
                                                <script>
                                                    $(${row.id}).selectpicker('deselectAll');/*取消默认选中*/
                                                </script>
                                            </c:if>
                                        </div>
                            </div>
                            　　</c:if>
<%--######################################   select row 没有交互的   ##################################################--%>
                        <c:if test="${row.typeName == 'selectNoEvent' }">
                            <div class="row" style="margin-bottom: -17px;">
                                <div class="col-sm-3">
                                    <label>${row.labelName}:</label>
                                </div>
                                <div class="${window.id}_set" style="display: ${window.isSet?"":"none"}">
                                    <div id="${row.id}_display" class="col-sm-3">${row.value}</div>
                                </div>
                                <div class="${window.id}_unset" style="display: ${window.isSet?"none":""}">
                                    <div class="col-sm-3">
                                        <select id="${row.id}" class="selectpicker" title="选择"
                                                data-width="100px" >
                                            <c:forEach var="value" items="${row.selectValueList}">
                                                <c:choose>
                                                    <c:when test="${value == row.value}">
                                                        <option selected = selected>${value}</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option>${value}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <c:if test="${!window.isSet}">
                                        <script>
                                            $(${row.id}).selectpicker('deselectAll');/*取消默认选中*/
                                        </script>
                                    </c:if>
                                </div>
                            </div>
                            　　</c:if>
<%--######################################   read row 只读信息   ######################################################--%>
                        <c:if test="${row.typeName == 'read' }">
                            <div class="row" style="margin-bottom: -17px;">
                                <div class="col-sm-3">
                                <label>${row.labelName}:</label>
                            </div>
                                <div id="${row.id}" class="col-sm-3">${row.value}</div>
                            </div>
                            　　</c:if>　　
<%--######################################   text row 显示或文本框   ######################################################--%>
                        <c:if test="${row.typeName == 'text' }">
                            <div class="row" style="margin-bottom: -17px;">
                                <div class="col-sm-3">
                                    <label>${row.labelName}:</label>
                                </div>
                                        <div class="${window.id}_set"  style="display: ${window.isSet?"":"none"}">
                                            <div id="${row.id}_display" class="col-sm-3" style="width:  200px">${row.value}</div>
                                        </div>

                                        <div class="${window.id}_unset" style="display: ${window.isSet?"none":""}">
                                            <div class="col-sm-3">
                                                <div class="input-group input-group-sm">
                                                    <input id="${row.id}" type="text" class="form-control"
                                                           value="${window.isSet?row.value:""}" style="width: 200px">
                                                </div>
                                            </div>
                                        </div>
                            </div>
                            　　</c:if>　　
                    </c:forEach>
<%--######################################   window footer  ######################################################--%>
                            <div class="${window.id}_unset" style="display: ${window.isSet?"none":""}">
                                <div class="row" style="margin-bottom: -17px;">
                                    <div class="col-sm-12" align="center" style="margin-top: 6px">
                                        <%--<button type="button" class="btn btn-primary"
                                                style="width: 100px" onclick="undo('${window.id}')">取消
                                        </button>--%>
                                            <button type="button" class="btn btn-primary"
                                                    style="width: 100px" onclick="windowFinish('${window.isSet}','${window.submitTextIdArr}','${window.id}',
                                                    '${window.belongCategoryId}','${window.detail.host_id}','${window.detail.machine_entity_id}')">完成
                                            </button>
                                    </div>
                                </div>
                            </div>
                </div>
            </div>
            </div><%--/ general  window  end--%>
        </c:if>

        <%--偶数下标 或者是最后一个下标 补充 row 的div 收尾--%>
        <c:if test="${w_i.count%2 == 0 ||  i.count == fn:length(windows)}">
        </div>
        </c:if>
        </c:forEach>
    </div>
</body>
</html>