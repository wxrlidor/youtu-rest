package com.youtu.rest.test;
/**
 *@author:王贤锐
 *@date:2018年1月20日  下午4:02:15
**/

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrJTest {
	/**
	 * 写入solr
	 * @throws SolrServerException
	 * @throws IOException
	 */
	@Test
	public void addDoucment() throws SolrServerException, IOException{
		//创建一个solr服务对象
		SolrServer solrServer = new HttpSolrServer("http://192.168.168.121:8080/solr");
		//创建文档对象并加入field属性
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "Test001");
		document.addField("item_title", "商品2号");
		//写入索引库中并提交
		solrServer.add(document);
		solrServer.commit();
	}
	/**
	 * 删除solr
	 * @throws Exception
	 */
	@Test
	public void deleteDocument() throws Exception {
		//创建一连接
		SolrServer solrServer = new HttpSolrServer("http://192.168.168.121:8080/solr");
		//根据id删除
		solrServer.deleteById("Test001");
		//删除所有
		//solrServer.deleteByQuery("*:*");
		solrServer.commit();
	}
}
