/**
 * Created by ZhangQingJing on 2015/12/25.
  *  Js for  server_detail.jsp
 */
function setting(windowId){
    $('.' + windowId + '_set').hide();
    $('.' + windowId + '_unset').show();
}

function selectChange(category_id,windowId,selectedOption,selectLevel,selectId1,selectId2,readIdArrStr){
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
                $('#'+selectId1 + "_display").html(selectedOption.value);
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
                var result = JSON.parse(data);//结果数据数
                for(i=0;i<readIdArr.length;i++){
                    $('#'+readIdArr[i]).html(result['valueArr'][i]);
                }
                $('#'+selectId2 + "_display").html(selectedOption.value);
                $('#'+windowId+'_typeId').val(result['type_id']);
            },
            error: function (data) {
                alert("发生错误  --> selectChange_2 : " + data);
            }
        });

    }
}
function windowFinish(isSet,submitTextIdArr,windowId,category_id,host_id,machine_id){
    //直接定位到第二个下拉框的ID
    if($("#" +windowId+"_version_select  option:selected").val() == ""){
        alert("注意: 请先进行填选后再提交!");
        return;
    };
    if(submitTextIdArr != '') {
        var textIds = submitTextIdArr.split(",");//text 类型Ids
        var params = "";
        var l = textIds.length;
        for (i = 0; i < textIds.length; i++) {
            if (i == (l - 1)) {
                var val;
                if (document.getElementById(textIds[i]).tagName == "SELECT") {
                    val = $("#" + textIds[i] + " option:selected").val();
                } else {
                    val = $("#" + textIds[i]).val();
                }
                params = params + (i + 1) + '=' + val;
                $("#" + textIds[i] + "_display").html(val);
            } else {
                var val;
                if (document.getElementById(textIds[i]).tagName == "SELECT") {
                    val = $("#" + textIds[i] + " option:selected").val();
                } else {
                    val = $("#" + textIds[i]).val();
                }
                params = params + (i + 1) + '=' + val + '&';
                $("#" + textIds[i] + "_display").html(val);
            }
        }
    }
    //以上更新下拉框对应的 展示的div 同时组装post参数
    var type_id =   $('#' + windowId + '_typeId').val();
    var result = "0";
    if(isSet == 'true'){
        var entity_id = $('#' + windowId + '_entityId').val();
        $.ajax({
            url:  path+"ast",
            type: 'post', dataType: 'text', cache: false, async: false,
            data: "action=updateAssectEntity&type_id=" +type_id + "&entity_id=" + entity_id +"&" + params,
            success: function (data) {
                result = data;
                if($("#this_machine_id").val() == entity_id){//主机更新则刷新页面
                    window.location.reload();
                }
            },
            error: function (data) {
                alert("发生错误  -->  " + data);
            }
        });

    }else{
        if(category_id == "1"){//new Host
            $.ajax({
                url:  path+"ast",
                type: 'post', dataType: 'text', cache: false, async: false,
                data: "action=newHost&type_id=" + type_id + "&category_id=" +category_id + "&host_id=" + host_id +"&" + params,
                success: function (data) {
                    var jsonData = JSON.parse(data);//结果数据数
                    $('#this_host_id').val(host_id);
                    $('#this_machine_id').val(jsonData['machine_id']);
                    result = jsonData['result'];
                    window.location.reload();//主机更新则刷新页面
                },
                error: function (data) {
                    alert("发生错误  -->  " + data);
                }
            });
        }else{//new Entity To Host
            var this_machine_id = $('#this_machine_id').val();
            if(machine_id == ''){
                if(this_machine_id == ''){
                    alert('注意: 先完善主机信息!');
                    return;
                }else{
                    machine_id = this_machine_id;
                }
            }
            $.ajax({
                url:  path+"ast",
                type: 'post', dataType: 'text', cache: false, async: false,
                data: "action=newEntityToHost&type_id=" + type_id + "&category_id=" +category_id + "&machine_id=" + machine_id +"&" + params,
                success: function (data) {
                    result = data;
                },
                error: function (data) {
                    alert("error  -->  " + data);
                }
            });
        }
    }
    if(result == "1") {
        $('.' + windowId + '_unset').hide();
        $('.' + windowId + '_set').show();
    }else{
        alert("系统错误!!");
    }
}

function undo(windowId){
    $('.' + windowId + '_unset').hide();
    $('.' + windowId + '_set').show();
}