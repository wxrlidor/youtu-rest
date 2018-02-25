package com.youtu.rest.service;
/**
 *@author:王贤锐
 *@date:2018年1月22日  下午3:25:55
**/

import com.youtu.common.pojo.YouTuResult;

public interface ItemService {
	YouTuResult getItemBaseInfo(long itemId);
	
	YouTuResult getItemDesc(long itemId);
	
	YouTuResult getItemParam(long itemId);
}
