/*
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
package com.zjhc.hcdream.util;

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RequestUtil {
    
	//异步请求返回
	public static void responseOut(String encoding, String data, HttpServletResponse response) {
		response.setContentType("text/html; charset=" + encoding);
		try {
			PrintWriter pw = response.getWriter();
			pw.print(data);
	        pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//异步请求返回
	public static void responseTextOut(String encoding, String data, HttpServletResponse response) {
		response.setContentType("text/plain; charset=" + encoding);
		try {
			PrintWriter pw = response.getWriter();
			pw.print(data);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//获取request对象中所有参数，并设置到map中
	public static Map getMapByRequest(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Enumeration enu = request.getParameterNames();
		while(enu.hasMoreElements()) {
            String paraName = (String)enu.nextElement();   
            String paraValue = request.getParameter(paraName); 
            if(paraValue!=null && !"".equals(paraValue)){
            	map.put(paraName, paraValue.trim());
            }           
		}	
		if(StringUtil.checkObj(request.getAttribute("sUrl"))){
			map.put("sURLs", request.getAttribute("sUrl").toString());
		}
		return map;
	}
	
	//根据KEY获取session对应的对象
	public static Object getSessionObject(HttpServletRequest request, String key) {
		HttpSession session = request.getSession();
		return SessionUtil.getAttribute(key, session);
	}
	
	/**
	 * 以流的方式将文件响应到客户端，一般用于文件下载
	 * 此时的文件，是放在了应用服务器以外的的目录，需要先读到，然后再写出(即不是A标签能直接下载的)
	 */
	public static void readFile(String path,String fileName,
								HttpServletResponse response)throws Exception{
		ServletOutputStream out =null;
		InputStream inStream=null;
		try{
			File pathsavefile = new File(path); 
			if(!StringUtil.checkStr(fileName)){
				String pths[]=path.replaceAll("/", "\\\\").split("\\\\");
				fileName=pths[pths.length-1];//保存窗口中显示的文件名 
			}
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Pragma", "public");
			response.setContentType("APPLICATION/OCTET-STREAM;charset=GBK"); 
			//fileName=new String(fileName.getBytes(),"UTF-8");//response.encodeURL();//转码 
			fileName=new String(fileName.getBytes("GBK"),"ISO8859-1");//
			response.setHeader("Content-Disposition", "attachment; filename=\""+fileName+"\""); 
			out = response.getOutputStream(); 
			inStream=new FileInputStream(pathsavefile); 
			byte[] b = new byte[1024]; 
			int len; 
			while((len=inStream.read(b)) >0) 
			out.write(b,0,len); 
			response.setStatus( response.SC_OK ); 
			response.flushBuffer(); 
		}catch(IOException ex){
			throw ex;
		}finally{
			if(out!=null)out.close();
			if(inStream!=null)inStream.close(); 
		}
	}
}