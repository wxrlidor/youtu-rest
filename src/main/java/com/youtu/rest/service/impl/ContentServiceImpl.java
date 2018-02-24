package com.youtu.rest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youtu.mapper.TbContentMapper;
import com.youtu.pojo.TbContent;
import com.youtu.pojo.TbContentExample;
import com.youtu.rest.service.ContentService;

/**
 * 内容管理
 * 
 * @author:张晓芬
 * @date:2018年2月23日 下午10:45:27
 **/
@Service
public class ContentServiceImpl implements ContentService {
	@Autowired
	private TbContentMapper contentMapper;

	@Override
	public List<TbContent> getContentList(long contentCid) {
		// 根据内容分类id查询内容列表
		TbContentExample example = new TbContentExample();
		com.youtu.pojo.TbContentExample.Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(contentCid);
		// 执行查询
		List<TbContent> list = contentMapper.selectByExample(example);
		// 返回值：pojo列表
		return list;
	}

}
