package com.tuniu.gt.toolkit;

import org.junit.Test;

public class MailerTest {

	@Test
	public void test() {
		EmailData ed = new EmailData("robot@tuniu.com", new String[]{"liangjie@tuniu.com"}, new String[]{ "yuliang@tuniu.com"}, "今天天气不错", "哈哈哈哈哈，阿毕，robot不是ssl方式发送的，被你误导了。。。", "html");
		
	}

}
