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

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtil {

    /**
     * 判断字符串不为空
     * @param str
     * @return
     */
	public static boolean checkStr(String str) {
		boolean bool = true;
		if (str == null || "".equals(str.trim()))
			bool = false;
		return bool;
	}

	/**
	 * 判断对象不为空
	 * @param obj
	 * @return
	 */
	public static boolean checkObj(Object obj) {
		boolean bool = true;
		if (obj == null || "".equals(obj.toString().trim()))
			bool = false;
		return bool;
	}

	/**
	 * 返回对象不为空的toString方法
	 * 使用场景？
	 * @param obj
	 * @return
	 */
	public static String toString(Object obj) {
		return obj != null ? obj.toString().trim() : "";
	}
	
	/**
	 * 对象转数值
	 * String s = "20"; toInteger(s); // 20;
	 * Man man = new Man();
	 * man.toString(); // "15"
	 * toInteger(man); // 15
	 * @param obj
	 * @return
	 */
	public static Integer toInteger(Object obj) {
		return obj != null ? Integer.parseInt(obj.toString()) : 0;
	}

	/**
	 * 字符串转数值，如果字符串为空，则返回-1；
	 * String s = "";
	 * toInt(s); // -1
	 * @param str
	 * @return
	 */
	public static int toInt(String str) {
		return "".equals(str) ? -1 : Integer.parseInt(str);
	}

	/**
	 * 字符串编码从ISO8859_1转成GBK
	 * @param str
	 * @return
	 */
	public static String getISOToGBK(String str) {
		String strName = "";
		try {
			if (str != null) {
				strName = new String(str.getBytes("ISO8859_1"), "GBK");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strName;
	}

	/**
	 * 字符串编码从ISO8859_1转成UTF8
	 * @param str
	 * @return
	 */
	public static String getISOToUTF8(String str) {
		String strName = "";
		try {
			if (str != null) {
				strName = new String(str.getBytes("ISO8859_1"), "UTF8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strName;
	}

	/**
	 * 返回当前时间的yyyy-MM-dd格式字符串
	 * @return str
	 */
	public static String getNowDate() {
		Date nowDate = new Date();
		Calendar now = Calendar.getInstance();
		now.setTime(nowDate);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String str = formatter.format(now.getTime());
		// str=getNextDate(str,-1);
		return str;
	}

	/**
	 * 当前时间减去 30 天
	 * @return
	 */
	public static String getNowTimeLittleDate2() {
		Calendar c = Calendar.getInstance();
		c.add(c.DATE, -30);
		String time = "" + c.get(c.YEAR) + "-" + (c.get(c.MONTH) + 1) + "-"
				+ c.get(c.DATE) + " " + c.get(c.HOUR_OF_DAY) + ":"
				+ c.get(c.MINUTE) + ":" + c.get(c.SECOND);
		String returnstr = "";
		try {
			Date d = StringUtil.parses(time, "yyyy-MM-dd HH:mm:ss");
			// System.out.println(SimpleDateFormat(d,"yyyy-MM-dd HH:mm:ss"));
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd 00:00:00");
			returnstr = formatter.format(d);
		} catch (Exception e) {

		}
		return returnstr;
	}

	/**
	 * 返回当前时间的yyyy-MM-dd HH:mm:ss格式字符串
	 * @return str
	 */
	public static String getNowTime() {
		Date nowDate = new Date();
		Calendar now = Calendar.getInstance();
		now.setTime(nowDate);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = formatter.format(now.getTime());
		return str;
	}

	/**
	 * 返回两个时间的毫秒差值
	 * @param sDate
	 * @param eDate
	 * @return long
	 */
	public static long getTimeInMillis(String sDate, String eDate) {
		Timestamp sd = Timestamp.valueOf(sDate);
		Timestamp ed = Timestamp.valueOf(eDate);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sd);
		long timethis = calendar.getTimeInMillis();

		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(ed);
		long timeend = calendar2.getTimeInMillis();
		long thedaymillis = timeend - timethis;
		return thedaymillis;
	}

	/**
	 * 将给定的时间字符串格式化为yyyy-MM-dd HH:mm格式的字符串
	 * @param dTime
	 * @return str
	 */
	public static String formatDateTime(String dTime) {
		String dateTime = "";
		if (dTime != null && !"".equals(dTime)
				&& !dTime.startsWith("1900-01-01")) {
			Timestamp t = Timestamp.valueOf(dTime);
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm");
			dateTime = formatter.format(t);
		}
		return dateTime;
	}

	/**
	 * 将给定的时间字符串格式化为HH:mm:ss格式的字符串
	 * @param dTime
	 * @return str
	 */
	public static String formatTime(String dTime) {
		String dateTime = "";
		if (dTime != null && !"".equals(dTime)) {
			Timestamp t = Timestamp.valueOf(dTime);
			SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
			dateTime = formatter.format(t);
		}
		return dateTime;
	}

	/**
	 * 按给定格式解析字符串为时间对象
	 * @param strDate
	 * @param pattern
	 * @return Date
	 * @throws ParseException
	 */
	public static Date parses(String strDate, String pattern)
			throws ParseException {
		return new SimpleDateFormat(pattern).parse(strDate);
	}

	/**
	 * 当前日期是第几周
	 * @return str
	 */
	public static String getWeekOfYear() {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		String week = calendar.get(Calendar.WEEK_OF_YEAR) + "";
		return week;
	}

	/**
	 * 当前时间减去一年 返回yyyy-MM-dd HH:mm:ss格式字符串
	 * @return str
	 */
	public static String getNowTimeLittle() {
		Calendar c = Calendar.getInstance();
		c.add(c.YEAR, +1);
		String time = "" + c.get(c.YEAR) + "-" + (c.get(c.MONTH) + 1) + "-"
				+ c.get(c.DATE) + " " + c.get(c.HOUR_OF_DAY) + ":"
				+ c.get(c.MINUTE) + ":" + c.get(c.SECOND);
		String returnstr = "";
		try {
			Date d = StringUtil.parses(time, "yyyy-MM-dd HH:mm:ss");
			// System.out.println(SimpleDateFormat(d,"yyyy-MM-dd HH:mm:ss"));
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			returnstr = formatter.format(d);
		} catch (Exception e) {
		}
		return returnstr;
	}

	/**
	 * 当前时间减去一天,返回yyyy-MM-dd 00:00:00格式字符串
	 * @return str
	 */
	public static String getNowTimeLittleDate() {
		Calendar c = Calendar.getInstance();
		c.add(c.DATE, +1);
		String time = "" + c.get(c.YEAR) + "-" + (c.get(c.MONTH) + 1) + "-"
				+ c.get(c.DATE) + " " + c.get(c.HOUR_OF_DAY) + ":"
				+ c.get(c.MINUTE) + ":" + c.get(c.SECOND);
		String returnstr = "";
		try {
			Date d = StringUtil.parses(time, "yyyy-MM-dd HH:mm:ss");
			// System.out.println(SimpleDateFormat(d,"yyyy-MM-dd HH:mm:ss"));
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd 00:00:00");
			returnstr = formatter.format(d);
		} catch (Exception e) {

		}
		return returnstr;
	}

	/**
	 * 根据参数获取随机值的整位数
	 * @param num
	 * @return str
	 */
	public static String getRandom(int num) {
		return (Math.random() + "").substring(2, num + 2);
	}

	/**
	 * 返回两个时间的差值,小于秒用毫秒做单位
	 * @param sDate
	 * @param eDate
	 * @return str
	 */
	public static String getTimeInMillis(Date sDate, Date eDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sDate);
		long timethis = calendar.getTimeInMillis();

		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(eDate);
		long timeend = calendar2.getTimeInMillis();
		long thedaymillis = timeend - timethis;
		return thedaymillis < 1000 ? thedaymillis + "毫秒!"
				: (thedaymillis / 1000) + "秒钟!";
	}

	public static String showTrace() {
		StackTraceElement[] ste = new Throwable().getStackTrace();
		StringBuffer CallStack = new StringBuffer();

		for (int i = 1; i < ste.length; i++) {
			CallStack.append(ste[i].toString() + "\n");
			if (i > 4)
				break;
		}
		return CallStack.toString();
	}

	public static String checkTableDefKey(String[] key, String[] value,
			String name) {
		String str = "";
		for (int i = 0; i < key.length; i++) {
			if (name.equals(key[i])) {
				str = value[i];
				break;
			}
		}
		return str;
	}

	/**
	 * 判断字符串是否中文
	 * @param str
	 * @return
	 */
	public static boolean isChinese(String str) {
		String regEx = "[\\u4e00-\\u9fa5]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.find();
	}

	public static String getStrToGbk(String str) {
		String strName = "";
		try {
			if (str != null) {
				strName = new String(str.getBytes("UTF-8"), "GBK");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strName;
	}

	public static String getNextDate(String ts, int i) {
		Calendar now = Calendar.getInstance();
		Timestamp t = Timestamp.valueOf(ts + " 00:00:00.000");
		now.setTime(t);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		now.add(Calendar.DAY_OF_MONTH, +(i));
		String dt = formatter.format(now.getTime());
		return dt;
	}
	
	/**
	 * 对时间的分钟部分进行加减操作，返回yyyy-MM-dd HH:mm:ss格式字符串
	 * @param ts
	 * @param i
	 * @return
	 */
	public static String getNextTime(String ts, int i) {
		Calendar now = Calendar.getInstance();
		Timestamp t = Timestamp.valueOf(ts);
		now.setTime(t);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		now.add(Calendar.MINUTE, +(i));
		String dt = formatter.format(now.getTime());
		return dt;
	}

	/**
	 * 对时间的月份部分进行加减操作，返回yyyy-MM-dd HH:mm:ss格式字符串
	 * @param ts
	 * @param i
	 * @return
	 */
	public static String getNextMonth(String ts, int i) {
		Calendar now = Calendar.getInstance();
		Timestamp t = Timestamp.valueOf(ts);
		now.setTime(t);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM-dd HH:mm:ss");
		now.add(Calendar.MONTH, +(i));
		String dt = formatter.format(now.getTime());
		return dt;
	}

	/**
	 * 取Unix时间戳
	 * @param dateTime
	 * @return
	 */
	public static long getUnixTime(String dateTime) {
		Date date1 = null;
		Date date2 = null;
		try {
			date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTime);
			date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.parse("1970-01-01 08:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long l = (date1.getTime() - date2.getTime()) / 1000;
		return l;
	}

	/**
	 * 字符串首字母大写
	 * @param str
	 * @return
	 */
	public static String toFirstUpperCase(String str) {
		if (str == null || "".equals(str.trim()))
			return "";
		String firstChar = str.substring(0, 1).toUpperCase();
		String lastStr = str.substring(1);
		return firstChar + lastStr;
	}

	/**
	 * 判断一个字符串是不是数字
	 * @param str
	 * @return
	 */
	public static boolean isNum(String str) {
		boolean flg;
		try {
			Double.parseDouble(str);
			flg = true;
		} catch (Exception ex) {
			flg = false;
		}
		return flg;
	}

	/**
	 * 去除字符串数组中的重复值 add by tanzhouwen
	 * @param stringArray
	 * @return
	 */
	public static String[] filterRepeat(String[] stringArray) {
		ArrayList arrayList = new ArrayList();
		for (String str : stringArray) {
			if (!arrayList.contains(str)) {
				arrayList.add(str);
			}
		}
		return (String[]) arrayList.toArray(new String[] {});
	}

	/**
	 * 得到ID的in字句 如e.id in (1,3,4)
	 * 
	 * @param ids
	 *            如"1,2,3,"等
	 * @param alias
	 *            如"e.id"等
	 * @return
	 */
	public static String getIn300Ids(String ids, String alias) {
		String tempS[] = ids.split(",");
		int len = tempS.length;
		int which = 0;
		boolean isAnd = alias.indexOf("not") > 0;
		StringBuffer idsStr = new StringBuffer();
		idsStr.append("(");
		if (len > 300) {
			if (len % 300 > 0) {
				which = len / 300 + 1;
			} else {
				which = len / 300;
			}
			for (int i = 0; i < which; i++) {
				idsStr.append(alias + " in (");
				for (int j = 300 * i; j < 300 * i + 300; j++) {
					if (j < len) {
						idsStr.append(tempS[j] + ",");
					} else {
						break;
					}
				}
				idsStr = idsStr.replace(idsStr.lastIndexOf(","), idsStr
						.length(), "");
				if (i < which - 1) {
					if (isAnd) {
						idsStr.append(") and ");
					} else {
						idsStr.append(") or ");
					}
				} else {
					idsStr.append(")");
				}
			}
		} else {
			idsStr.append(alias + " in (");
			if (ids.lastIndexOf(",") == ids.length() - 1) {
				idsStr.append(ids.substring(0, ids.length() - 1));
			} else {
				idsStr.append(ids);
			}
			idsStr.append(" )");
		}
		idsStr.append(")");
		return idsStr.toString();
	}

	/**
	 * 格式字符串 格式：a,b,v====>'a','b','v'
	 * 
	 * @param str
	 * @return
	 */
	public static String getFormatString(String str) {
		String strArr[] = str.split(",");
		String retStr = "";
		for (int i = 0; i < strArr.length; i++) {
			if (i > 0) {
				retStr = retStr + ",";
			}
			retStr = retStr + "'" + strArr[i] + "'";
		}
		return retStr;
	}

	public static String strRound(double value, int decimalPlaces) {
		// 声明一个返
		String rval;

		// 如果小数位是0
		if (decimalPlaces == 0) {
			rval = String.valueOf(Math.round(value));
			return rval;
		}

		// 先将参数值转为String型,并找到小数点所在位置
		DecimalFormat dformat = new DecimalFormat("0.0000000");
		String str = dformat.format(value);
		int point = str.indexOf(".");
		// 分别得到小数点之前的字符与小数点之后的字符
		String beforePoint = str.substring(0, point);
		String afterPoint = str.substring(point + 1);

		// 如果小数位正好是要求的小数位数
		if (afterPoint.length() == decimalPlaces) {
			rval = String.valueOf(value);
		} else if (afterPoint.length() < decimalPlaces) {
			// 如果小数位数少于要求的小数位数,则在后面补零
			StringBuffer sb = new StringBuffer(afterPoint);
			for (int i = 0; i < decimalPlaces - afterPoint.length(); i++) {
				sb.append("0");
			}
			// 连接
			sb.insert(0, ".").insert(0, beforePoint);
			rval = sb.toString();
		} else {
			// 如果小数位数多于要求的小数位数,则要四舍五入

			// 不管怎样,先舍
			StringBuffer sb = null;
			sb = new StringBuffer(beforePoint);
			sb.append(".").append(afterPoint.substring(0, decimalPlaces));
			String val = sb.toString();
			// 得到要舍掉的那位数
			int temp = Integer.valueOf(afterPoint.charAt(decimalPlaces) + "");
			// 如果要舍掉位置的数<4
			if (temp < 4) {
				rval = val;
			} else {
				// 如果要舍掉的位置>4

				// 构造出要加的o.XX1,如期而至2.588,保留2位小数,就要在2.5的基础上加0.01
				sb = new StringBuffer("0.1");
				for (int i = 1; i < decimalPlaces; i++) {
					sb.insert(2, "0");
				}

				// 在已经舍掉的情况下加上该补足的0.XX1
				double dbl = Double.valueOf(val)
						+ Double.valueOf(sb.toString());
				val = String.valueOf(dbl);
				// 此时加完后可能变成1,如0.99,保留1位小数,四舍五入就变成1了,所以要再判断是否小数位够位数
				// 如果没有小数位,则补足小数位
				if (val.indexOf(".") == -1) {
					val += ".";
					for (int i = 0; i < decimalPlaces; i++) {
						val += "0";
					}

					rval = val;
				} else {
					// 如果有小数位,不管怎样都补足小数位
					val = val.substring(val.indexOf(".") + 1);
					sb = new StringBuffer(dbl + "");
					for (int i = 0; i < decimalPlaces - val.length(); i++) {
						sb.append("0");
					}
					rval = sb.toString();
				}
			}
		}

		// 因为double型不精确,所以最后再截一次
		point = rval.indexOf(".");
		return rval.substring(0, point + decimalPlaces + 1);

	}

	/**
	 * 删除input字符串中的html格式
	 * 
	 * @param input
	 * @return
	 */
	public static String splitAndFilterString(String input) {
		if (input == null || input.trim().equals("")) {
			return "";
		}
		// 去掉所有html元素,
		String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(
				"<[^>]*>", "");
		// String str = input.replaceAll("<[a-zA-Z]+[1-9]?[^><]*>",
		// "").replaceAll("</[a-zA-Z]+[1-9]?>", "");
		str = str.replaceAll("[(/>)<]", "");
		return str;
	}

	/**
	 * 返回数据库数据类型的编号
	 * @param typeName
	 * @param colScale
	 * @return
	 */
	public static int getColType(String typeName, String colScale) {
		int type = 0;
		if ("varchar".equalsIgnoreCase(typeName)
				|| "varchar2".equalsIgnoreCase(typeName)
				|| "text".equalsIgnoreCase(typeName)) {
			type = 1;
		} else if ("char".equalsIgnoreCase(typeName)) {
			type = 2;
		} else if ("datetime".equalsIgnoreCase(typeName)) {
			type = 6;
		} else if ("date".equalsIgnoreCase(typeName)) {
			type = 7;
		} else if ("time".equalsIgnoreCase(typeName)) {
			type = 8;
		}

		if (Integer.parseInt(colScale) > 0) {
			type = 5;
		} else {
			if ("numeric".equalsIgnoreCase(typeName)
					|| "long".equalsIgnoreCase(typeName)) {
				type = 3;
			} else if ("smallint".equalsIgnoreCase(typeName)
					|| "int".equalsIgnoreCase(typeName)
					|| "integer".equalsIgnoreCase(typeName)) {
				type = 4;
			}
		}
		return type;
	}

	public static String getDBDefault(int type, String def) {
		String deft = "";
		if (def != null && !"".equals(def)) {
			if (type == 1 || type == 2 || type == 6 || type == 7 || type == 8)
				deft = "default " + def;
			else
				deft = "default '" + def + "'";
		}
		return deft;
	}

	/**
	 * 获取数据库数据类型
	 * @param type
	 * @param len
	 * @param flt
	 * @return
	 */
	public static String getDBColType(int type, int len, int flt) {
		String typeName = "";
		if (type == 1) {
			typeName = "varchar(" + len + ")";
		} else if (type == 2) {
			typeName = "char(" + len + ")";
		} else if (type == 3) {
			typeName = "numeric(" + len + ")";
		} else if (type == 4) {
			typeName = "int";
		} else if (type == 5) {
			typeName = "numeric(" + len + "," + flt + ")";
		} else if (type == 6) {
			typeName = "datetime";
		} else if (type == 7) {
			typeName = "date";
		} else if (type == 8) {
			typeName = "time";
		}
		return typeName;
	}

	/**
	 * 获取java数据类型
	 * @param type
	 * @return
	 */
	public static String getStrColType(int type) {
		String typeName = "";
		if (type == 1) {
			typeName = "String";
		} else if (type == 2) {
			typeName = "Char";
		} else if (type == 3) {
			typeName = "Long";
		} else if (type == 4) {
			typeName = "Integer";
		} else if (type == 5) {
			typeName = "Double";
		} else if (type == 6) {
			typeName = "Date";
		} else if (type == 7) {
			typeName = "Date";
		} else if (type == 8) {
			typeName = "Date";
		}
		return typeName;
	}

	/**
	 * 获取htc列表排序字段数据类型
	 * @param type
	 * @return
	 */
	public static String getGridColType(int type) {
		String typeName = "";
		if (type == 1) {
			typeName = "String";
		} else if (type == 2) {
			typeName = "String";
		} else if (type == 3) {
			typeName = "Number";
		} else if (type == 4) {
			typeName = "Number";
		} else if (type == 5) {
			typeName = "Number";
		} else if (type == 6) {
			typeName = "Date";
		} else if (type == 7) {
			typeName = "Date";
		} else if (type == 8) {
			typeName = "Date";
		}
		return typeName;
	}

	/**
	 * 获取字符串的中文个数
	 * @author tanjianwen
	 * @return
	 */
	public static int getChineseCount(String str) {
		return str.getBytes().length - str.length();
	}

	public static String getSubString(String str, int pstart, int pend) {
		String resu = "";
		int beg = 0;
		int end = 0;
		int count1 = 0;
		char[] temp = new char[str.length()];
		str.getChars(0, str.length(), temp, 0);
		boolean[] bol = new boolean[str.length()];
		for (int i = 0; i < temp.length; i++) {
			bol[i] = false;
			if ((int) temp[i] > 255) {// 说明是中文
				count1++;
				bol[i] = true;
			}
		}

		if (pstart > str.length() + count1) {
			resu = null;
		}
		if (pstart > pend) {
			resu = null;
		}
		if (pstart < 1) {
			beg = 0;
		} else {
			beg = pstart - 1;
		}
		if (pend > str.length() + count1) {
			end = str.length() + count1;
		} else {
			end = pend;// 在substring的末尾一样
		}
		// 下面开始求应该返回的字符串
		if (resu != null) {
			if (beg == end) {
				int count = 0;
				if (beg == 0) {
					if (bol[0] == true)
						resu = null;
					else
						resu = new String(temp, 0, 1);
				} else {
					int len = beg;// zheli
					for (int y = 0; y < len; y++) {// 表示他前面是否有中文,不管自己
						if (bol[y] == true)
							count++;
						len--;// 想明白为什么len--
					}
					// for循环运行完毕后，len的值就代表在正常字符串中，目标beg的上一字符的索引值
					if (count == 0) {// 说明前面没有中文
						if ((int) temp[beg] > 255)// 说明自己是中文
							resu = null;// 返回空
						else
							resu = new String(temp, beg, 1);
					} else {// 前面有中文，那么一个中文应与2个字符相对
						if ((int) temp[len + 1] > 255)// 说明自己是中文
							resu = null;// 返回空
						else
							resu = new String(temp, len + 1, 1);
					}
				}
			} else {// 下面是正常情况下的比较
				int temSt = beg;
				int temEd = end - 1;// 这里减掉一
				for (int i = 0; i < temSt; i++) {
					if (bol[i] == true)
						temSt--;
				}// 循环完毕后temSt表示前字符的正常索引
				for (int j = 0; j < temEd; j++) {
					if (bol[j] == true)
						temEd--;
				}// 循环完毕后temEd-1表示最后字符的正常索引
				if (bol[temSt] == true)// 说明是字符，说明索引本身是汉字的后半部分，那么应该是不能取的
				{
					int cont = 0;
					for (int i = 0; i <= temSt; i++) {
						cont++;
						if (bol[i] == true)
							cont++;
					}
					if (pstart == cont)// 是偶数不应包含,如果pstart<cont则要包含
						temSt++;// 从下一位开始
				}
				if (bol[temEd] == true) {// 因为temEd表示substring
											// 的最面参数，此处是一个汉字，下面要确定是否应该含这个汉字
					int cont = 0;
					for (int i = 0; i <= temEd; i++) {
						cont++;
						if (bol[i] == true)
							cont++;
					}
					if (pend < cont)// 是汉字的前半部分不应包含
						temEd--;// 所以只取到前一个
				}
				if (temSt == temEd) {
					resu = new String(temp, temSt, 1);
				} else if (temSt > temEd) {
					resu = null;
				} else {
					resu = str.substring(temSt, temEd + 1);
				}
			}
		}
		return resu;// 返回结果
	}
	
	/**
	 * 判断请求IP是否包含在ip数组中
	 * @param requestIP
	 * @param ips
	 * @return
	 */
	public static boolean checkIP(String requestIP, String[] ips) {
		for (String ip : ips) {
			if (requestIP.equals(ip)) return true;
			if ((ip.indexOf("-")) >= 0) {//x.x.x.x-x.x.x.x
				String[] ipPart = requestIP.split("\\.");
				String[] ipOpenPart = ip.split("\\-")[0].split("\\.");
				String[] ipEndPart = ip.split("\\-")[1].split("\\.");
				if (ipOpenPart.length != ipPart.length || ipEndPart.length != ipPart.length) continue;
				boolean flag = true;
				for (int i = 0; i < ipPart.length; i++) {
					if (ipPart[i].compareTo(ipOpenPart[i]) < 0 || ipPart[i].compareTo(ipEndPart[i]) > 0) {
						flag = false;
					} 
				}
				if (flag) return true;
			}
		}
		return false;
	}
	
	/*// 获得请求客户端的IP
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}*/
	
	/**
	 * 效验字符串是否符合正则表达式格式
	 * @param value
	 * @param regexp
	 * @return
	 */
	public static boolean checkRegexp(String value, String regexp) {
		return Pattern.compile(regexp).matcher(value).matches();
	}
	
	/**
	 * @param str
	 * @return
	 */
	public static String repalceNewLineCharacter(String str){
		if(!checkStr(str)){
			return "";
		}
		String result = str.trim();
		result = result.replaceAll("\n","");
		result = result.replaceAll("\r","");
		return result;
	}
}