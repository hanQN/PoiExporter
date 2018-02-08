package com.gempoll.store.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jfinal.core.Controller;

public class BaseController extends Controller {

	//统一返回JSON格式
	public void renderJson(int code,String msg,Object data){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", code);
		result.put("msg", msg);
		result.put("data", data);
		renderJson(result);
	}
	
	//计算两个日期时间差（秒）
	public int dateBuff(Date a,Date b){
		try {
			return (int)(a.getTime() - b.getTime())/1000;
		} catch (Exception e) {
			return 0;
		}
	}
	
	public static void main(String[] args) throws Exception {
		DecimalFormat df = new DecimalFormat("#.#");
		System.err.println(df.format(0.5));
	}
	//获取某个日期的前n天的日期
	public String beforeDate(Date date, int n) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // add方法中的第二个参数n中，正数表示该日期后n天，负数表示该日期的前n天
        calendar.add(Calendar.DATE, n);
        Date date1 = calendar.getTime();
        return sdf.format(date1);
	}
	
	// 判断一个字符串是否含有数字
	public boolean HasDigit(String content) {
	    boolean flag = false;
	    Pattern p = Pattern.compile(".*\\d+.*");
	    Matcher m = p.matcher(content);
	    if (m.matches()) {
	        flag = true;
	    }
	    return flag;
	}

}
