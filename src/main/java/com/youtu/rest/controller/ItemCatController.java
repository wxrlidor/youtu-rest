package com.youtu.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.youtu.rest.pojo.CatResult;
import com.youtu.rest.service.ItemCatService;

/**
 * @author:张晓芬
 * @date:2018年2月20日 下午11:43:33
 **/
@Controller
public class ItemCatController {
	// 调用service
	@Autowired
	public ItemCatService itemCatService;
//	// 接口url 原来的@RequestMapping("/itemcat/list")不能解决乱码
//	@RequestMapping(value = "/itemcat/list", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
//	// 字符串直接响应
//	@ResponseBody
//	// callback是方法名称，callback = myfunction
//	public String getItemCatList(String callback) {
//		CatResult catResult = itemCatService.getItemCatList();
//		// catResult是pojo对象
//		// 把pojo转换成json字符串
//		String json = JsonUtils.objectToJson(catResult);
//		// 拼装返回值，callback是方法名称
//		String result = callback + "(" + json + ");";
//		return result;
//	}
	@RequestMapping(value = "/itemcat/list", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
	@ResponseBody
	public Object getItemCatList(String callback){
		CatResult catResult = itemCatService.getItemCatList();
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(catResult);
		mappingJacksonValue.setJsonpFunction(callback);
		return mappingJacksonValue;
	}
}