package com.dear.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.dear.service.AgvService;
import com.dear.bean.recordDetails;
import com.dear.utils.PostTencentConstants;

@Controller
@RequestMapping(value = "/QywxWeb")
public class QywxWeb {
	@Resource(name = "agvService")
	private AgvService agvService;

	/**
	 * @name 中文名称
	 * @description 根据页面URL和页面ticket生成接入JS-SDK接入码
	 * @time 创建时间:2018年7月23日19:58:05
	 * @param url ：接入js-sdk的页面地址 ticket：通过token生成的接入js-sdk的ticket
	 * @return 请求返回接入js-sdk所需json对象
	 * @author 朱浩
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	@RequestMapping(value = "/getconfig")
	@ResponseBody
	public JSONObject getConfig(@RequestBody JSONObject json) {
		JSONObject rul = new JSONObject();
		String token = getTencentToken();
		String ticket = getTencentJSSDKTicket(token);
		String w = json.getString("w");
		String code = json.getString("code");
		String url = "https://www.digital-ricoh.com/AGV/html/" + w + ".html?code=" + code + "&state=";
//		String url = "http://172.31.76.164:8080/AGV/html/t.html";
		rul = getSignature(url, ticket);
		System.out.println(rul);
		return rul;
	}

	public JSONObject getSignature(String url, String ticket) {
		JSONObject rul = new JSONObject();

		String noncestr = getRandomString(16);
		String timestamp = (int) (System.currentTimeMillis() / 1000) + "";
		String sign = "";
		sign += PostTencentConstants.QYWX_GET_JSAPITICKET_URL_PARAM_TICKET + PostTencentConstants.QYWX_EQUAL + ticket
				+ PostTencentConstants.QYWX_AND + PostTencentConstants.QYWX_GET_JSAPITICKET_URL_PARAM_NONCESTR
				+ PostTencentConstants.QYWX_EQUAL + noncestr + PostTencentConstants.QYWX_AND
				+ PostTencentConstants.QYWX_GET_JSAPITICKET_URL_PARAM_TIMESTAMP + PostTencentConstants.QYWX_EQUAL
				+ timestamp + PostTencentConstants.QYWX_AND + PostTencentConstants.QYWX_GET_JSAPITICKET_URL_PARAM_URL
				+ PostTencentConstants.QYWX_EQUAL + url;
		String signature = "";
		try {
			// 指定sha1算法
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.update(sign.getBytes());
			// 获取字节数组
			byte messageDigest[] = digest.digest();
			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			// 字节数组转换为 十六进制 数
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			signature = hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		rul.put(PostTencentConstants.QYWX_GET_JSAPITICKET_RETURN_SIGNATURE, signature);
		rul.put(PostTencentConstants.QYWX_GET_JSAPITICKET_URL_PARAM_NONCESTR, noncestr);
		rul.put(PostTencentConstants.QYWX_GET_JSAPITICKET_URL_PARAM_TIMESTAMP, timestamp);
		// System.out.println(rul);
		return rul;
	}

	/**
	 * @name 中文名称
	 * @description 根据扫码登录返回的code值和应用token值获取扫码登录人用户id(注：id为企业微信扫码人id)
	 * @time 创建时间:2018年7月23日14:07:28
	 * @param code :前台扫码登录后微信js插件会自动跳转链接，且附带该code。 accessToken: 应用调用接口凭证，
	 *             该类下getTencentToken()方法可直接获取。
	 * @return 请求返回userid
	 * @author 朱浩
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public String getTencentUserInfo(String code, String accessToken) {
		String url = "";
		url += PostTencentConstants.QYWX_GET_USERINFO_URL + PostTencentConstants.QYWX_QUERY;
		url += PostTencentConstants.QYWX_GET_USERINFO_URL_PARAM_TOKEN + PostTencentConstants.QYWX_EQUAL + accessToken;
		url += PostTencentConstants.QYWX_AND;
		url += PostTencentConstants.QYWX_GET_USERINFO_URL_PARAM_CODE + PostTencentConstants.QYWX_EQUAL + code;
		JSONObject userInfoJson = sendPostRequest(url);
		String errcode = userInfoJson.getString(PostTencentConstants.QYWX_GET_USERINFO_RETURN_ERRCODE);
		if (PostTencentConstants.QYWX_GET_USERINFO_RETURN_SUCCESS_CODE.equals(errcode)) {
			return userInfoJson.getString(PostTencentConstants.QYWX_GET_USERINFO_RETURN_USERID);
		} else {
		}
		return null;
	}

	/**
	 * @name 中文名称
	 * @description 获取企业微信Token（应用调用接口凭证） 该值具有以下特性： 1、通用性：该企业微信应用下通用， 2、
	 *              有效性：该值有效时长为7200秒， 3、微信端不可长期无限制请求，推荐使用缓存保存改值
	 * @time 创建时间:2018年7月23日14:07:28
	 * @param 请求URL地址 ，参数需自行拼接
	 * @return 请求返回token值
	 * @author 朱浩
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public String getTencentToken() {
		String url = "";
		url += PostTencentConstants.QYWX_GET_TOKEN_URL + PostTencentConstants.QYWX_QUERY;
		url += PostTencentConstants.QYWX_GET_TOKEN_URL_PARAM_CORPID + PostTencentConstants.QYWX_EQUAL
				+ PostTencentConstants.QYWX_CORPID;
		url += PostTencentConstants.QYWX_AND + PostTencentConstants.QYWX_GET_TOKEN_URL_PARAM_CORPSECRET
				+ PostTencentConstants.QYWX_EQUAL;
		url += PostTencentConstants.QYWX_CORPSECRET;
		JSONObject tokenJson = sendPostRequest(url);
		String errcode = tokenJson.getString(PostTencentConstants.QYWX_GET_TOKEN_RETURN_ERRCODE);
		if (PostTencentConstants.QYWX_GET_TOKEN_RETURN_SUCCESS_CODE.equals(errcode)) {
			// System.out.println("返回值" + tokenJson.toJSONString() + " "
			// + System.currentTimeMillis());
			PostTencentConstants.QYWX_TOKEN = tokenJson.getString(PostTencentConstants.QYWX_GET_TOKEN_RETURN_TOKEN);
			PostTencentConstants.QYWX_TIME = System.currentTimeMillis();
			// System.out
			// .println("token:"
			// + tokenJson
			// .getString(PostTencentConstants.QYWX_GET_TOKEN_RETURN_TOKEN));
			return tokenJson.getString(PostTencentConstants.QYWX_GET_TOKEN_RETURN_TOKEN);
		} else {
		}
		return null;
	}

	/**
	 * @name 中文名称
	 * @description 根据token获取接入js-sdk的ticket
	 * @time 创建时间:2018年7月23日19:33:47
	 * @param accessToken : 应用调用接口凭证， 该类下getTencentToken()方法可直接获取。
	 * @return 请求返回ticket值。
	 * @author 朱浩
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public String getTencentJSSDKTicket(String token) {
		String url = "";
		url += PostTencentConstants.QYWX_GET_JSAPITICKET_URL + PostTencentConstants.QYWX_QUERY;
		url += PostTencentConstants.QYWX_GET_JSAPITICKET_URL_PARAM_TOKEN + PostTencentConstants.QYWX_EQUAL + token;
		JSONObject ticketJson = sendPostRequest(url);
		String errcode = ticketJson.getString(PostTencentConstants.QYWX_GET_JSAPITICKET_RETURN_ERRCODE);
		if (PostTencentConstants.QYWX_GET_JSAPITICKET_RETURN_SUCCESS_CODE.equals(errcode)) {
			System.out
					.println("ticket:" + ticketJson.getString(PostTencentConstants.QYWX_GET_JSAPITICKET_RETURN_TICKET));
			return ticketJson.getString(PostTencentConstants.QYWX_GET_JSAPITICKET_RETURN_TICKET);
		} else {
		}
		return null;
	}

	/**
	 * @name 中文名称
	 * @description 相关说明
	 * @time 创建时间:2018年7月23日11:48:33
	 * @param 请求URL地址 ，参数需自行拼接
	 * @return 请求返回json对象
	 * @author 朱浩
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	private JSONObject sendPostRequest(String url) {
		StringBuffer stringBuffer = new StringBuffer("");
		try {
			URL postUrl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-Type", "application/json");
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				stringBuffer.append(line);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.parseObject(stringBuffer.toString());
	}

	/**
	 * @name 中文名称
	 * @description 获取指定位数的随机字符串(包含小写字母、大写字母、数字,0<length)
	 * @time 创建时间:2018年7月23日14:17:21
	 * @param 获取字符串长度
	 * @return 对应长度的随机字符串
	 * @author 朱浩
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	private String getRandomString(int length) {
		// 随机字符串的随机字符库
		String KeyString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuffer sb = new StringBuffer();
		int len = KeyString.length();
		for (int i = 0; i < length; i++) {
			sb.append(KeyString.charAt((int) Math.round(Math.random() * (len - 1))));
		}
		return sb.toString();
	}

	@RequestMapping(value = "/getUserid")
	@ResponseBody
	//
	public Map<String, Object> getTruck(@RequestBody JSONObject json) {
		String token = null;
		if (System.currentTimeMillis() > PostTencentConstants.QYWX_TIME + 7190000) {
			token = getTencentToken();
		} else {
			token = PostTencentConstants.QYWX_TOKEN;
		}

		String userid = getTencentUserInfo(json.getString("code"), token);
		System.out.println(token);
		System.out.println(userid);
		Map<String, Object> resultmap = new HashMap<String, Object>();
		resultmap.put("userid", userid);
		return resultmap;
	}

	@RequestMapping(value = "/saveRecord")
	@ResponseBody
	//
	public Map<String, Object> saveRecord(@RequestBody JSONObject json) {
		String agv = json.getString("agv");
		String userid = json.getString("userid");
		String car = json.getString("car");
		String datetime = json.getString("datetime");
		Map<String, Object> resultmap = new HashMap<String, Object>();

//		if (agv == null || agv.length() == 0 || userid == null || userid.length() == 0 || datetime == null
//				|| datetime.length() == 0 || car == null || car.length() == 0) {
//			resultmap.put("errcode", 1);
//			return resultmap;
//		}

		try {

			agvService.saveRecord(agv,car, userid, datetime);
			agvService.u_agv_status(agv);
			resultmap.put("errcode", 0);

		} catch (Exception e) {
			e.printStackTrace();
			resultmap.put("errcode", 1);
		}

		return resultmap;
	}
	
	
	@RequestMapping(value = "/getRecord")
	@ResponseBody
	//
	public Map<String, Object> getRecord() {
		Map<String, Object> resultmap = new HashMap<String, Object>();
		try {

			List<recordDetails> obj = agvService.getRecord();
			resultmap.put("obj", obj);
			resultmap.put("errcode", 0);

		} catch (Exception e) {
			e.printStackTrace();
			resultmap.put("errcode", 1);
		}

		return resultmap;
	}

}
