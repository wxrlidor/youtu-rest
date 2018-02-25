package com.youtu.rest.test;
/**
 *@author:王贤锐
 *@date:2018年1月19日  下午3:23:07
**/

import java.util.HashSet;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JedisTest {
	/**
	 * 测试单机版jedis
	 */
	@Test
	public void jedisTestSingle(){
		//创建jedis对象
		Jedis jedis = new Jedis("192.168.1.121",6379);
		//使用jeids对象执行命令，与redis客户端一样
		jedis.set("keytest", "first value");
		String string = jedis.get("keytest");
		System.out.println(string);
		//关闭jedis对象
		jedis.close();
	}
	/**
	 * 使用连接池连接
	 */
	@Test
	public void jedisPoolTest(){
		//创建连接池对象
		JedisPool pool = new JedisPool("192.168.168.121", 6379);
		//获取jedis对象
		Jedis jedis = pool.getResource();
		String string = jedis.get("keytest");
		System.out.println(string);
		jedis.close();
		pool.close();
	}
	/**
	 * 测试集群
	 */
	@Test
	public void jedisClusterTest(){
		//创建结点列表set数据
		HashSet<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.168.121", 7001));
		nodes.add(new HostAndPort("192.168.168.121", 7002));
		nodes.add(new HostAndPort("192.168.168.121", 7003));
		nodes.add(new HostAndPort("192.168.168.121", 7004));
		nodes.add(new HostAndPort("192.168.168.121", 7005));
		nodes.add(new HostAndPort("192.168.168.121", 7006));
		//创建集群对象，自带连接池
		JedisCluster cluster = new JedisCluster(nodes);
		//cluster.set("key1", "addesff");
		String string = cluster.get("key1");
		System.out.println(string);
	}
	/**
	 * spring单机版测试
	 */
	@Test
	public void jedisSpringSingle(){
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		JedisPool pool = (JedisPool) applicationContext.getBean("redisClient");
		Jedis jedis = pool.getResource();
		String string = jedis.get("key1");
		System.out.println(string);
		jedis.close();
		pool.close();
	}
	/**
	 * spring集群版测试
	 */
	@Test
	public void jedisSpring(){ 
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		JedisCluster jedisCluster =  (JedisCluster) applicationContext.getBean("redisClient");
		String string = jedisCluster.get("key1");
		System.out.println(string);
		jedisCluster.close();
	}
}
