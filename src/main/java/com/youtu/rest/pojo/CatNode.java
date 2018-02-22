package com.youtu.rest.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *@author:张晓芬
 *@date:2018年2月20日  上午10:39:52
**/
// CatNode分类节点
public class CatNode {
//分类列表的节点。包含u、n、i属性
		@JsonProperty("n")
		//定义了一个只能在该类内部才能访问的，名叫name的字符串变量
		//name的key值默认用上边式子的n来代替
		private String name;
		@JsonProperty("u")
		private String url;
		@JsonProperty("i")
		//List<?>用？而不是CatNode，是因为第三层是字符串，不同于第1，2 层的子节点u,n,i
		private List<?> item;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public List<?> getItem() {
			return item;
		}
		public void setItem(List<?> item) {
			this.item = item;
		}
		
	

}
