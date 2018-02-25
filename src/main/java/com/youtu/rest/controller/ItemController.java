package com.youtu.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youtu.common.pojo.YouTuResult;
import com.youtu.rest.service.ItemService;

/**
 * 商品相关服务控制器
 *@author:王贤锐
 *@date:2018年1月22日  下午3:28:44
**/
@Controller
@RequestMapping("/item")
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	/**
	 * 接收商品id，返回用youturesult包装的商品信息
	 * @param itemId
	 * @return
	 */
	@RequestMapping("/baseInfo/{itemId}")
	@ResponseBody
	public YouTuResult getItemBaseInfo(@PathVariable Long itemId){
		return itemService.getItemBaseInfo(itemId);
	}
	/**
	 * 返回商品描述信息
	 * @param itemId
	 * @return
	 */
	@RequestMapping("/desc/{itemId}")
	@ResponseBody
	public YouTuResult getItemDesc(@PathVariable Long itemId) {
		YouTuResult result = itemService.getItemDesc(itemId);
		return result;
	}
	/**
	 * 取得商品规格参数
	 * @param itemId
	 * @return
	 */
	@RequestMapping("/param/{itemId}")
	@ResponseBody
	public YouTuResult getItemParam(@PathVariable Long itemId) {
		YouTuResult result = itemService.getItemParam(itemId);
		return result;
	}
}
