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

<%@ page contentType="text/html; charset=UTF-8" %>
<% request.setCharacterEncoding("utf-8"); %>
<% response.setCharacterEncoding("utf-8"); %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <title>INDEX</title>
    <%--<script src="<%=basePath%>static/js/jquery-2.1.1.js"></script>--%>
    <script src="js/jquery-2.1.1.js"></script>
    <script>
        var path = "<%=basePath%>";
        console.log('path:  '  +path);
        function test(){
            $.ajax({
                url:  path+"ast",
                type: 'post', dataType: 'text', cache: false, async: false,
                data: "method=m&id=1&age=25",
                //                random: Math.random(),
                success: function (data) {
                    console.log('success:  '  +data);
                },
                error: function (data) {
                    console.log(data);
                }
            });
        }
        function testJq(){
            console.log($("#divtest").text());
//            alert($("#divtest").text());
        }
    </script>
</head>
<body>
INDEX JSP
<h2>${greeting}</h2>
<br/>
<br/>
<br/>
<a href="#" onclick="test()">ajax</a>
<br/>
<br/>
<br/>
<form method="post" action="<%=basePath%>ast">
    <input type="text"  name="method"  value="m" />
    <input type="text"  name="age"  value="123" />
    <%--<button type="submit" value="submit" style="width:50px;height: 20px" > form</button>--%>
    <input type="submit" value="add" />
</form>
<br/>
<br/>
<br/>
<div id="divtest">divconten</div>
<br/>
<br/>
<br/>
<a href="#" onclick="testJq()" >test Jquery</a>
</body>
</html>
