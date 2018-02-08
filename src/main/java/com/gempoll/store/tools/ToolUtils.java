package com.gempoll.store.tools;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.oreilly.servlet.Base64Encoder;

public class ToolUtils {
	
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
		if (ip.indexOf(",") > -1) {
			ip = ip.substring(0, ip.indexOf(","));
		}
		return ip;
	}

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(ToolUtils.class);

	/**
	 * double精度调整
	 * 
	 * @param doubleValue
	 *            需要调整的值123.454
	 * @param format
	 *            目标样式".##"
	 * @return
	 */
	public static String decimalFormatToString(double doubleValue, String format) {
		DecimalFormat myFormatter = new DecimalFormat(format);
		String formatValue = myFormatter.format(doubleValue);
		return formatValue;
	}

	/**
	 * 获取UUID by jdk
	 * 
	 * @author daniel
	 * @return
	 */
	public static String getUuidByJdk(boolean is32bit) {
		String uuid = UUID.randomUUID().toString();
		if (is32bit) {
			return uuid.toString().replace("-", "");
		}
		return uuid;
	}

	/**
	 * 获取系统流水号
	 * 
	 * @param length
	 *            指定流水号长度
	 * @param isNumber
	 *            指定流水号是否全由数字组成
	 * @return
	 */
	public static String getSysJournalNo(int length, boolean isNumber) {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		if (uuid.length() > length) {
			uuid = uuid.substring(0, length);
		} else if (uuid.length() < length) {
			for (int i = 0; i < length - uuid.length(); i++) {
				uuid = uuid + Math.round(Math.random() * 9);
			}
		}
		if (isNumber) {
			return uuid.replaceAll("a", "1").replaceAll("b", "2")
					.replaceAll("c", "3").replaceAll("d", "4")
					.replaceAll("e", "5").replaceAll("f", "6");
		} else {
			return uuid;
		}
	}
	
    /**
     * 随机生成一串字符串
     * @param length
     * @return
     */
    public static final String randomString(int length) {
        if (length < 1) {
            return null;
        }
    	Random randGen = new Random();
        char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
        char[] randBuffer = new char[length];
        for (int i=0; i<randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
        }
        return new String(randBuffer);
    }
    
    //判断字符串对象是否为空
    public static boolean isEmpty(String str){
    	if(null==str || str.isEmpty()){
    		return true;
    	}
    	return false;
    }
    
    
    /**
	 * 生成一串数字
	 * 
	 * @param length
	 * @return
	 */
	public static String randomInt(int length) {
		if (length < 1) {
			return null;
		}
		Random randGen = new Random();
		char[] numbersAndLetters = ("0123456789").toCharArray();
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(9)];
		}
		return new String(randBuffer);
	} 

	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		for(int i =0;i<70;i++){
			System.err.print("'"+new ToolUtils().randomString(16)+"',");
		}
		
	}
}
