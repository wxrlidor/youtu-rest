package com.youtu.rest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.youtu.common.pojo.YouTuResult;
import com.youtu.common.utils.ExceptionUtil;
import com.youtu.rest.dao.impl.JedisClientSingle;
import com.youtu.rest.service.RedisService;
/**
 * 后台系统内容管理 缓存同步服务
 * @author 王贤锐
 * @date :2018年2月25日  下午2:35:56
 */
@Service
public class RedisServiceImpl implements RedisService {

	@Autowired
	private JedisClientSingle jedisClientSingle;
	
	@Value("${REDIS_INDEX_CONTENT_HKEY}")
	private String REDIS_INDEX_CONTENT_HKEY;
	/**
	 * 首页分类管理同步缓存
	 */
	@Override
	public YouTuResult syncContent(long contentCid) {
		try {
			jedisClientSingle.hdel(REDIS_INDEX_CONTENT_HKEY, contentCid + "");
		} catch (Exception e) {
			e.printStackTrace();
			return YouTuResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return YouTuResult.ok();
	}

}
