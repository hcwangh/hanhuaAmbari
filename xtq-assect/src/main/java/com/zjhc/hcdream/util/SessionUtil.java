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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

/**
 * @author gaotao
 * 对session进行包装，从session读写数据统一用此工具类进行操作
 * 如果到时将session数据存入分布式缓存服务器，有可以统一进行缓存读写操作
 */
public class SessionUtil {
    
	//记录当前正登录系统的所有账号
	private static Map<String,Map<String,Object>> singleLogin=new HashMap<String, Map<String,Object>>();
	
	public static Map<String, Map<String, Object>> getSingleLogin() {
		return singleLogin;
	}

	public static void setSingleLogin(Map<String, Map<String, Object>> singleLogin) {
		SessionUtil.singleLogin = singleLogin;
	}

	/**
	 * 设置session属性值
	 * @param key
	 * @param obj
	 * @param session
	 */
	public static void  setAttribute(String key,Object obj,HttpSession session){
		session.setAttribute(key, obj);
	}
	
	/**
	 * 获取session属性值
	 * @param key
	 * @param session
	 * @return
	 */
	public static Object getAttribute(String key,HttpSession session){
		return session.getAttribute(key);
	}
	
}