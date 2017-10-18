package com.tuniu.qms.test.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.tuniu.qms.qc.dao.PunishStandardMapper;
import com.tuniu.qms.qc.model.PunishStandard;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:Spring.xml"})
public class TestPunishStandardMapper {

	@Autowired
	private PunishStandardMapper mapper;
	
	@Test
	public void testAdd() {
		PunishStandard obj = new PunishStandard();
		obj.setLevel("一级甲等");
		obj.setDescription("公司损失金额超过2万元以上的投诉");
		mapper.add(obj);
	}

	@Test
	public void testDel() {
		mapper.delete(158819);
	}

	@Test
	public void testGet() {
		PunishStandard obj = mapper.get(158819);
		System.out.println(JSON.toJSONString(obj));
	}
	
	@Test
	public void testUpdate() {
		PunishStandard obj = mapper.get(158819);
//		obj.setUsedBy("投诉质检");//一级乙等	公司损失金额1万元~2万元（含）的投诉	500	5
//		obj.setLevel("一级甲等");
//		obj.setDescroption("公司损失金额超过2万元以上的投诉");
//		obj.setPunishAmount(1000);
		mapper.update(obj);

	}
	
	@Test
	public void testList() {
		System.out.println(mapper.list(null));
	}
	

}
