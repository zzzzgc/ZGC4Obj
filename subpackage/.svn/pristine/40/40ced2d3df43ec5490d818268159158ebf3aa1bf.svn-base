package com.xinxing.subpackage.data.common.json;

import com.alibaba.fastjson.JSON;

public class JsonUtils {
	/***
	 * java对象转json字符串
	 * @param obj
	 * @return
	 */
	public static String obj2Json(Object obj){
		String str = JSON.toJSONString(obj);
		return str;
	}
	
	/***
	 * json转Java对象
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T json2Obj(String json, Class<T> clazz){
		return JSON.parseObject(json, clazz);
	}
	
	
	/**
	 * 将内容中有英文双引号的信息替换为中文双引号
	 * @param s
	 * @return
	 */
	public static String formateStr2StandJson(String s){
		char[] temp = s.toCharArray();		
		int n = temp.length;
		for(int i =0;i<n;i++){
			if(temp[i]==':'&&temp[i+1]=='"'){
					for(int j =i+2;j<n;j++){
						if(temp[j]=='"'){
							if(temp[j+1]!=',' &&  temp[j+1]!='}'){
								temp[j]='”';
							}else if(temp[j+1]==',' ||  temp[j+1]=='}'){
								break ;
							}
						}
					}	
			}
		}		
		return new String(temp);
	}
}
