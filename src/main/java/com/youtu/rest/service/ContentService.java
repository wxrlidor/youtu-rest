package com.youtu.rest.service;
/**
 *@author:张晓芬
1 *@date:2018年2月23日  下午10:32:03
**/

import java.util.List;

import com.youtu.pojo.TbContent;

public interface ContentService {
	List<TbContent> getContentList(long contentCid);

}
