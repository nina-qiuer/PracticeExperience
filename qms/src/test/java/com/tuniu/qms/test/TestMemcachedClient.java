package com.tuniu.qms.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.whalin.MemCached.MemCachedClient;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:Spring.xml" })
public class TestMemcachedClient {

	@Autowired
	private MemCachedClient memCachedClient;
	
	@Test
	public void testSetAndGet() {
		memCachedClient.set("key987654321", "test");
		String str = (String) memCachedClient.get("key987654321");
	}

}
