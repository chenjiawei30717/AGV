package com.dear.controller;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dear.utils.PostTencentConstants;

@RestController 
// 等同于同时加上了@Controller和@ResponseBody
public class HelloController {
    // 访问/hello或者/hi任何一个地址，都会返回一样的结果
    @RequestMapping(value = { "/hello", "/hi" }, method = RequestMethod.GET)
    public String say() {
        return "hi you!!!";
    }
    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/api/Base/LoginApp")
	@ResponseBody 
	public JSONObject login(@RequestBody JSONObject json1) {
    	System.out.println("接收请求");
    	System.out.println(json1.toJSONString());
		String str="{\"StatusCode\":200,\"Message\":null,\"Data\":{\"U\":{\"USER_ID\":\"4403001\",\"ORG_CODE\":\"4403Z01\",\"ORG_NAME\":\"XX维修项目\",\"DEPT_CODE\":\"ZHJD\",\"DEP_NAME\":\"事业部\",\"DIST_ID\":\"440300\",\"CODE\":\"4403Z01YWB110\",\"IS_SYS\":false,\"BUILD_ID\":null,\"ROLE_ID\":\"U007\",\"UROLE_TYPE\":3,\"ROLE_NAME\":\"项目经理\",\"PASSWORD\":\"e10adc3949ba59abbe56e057f20f883e\",\"GENDER\":1,\"BIND_PHONE\":true,\"FACE\":null,\"MEMO\":null,\"ADDRESS\":null,\"DUTY_TYPE\":null,\"ACTION_TYPE\":\"-1,B\",\"EMAIL\":null,\"FIX\":false,\"IS_ACC\":false,\"IS_OUT\":false,\"JOB_TYPE\":null,\"NAME\":\"李经理\",\"PHONE\":\"13200000001\",\"POSITION\":null,\"TITLE\":null,\"SUPPER_ID\":null,\"SUPPER_NAME\":null,\"SUPPER_TYPE\":null,\"SUPPER_CONTACT\":null,\"SUPPER_ADDRESS\":null,\"SUPPER_TEL\":null,\"CREATE_TIME\":\"2018-08-29T00:00:00\",\"CREATE_USER_ID\":\"admin\",\"MODIFY_USER_ID\":null,\"MODIFY_TIME\":null,\"STATE\":1,\"sys_updatetime\":\"2018-08-30T09:39:27.101386Z\"},\"P\":[{\"URIGHT_ID\":1,\"URIGHT_NAME\":\"工作台\",\"RIGHT_TYPE\":null,\"PARENT_ID\":null,\"LEVEL\":1,\"ORDER_NO\":0,\"MODULE_NAME\":\"home\",\"IS_MENU\":false,\"ICON\":\"iconfont icon-gongzuotai\",\"BACKGROUND_COLOR\":\"\",\"FUNC\":\"pages/home.html\",\"PARAMETER\":\"\",\"IS_SINGLE\":false,\"MEMO\":\"\",\"PARENT_NAME\":\"\"},{\"URIGHT_ID\":5,\"URIGHT_NAME\":\"维修\",\"RIGHT_TYPE\":null,\"PARENT_ID\":1,\"LEVEL\":2,\"ORDER_NO\":1,\"MODULE_NAME\":\"repair\",\"IS_MENU\":true,\"ICON\":\"iconfont icon-weixiubaoyang\",\"BACKGROUND_COLOR\":\"#FFBD4D\",\"FUNC\":\"home/task-main.html\",\"PARAMETER\":null,\"IS_SINGLE\":false,\"MEMO\":null,\"PARENT_NAME\":\"\"},{\"URIGHT_ID\":6,\"URIGHT_NAME\":\"模块2\",\"RIGHT_TYPE\":null,\"PARENT_ID\":1,\"LEVEL\":2,\"ORDER_NO\":2,\"MODULE_NAME\":\"polling\",\"IS_MENU\":true,\"ICON\":\"iconfont icon-xunjianguanli\",\"BACKGROUND_COLOR\":\"#6BA7F0\",\"FUNC\":\"home/polling/order-detail.html\",\"PARAMETER\":null,\"IS_SINGLE\":false,\"MEMO\":null,\"PARENT_NAME\":\"\"},{\"URIGHT_ID\":7,\"URIGHT_NAME\":\"模块3\",\"RIGHT_TYPE\":null,\"PARENT_ID\":1,\"LEVEL\":2,\"ORDER_NO\":3,\"MODULE_NAME\":\"maintain\",\"IS_MENU\":true,\"ICON\":\"iconfont icon-Maintenance\",\"BACKGROUND_COLOR\":\"#5CBD9C\",\"FUNC\":\"home/maintain/order-detail.html\",\"PARAMETER\":null,\"IS_SINGLE\":false,\"MEMO\":null,\"PARENT_NAME\":\"\"},{\"URIGHT_ID\":3,\"URIGHT_NAME\":\"我的\",\"RIGHT_TYPE\":null,\"PARENT_ID\":null,\"LEVEL\":1,\"ORDER_NO\":4,\"MODULE_NAME\":\"my\",\"IS_MENU\":true,\"ICON\":\"iconfont icon-wode\",\"BACKGROUND_COLOR\":null,\"FUNC\":\"pages/my.html\",\"PARAMETER\":null,\"IS_SINGLE\":false,\"MEMO\":null,\"PARENT_NAME\":\"\"},{\"URIGHT_ID\":4,\"URIGHT_NAME\":\"模块4\",\"RIGHT_TYPE\":null,\"PARENT_ID\":1,\"LEVEL\":2,\"ORDER_NO\":4,\"MODULE_NAME\":\"alarm\",\"IS_MENU\":true,\"ICON\":\"iconfont icon-alarm\",\"BACKGROUND_COLOR\":\"#F27475\",\"FUNC\":\"home/alarm/order-detail.html\",\"PARAMETER\":null,\"IS_SINGLE\":false,\"MEMO\":null,\"PARENT_NAME\":\"\"}],\"R\":true}}";
		JSONObject json=JSON.parseObject(str);
		return json;
    }
}
