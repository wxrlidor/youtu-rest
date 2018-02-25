package com.youtu.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.youtu.common.utils.JsonUtils;
import com.youtu.mapper.TbContentMapper;
import com.youtu.pojo.TbContent;
import com.youtu.pojo.TbContentExample;
import com.youtu.pojo.TbContentExample.Criteria;
import com.youtu.rest.dao.JedisClient;
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
	@Autowired
	private JedisClient jedisClient;
	@Value("${REDIS_INDEX_CONTENT_HKEY}")
	private String REDIS_INDEX_CONTENT_HKEY;
	/**
	 * 根据内容分类id获取内容列表
	 */
	@Override
	public List<TbContent> getContentList(long contentId) {
		//从redis中取缓存
		try {
			String result = jedisClient.hget(REDIS_INDEX_CONTENT_HKEY, contentId+"");
			//如果结果不为空就返回值
			if(!StringUtils.isBlank(result)){
				//把json字符串转换成list返回
				List<TbContent> resultList = JsonUtils.jsonToList(result, TbContent.class);
				return resultList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//如果取不到缓存就去数据库查询后添加到缓存中
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(contentId);
		List<TbContent> list = contentMapper.selectByExample(example);
		
		//添加到redis缓存中
		try {
			//把list转换成json字符串
			String cacheData = JsonUtils.objectToJson(list);
			jedisClient.hset(REDIS_INDEX_CONTENT_HKEY, contentId+"", cacheData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

}
