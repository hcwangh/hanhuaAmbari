/**
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

var App = require('app');
var filters = require('views/common/filter_view');
var sort = require('views/common/sort_view');
var date = require('utils/date');
var misc = require('utils/misc');

App.MainZhangqjTestView = Em.View.extend({
  name: 'mainZhangqjTestView',
  templateName: require('templates/main/zhangqj/test'),
  zhangqjTextField: Em.TextField.extend({}),

  selectAllPerson: false,
  ajaxData:'a',
  authBeforeSend: function(opt, xhr, data) {
    xhr.setRequestHeader("Authorization", data.auth);
  },
  ssss:function(data){
      console.log('[DEBUG By ZQJ] ------------------------------------------------------------>getDataSuccess: ' + data.items[0].Clusters.provisioning_state);
  },
  eee:function(data){
    alert('error!!');
  },
  getData: function(){
    var hash = misc.utf8ToB64('admin' + ":" + 'admin');
      App.ajax.send({
          name: 'zhangqj.test',
          sender: this,
          success: 'ssss',
          error:'eee'
  });
    //{{params: string, success: callback, error: callback}}
    //var dd;
    //var ajaxOptions = {params: '', success: 'ssss', error: 'eee',hash:hash};
    //App.HttpClient.get_zhangqj('http://10.80.6.121:8080/api/v1/clusters',dd,ajaxOptions);
    //var data = '';
     //App.HttpClient.get('http://10.80.6.121:8080/api/v1/clusters', '', data);
    //alert(data);
    //App.HttpClient.get('http://10.80.6.121:8080/api/v1/clusters',)
   /* var settings = {
      type: "GET",
      url: 'http://10.80.6.121:8080/api/v1/clusters',
      dataType:"xml",
      error: function(XHR,textStatus,errorThrown) {
        alert ("XHR="+XHR+"\ntextStatus="+textStatus+"\nerrorThrown=" + errorThrown);
      },
      success: function(data) {
        alert(data);
      },
      headers: {
        "Access-Control-Allow-Origin":"10.80.6.121:9999",
        "Access-Control-Allow-Headers":"X-Requested-With",
        "Authorization": "Basic " + hash,
          "X-Http-Method-Override":"GET",
          "Content-type": "application/x-www-form-urlencoded"
      }
    };
    $.ajax(settings);*/
   /* $.ajax({
      type: "GET",
      url: "http://10.80.6.121:8080/api/v1/clusters",
      beforeSend: function(request) {
        //request.setRequestHeader("auth", "Basic " + hash);
        request.setRequestHeader("Authorization", "Basic " + hash);
        //request.setRequestHeader("usr", "admin");
        //request.setRequestHeader("loginName", encodeURIComponent('admin'));
      },
      success: function(result) {
        alert(result);
      }
    });*/
    /*App.ajax.send_zhangqj({
      name: 'zhangqj.test',
      sender: this,
      data: {
        auth: "Basic " + hash,
        usr: 'admin',
        loginName: encodeURIComponent('admin')
      },
      beforeSend: 'authBeforeSend',
      success: function(data) {
        alert('success: ' + data);
      },
      error: function(data){
        alert('error!!');
      }
    });*/
    /*App.ajax.send_zhangqj({
      name: 'zhangqj.test',
      sender: this,
      /!*data: {
        context: Em.I18n.t(contextString),
        serviceName: service.get('serviceName'),
        componentName: operationData.componentName,
        parameters: parameters,
        noOpsMessage: operationData.componentNameFormatted
      },*!/
      success: function(data){
        alert(data);
      }
    });*/
   /* var settings = {
      type: "POST",
      url:URL+"?"+REQUEST,
      dataType:"xml",
      error: function(XHR,textStatus,errorThrown) {
        alert ("XHR="+XHR+"\ntextStatus="+textStatus+"\nerrorThrown=" + errorThrown);
      },
      success: function(data,textStatus) {
        $("body").append(data);
      },
      headers: {
        "Access-Control-Allow-Origin":"10.80.6.121:8080",
        "Access-Control-Allow-Headers":"X-Requested-With"
      }
    };
    $.ajax(settings);*/
  /*  $.ajax({
      type: 'GET',
      url: 'http://10.80.6.121:8080/api/v1/clusters',
      data: 'name=admin&password=admin',
      success: function(data){
        alert(data);
      }
    });*/
     /* $.ajax({
          dataType: 'jsonp',
          url: 'http://10.80.6.121:8080/api/v1/clusters',
          success: function(data){
              alert(data);
          }
      });*//*
      alert(3);
      $.getJSON('http://10.80.6.121:8080/api/v1/clusters&callback=ssss', function(data){
          alert(data);
          alert('1.5');
      });
      alert(2);*/
  },

  sortView: sort.serverWrapperView,
  nameSort: sort.fieldView.extend({
    column: 1,
    name:'name',
    displayName: '姓名'
  }),
  sexSort: sort.fieldView.extend({
    column: 2,
    name:'sex',
    displayName: '性别'
  }),
  ageSort: sort.fieldView.extend({
    column: 3,
    name:'age',
    displayName: '年龄'
  }),
  postSort: sort.fieldView.extend({
    column: 4,
    name:'post',
    displayName: '岗位'
  }),


  /**
   * Filter view for name column
   * Based on <code>filters</code> library
   */
  nameFilterView: filters.createTextView({
    column: 1,
    fieldType: 'filter-input-width',
    onChangeValue: function(){
      this.get('parentView').updateFilter(this.get('column'), this.get('value'), 'string');
    }
  }),
  /**
   * Filter view for name column
   * Based on <code>filters</code> library
   */
  nameFilterView: filters.createTextView({
    column: 1,
    fieldType: 'filter-input-width',
    onChangeValue: function(){
      this.get('parentView').updateFilter(this.get('column'), this.get('value'), 'string');
    }
  }),
  /**
   * Filter view for name column
   * Based on <code>filters</code> library
   */
  sexFilterView: filters.createTextView({
    column: 1,
    fieldType: 'filter-input-width',
    onChangeValue: function(){
      this.get('parentView').updateFilter(this.get('column'), this.get('value'), 'string');
    }
  }),
  /**
   * Filter view for name column
   * Based on <code>filters</code> library
   */
 ageFilterView: filters.createTextView({
    column: 1,
    fieldType: 'filter-input-width',
    onChangeValue: function(){
      this.get('parentView').updateFilter(this.get('column'), this.get('value'), 'string');
    }
  }),
  /**
   * Filter view for name column
   * Based on <code>filters</code> library
   */
  postFilterView: filters.createTextView({
    column: 1,
    fieldType: 'filter-input-width',
    onChangeValue: function(){
      this.get('parentView').updateFilter(this.get('column'), this.get('value'), 'string');
    }
  })

})
