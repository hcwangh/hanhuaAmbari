<%@ page language="java" import="java.util.*" contentType="text/html;
charset=UTF-8" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta  http-equiv='Content-Type'  content='text/html;  charset=utf-8'>
    <script type="text/javascript">
    </script>
    <style>
        html {
            /*overflow-x:hidden;*/
            /*overflow-y:hidden;*/
        }
    </style>
</head>

<body>
<div>
    <table  width="100%"   height="100%"<%-- style="margin-top: -11px;"--%>>
        <td  width=145 style="border: 0px">
            <iframe  src="left.html" width=100%  height=100%  frameborder="0" scrolling="no"></iframe>
        </td>
        <td >
            <div  style="height:100%" style='overflow:hidden;'>
                <iframe  width=100%  height=100%  name="mainFrame"
                         id="mainFrame"  src="main.html" frameborder="0" onLoad="iFrameHeight()" scrolling="no">
                </iframe>
                <script type="text/javascript" language="javascript">
                    function iFrameHeight() {
                        var ifm= document.getElementById("mainFrame");
                        var subWeb = document.frames ? document.frames["mainFrame"].document :ifm.contentDocument;
                        var arr = subWeb.URL.split('/');
                        if(arr[arr.length-1] == 'ast'){
                            ifm.height = 1300;
                        }
                        /*else{
                           if(ifm != null && subWeb != null) {
                                   ifm.height = subWeb.body.scrollHeight;
                           }
                        }*/
                        parent.document.body.scrollTop=0;
                    }

                    function scrollTop1(){
                        parent.document.body.scrollTop=0;
                    }

                </script>
            </div>
        </td>
    </table>
</div>
</body>
</html>
