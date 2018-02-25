package com.youtu.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youtu.common.pojo.YouTuResult;
import com.youtu.rest.service.RedisService;

/**
 * 缓存同步controller
 *@author:王贤锐
 *@date:2018年1月19日  下午10:41:51
**/
@Controller
@RequestMapping("/cache")
public class RedisController {
	@Autowired
	private RedisService redisService;
	/**
	 * 首页内容分类--同步缓存
	 * @param contentCid
	 * @return
	 */
	@RequestMapping("/sync/content/{contentCid}")
	@ResponseBody
	public YouTuResult contentSysnc(@PathVariable long contentCid){
		return redisService.syncContent(contentCid);
	}
}
