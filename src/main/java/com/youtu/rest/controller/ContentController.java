package com.youtu.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youtu.common.pojo.YouTuResult;
import com.youtu.common.utils.ExceptionUtil;
import com.youtu.pojo.TbContent;
import com.youtu.rest.service.ContentService;

/**内容管理Controller
 *@author:张晓芬
 *@date:2018年2月24日  上午10:21:18
**/
@Controller
@RequestMapping("/content")
public class ContentController {
	@Autowired
	private ContentService contentService;
	@RequestMapping("/list/{contentCategoryId}")
	@ResponseBody
	public YouTuResult getContentList(@PathVariable Long contentCategoryId){
		try{
			List<TbContent> list = contentService.getContentList(contentCategoryId);
			return YouTuResult.ok(list);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
			return YouTuResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
}
