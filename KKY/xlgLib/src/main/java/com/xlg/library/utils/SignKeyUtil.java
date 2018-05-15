package com.xlg.library.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

public class SignKeyUtil {

	public static String getNonceString() {
		return UUID.randomUUID().toString().trim().replaceAll("-", "");
	}

	public static String getSignKey() {
		return "ghOwfR7Wz44cbKU93KVSxwYXPGloNu";
	}

	/**
	 * 创建秘钥url
	 * 
	 * @param url
	 * @param currentToken
	 * @return
	 */
	public static String createGetSignUrl(String url, String currentToken) {

		Map<String, Object> params = unserialize(url);
		Map<String, Object> ret = new LinkedHashMap<String, Object>();

		ArrayList<Entry<String, Object>> lists = new ArrayList<Entry<String, Object>>(
				params.entrySet());
		Collections.sort(lists, new Comparator<Entry<String, Object>>() {

			public int compare(Entry<String, Object> arg0,
					Entry<String, Object> arg1) {

				return arg0.getKey().compareTo(arg1.getKey());
			}
		});
		Iterator<Entry<String, Object>> iterator = lists.iterator();
		while (iterator.hasNext()) {
			Entry<String, Object> next = iterator.next();

			ret.put(next.getKey(), next.getValue());
		}

		String serailizedKey = serialize(ret);
		String key = serailizedKey + "&key=" + currentToken;
		String sign = getMD5(key);
		return "?"+url + "&sign=" + sign.toUpperCase();
	}
	/**
	 * 创建秘钥url
	 *
	 * @param url
	 * @param currentToken
	 * @return
	 */
	public static String createPostSignUrl(String url, String currentToken) {

		Map<String, Object> params = unserialize(url);
		Map<String, Object> ret = new LinkedHashMap<String, Object>();

		ArrayList<Entry<String, Object>> lists = new ArrayList<Entry<String, Object>>(
				params.entrySet());
		Collections.sort(lists, new Comparator<Entry<String, Object>>() {

			public int compare(Entry<String, Object> arg0,
					Entry<String, Object> arg1) {

				return arg0.getKey().compareTo(arg1.getKey());
			}
		});
		Iterator<Entry<String, Object>> iterator = lists.iterator();
		while (iterator.hasNext()) {
			Entry<String, Object> next = iterator.next();

			ret.put(next.getKey(), next.getValue());
		}

		String serailizedKey = serialize(ret);

//		LogUtil.i("第一步：按照key升排序序列化转成字符串："+serailizedKey);

		String key = serailizedKey + "&key=" + currentToken;

//		LogUtil.i("第二步：拼接SignKey："+key);

		String sign = "";
		try {
			sign = getMD5Code(key, "UTF-8").toUpperCase();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

//		LogUtil.i("第三步：md5转32位转大写："+sign);

		return sign;

//		LogUtil.i("第三步：md5转32位转大写："+getMD5(key).toUpperCase());
//		return getMD5(key).toUpperCase();
	}

	/**
	 * 创建序列化
	 * 
	 * @param params
	 * @return
	 */
	private static String serialize(Map<String, Object> params) {

		Iterator<Entry<String, Object>> iter = params.entrySet().iterator();
		StringBuffer sb = new StringBuffer();
		while (iter.hasNext()) {
			Entry<String, Object> next = iter.next();

			sb.append(next.getKey() + "=" + next.getValue() + "&");
		}
		if (sb.length() > 0) {
			sb.setLength(sb.length() - 1);
		}
		String normailize = sb.toString();
		return normailize;
	}

	/**
	 * 反序列化
	 * 
	 * @param string
	 * @return
	 */
	private static Map<String, Object> unserialize(String string) {
		Map<String, Object> ret = new LinkedHashMap<String, Object>();

		int index = string.indexOf("?");
		String paramStr = string;
		if (index != -1) {
			paramStr = string.substring(index + 1);
		}

		Iterator<String> iterator = Arrays.asList(paramStr.split("&"))
				.listIterator();
		while (iterator.hasNext()) {
			String item = iterator.next();

			String[] split = item.split("=");
			if (split.length > 1) {

				String key = split[0];
				String value = split[1];
				if (null != value && !"".equals(value)) {
					ret.put(key, value);
				}
			}

		}
		return ret;
	}

	public static String getMD5(String str) {

		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

		char[] charArray = str.toCharArray();

		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++) {
			byteArray[i] = (byte) charArray[i];
		}
		byte[] md5Bytes = md5.digest(byteArray);

		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	/**
	 *
	 * 使用md5加密后的字符串
	 * @author sangy
	 * @date 2015-9-28 上午9:34:00
	 *
	 * @param strObj
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getMD5Code(String strObj,String encoding) throws UnsupportedEncodingException {
		String resultString = null;
		try {
			resultString = new String(strObj.getBytes(encoding));
			MessageDigest md = MessageDigest.getInstance("MD5");
			// md.digest() 该函数返回值为存放哈希值结果的byte数组
			resultString = byteToString(md.digest(strObj.getBytes(encoding)));
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
		return resultString;
	}

	// 转换字节数组为16进制字串
	public static String byteToString(byte[] bByte) {
		StringBuffer sBuffer = new StringBuffer();
		for (int i = 0; i < bByte.length; i++) {
			sBuffer.append(byteToArrayString(bByte[i]));
		}
		return sBuffer.toString();
	}

	// 返回形式为数字跟字符串
	private static String byteToArrayString(byte bByte) {
		int iRet = bByte;
		if (iRet < 0) {
			iRet += 256;
		}
		int iD1 = iRet / 16;
		int iD2 = iRet % 16;
		return strDigits[iD1] + strDigits[iD2];
	}

	// 全局数组
	private final static String[] strDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
}
