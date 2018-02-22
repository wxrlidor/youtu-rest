package com.youtu.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youtu.mapper.TbItemCatMapper;
import com.youtu.pojo.TbContentCategoryExample.Criteria;
import com.youtu.pojo.TbItemCat;
import com.youtu.pojo.TbItemCatExample;
import com.youtu.rest.pojo.CatNode;
import com.youtu.rest.pojo.CatResult;
import com.youtu.rest.service.ItemCatService;

/**商品分类服务,查询分类列表
 *@author:张晓芬
 *@date:2018年2月20日  上午11:07:59
**/
//加@Service能够让类ItemCatController调用真正实现功能的实现类ItemCatServiceImpl
//而不是调用接口ItemCatService
@Service
            // 类名                                                     //ItemCatService添加的接口
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	//注入Mapper查询数据库
		private TbItemCatMapper itemCatMapper;
		
		public CatResult getItemCatList() {
			
			CatResult catResult = new CatResult();
			//查询分类列表
			catResult.setData(getCatList(0));
			return catResult;
		}
		
		/**
		 * 
		 * <p>Title: getCatList</p>
		 * <p>Description: </p>
		 * @param parentId
		 * @return
		 */
		private List<?> getCatList(long parentId) {
			//创建查询条件
			TbItemCatExample example = new TbItemCatExample();
			com.youtu.pojo.TbItemCatExample.Criteria criteria = example.createCriteria();
	          //根据parentId查询
			criteria.andParentIdEqualTo(parentId);
			//执行查询，遍历list来创建节点
			List<TbItemCat> list = itemCatMapper.selectByExample(example);
			//返回值list
			List resultList = new ArrayList<>();
			//向list中添加节点
			int count = 0;
			for (TbItemCat tbItemCat : list) {
				//判断是否为父节点
				if (tbItemCat.getIsParent()) {
					CatNode catNode = new CatNode();
					//第一层执行如下操作
					if (parentId == 0) {
						catNode.setName("<a href='/products/"+tbItemCat.getId()+".html'>"+tbItemCat.getName()+"</a>");
						//第二层执行如下操作
					} else {
						catNode.setName(tbItemCat.getName());
					}
					catNode.setUrl("/products/"+tbItemCat.getId()+".html");
					catNode.setItem(getCatList(tbItemCat.getId()));				
					resultList.add(catNode);
					count++;
					if(parentId == 0 && count >= 14){
						break;
				}
				//如果是叶子节点
				} else {
					resultList.add("/products/"+tbItemCat.getId()+".html|" + tbItemCat.getName());
				}
			}
			return resultList;
		}

	}
