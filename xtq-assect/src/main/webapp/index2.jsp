<%@ page language="java" import="java.util.*" contentType="text/html;
charset=UTF-8" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<frameset rows="0,*" cols="*" frameborder="no " border="0" framespacing="0">
  <frame src="" name="topFrame" scrolling="no" noresize="noresize" id="topFrame" title="topFrame" />
  <frameset cols="0,145,10,*" framespacing="0" frameborder="no">
    <frame src="" name="frame_1" scrolling="no" noresize="noresize" id="frame_1" title="frame_1" />
    <frame src="left.html" name="leftFrame" scrolling="no" noresize="noresize" id="leftFrame" title="leftFrame" />
    <frame src="" name="frame_2" scrolling="no" noresize="noresize" id="frame_2" title="frame_2" />
    <frame src="main.html" name="mainFrame" id="mainFrame" title="mainFrame" />
  </frameset>
  </frameset>
<noframes><body>
</body>
</noframes></html>