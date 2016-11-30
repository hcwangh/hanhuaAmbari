/**
 * Created by ZhangQingJing on 2015/12/25.
 */
/**
 只能选取一行,传入flexigird_id, 返回第nth个元素的值,没有选中返回null
 */
function getSingleSelected_nth(flexEle,nth){
    var arr = new Array(0);
    _$('.trSelected td:nth-child(' +nth+ ') div', _$('#' +flexEle)).each(function(i){
        arr.push(_$(this).text());
    })
    var item = arr[0];
    return item;
}
/**
 * 解析当前url,返回url所带 参数map集合
 */
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
