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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class PropsUtil {
	public static ResourceBundle rb = null;
	/*static{
		try
		{
			rb = new PropertyResourceBundle(new FileInputStream(System.getProperty("user.dir") + 
					(System.getProperty("os.name").contains("Windows") ? "/src":"") + "/cdpistorm.properties"));
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}*/
	
	public static void init(String keyFileName){
		try
		{
			/*System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));
			System.out.println(PropsUtil.class.getClassLoader().getResource(""));
			System.out.println(PropsUtil.class.getClassLoader().getResource("/"));*/
			String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
			String filePath = rootPath +  keyFileName + ".properties";
			/*rb = new PropertyResourceBundle(new FileInputStream(System.getProperty("user.dir") +
					(System.getProperty("os.name").contains("Windows") ? "/src/main/resources":"") + "/" + keyFileName + ".properties"));*/
			rb = new PropertyResourceBundle(new FileInputStream(filePath));
//			rb = new PropertyResourceBundle(Runtime.getRuntime().getClass().getClassLoader().getResourceAsStream(keyFileName + ".properties"));
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	public static String getString(String key){
		return rb.getString(key);
	}

	public static int getInt(String key){
		return Integer.valueOf(rb.getString(key));
	}

	public static void main(String[] args) {
		PropsUtil.init("sys");
		System.out.println(PropsUtil.getString("repoName"));
	}

}
