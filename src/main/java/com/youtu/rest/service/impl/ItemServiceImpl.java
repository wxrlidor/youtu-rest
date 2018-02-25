package com.youtu.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.youtu.common.pojo.YouTuResult;
import com.youtu.common.utils.JsonUtils;
import com.youtu.mapper.TbItemDescMapper;
import com.youtu.mapper.TbItemMapper;
import com.youtu.mapper.TbItemParamItemMapper;
import com.youtu.pojo.TbItem;
import com.youtu.pojo.TbItemDesc;
import com.youtu.pojo.TbItemParamItem;
import com.youtu.pojo.TbItemParamItemExample;
import com.youtu.pojo.TbItemParamItemExample.Criteria;
import com.youtu.rest.dao.JedisClient;
import com.youtu.rest.service.ItemService;

/**
 * 商品相关服务
 * 
 * @author:王贤锐
 * @date:2018年1月22日 下午3:26:36
 **/
@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private TbItemMapper tbItemMapper;
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	@Autowired
	private TbItemParamItemMapper tbItemParamItemMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${REDIS_ITEM_KEY}")
	private String REDIS_ITEM_KEY;
	@Value("${REDIS_ITEM_TIME}")
	private int REDIS_ITEM_TIME;

	/**
	 * 接收商品id，根据商品id查询商品基本信息。返回一个商品的pojo，使用taotaoResult包装返回。
	 */
	@Override
	public YouTuResult getItemBaseInfo(long itemId) {
		// 先去查询缓存中商品基本信息
		try {
			String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":base");
			// 如果不为空就转换成item对象并返回结果
			if (!StringUtils.isBlank(json)) {
				TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
				return YouTuResult.ok(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 根据id取得商品基础信息
		TbItem item = tbItemMapper.selectByPrimaryKey(itemId);

		// 添加到缓存中
		try {
			jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":base", JsonUtils.objectToJson(item));
			// 设置有效时间
			jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":base", REDIS_ITEM_TIME);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return YouTuResult.ok(item);
	}

	/**
	 * 取商品描述
	 */
	@Override
	public YouTuResult getItemDesc(long itemId) {
		// 添加缓存
		try {
			// 添加缓存逻辑
			// 从缓存中取商品信息，商品id对应的信息
			String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":desc");
			// 判断是否有值
			if (!StringUtils.isBlank(json)) {
				// 把json转换成java对象
				TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
				return YouTuResult.ok(itemDesc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 创建查询条件
		TbItemDesc itemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);

		try {
			// 把商品信息写入缓存
			jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":desc", JsonUtils.objectToJson(itemDesc));
			// 设置key的有效期
			jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":desc", REDIS_ITEM_TIME);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return YouTuResult.ok(itemDesc);
	}

	/**
	 * 取商品规格参数
	 */
	@Override
	public YouTuResult getItemParam(long itemId) {
		// 添加缓存
		try {
			// 添加缓存逻辑
			// 从缓存中取商品信息，商品id对应的信息
			String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":param");
			// 判断是否有值
			if (!StringUtils.isBlank(json)) {
				// 把json转换成java对象
				TbItemParamItem paramItem = JsonUtils.jsonToPojo(json, TbItemParamItem.class);
				return YouTuResult.ok(paramItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 根据商品id查询规格参数
		// 设置查询条件
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		// 执行查询
		List<TbItemParamItem> list = tbItemParamItemMapper.selectByExampleWithBLOBs(example);
		if (list != null && list.size() > 0) {
			TbItemParamItem paramItem = list.get(0);
			try {
				// 把商品信息写入缓存
				jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":param", JsonUtils.objectToJson(paramItem));
				// 设置key的有效期
				jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":param", REDIS_ITEM_TIME);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return YouTuResult.ok(paramItem);
		}
		return YouTuResult.build(400, "无此商品规格");
	}

}
