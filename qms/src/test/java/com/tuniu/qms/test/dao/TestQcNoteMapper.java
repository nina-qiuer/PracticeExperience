package com.tuniu.qms.test.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tuniu.qms.qc.dao.QcNoteMapper;
import com.tuniu.qms.qc.dto.QcNoteDto;
import com.tuniu.qms.qc.model.QcNote;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:Spring.xml"})
public class TestQcNoteMapper {

	@Autowired
	private QcNoteMapper qcNoteMapper;
	
	@Test
	public void testAdd() {
		QcNote note = new QcNote();
		note.setQcBillId(3595);
		note.setAddPerson("姜野");
		note.setContent("测试");
		qcNoteMapper.add(note);
	}

	@Test
	public void testDel() {
		qcNoteMapper.delete(19674);
	}

	@Test
	public void testGet() {
		System.out.println(qcNoteMapper.get(19674));
	}
	
	@Test
	public void testUpdate() {
		QcNote obj = new QcNote();
		obj.setId(19674);
		obj.setContent("测试更新");
		qcNoteMapper.update(obj);
	}
	
	@Test
	public void testList() {
		QcNoteDto dto = new QcNoteDto();
		dto.setQcBillId(3595);
		System.out.println(qcNoteMapper.list(dto));
	}
	
	@Test
	public void testCount() {
		QcNoteDto dto = new QcNoteDto();
		dto.setQcBillId(2861);
		System.out.println(qcNoteMapper.count(dto));
	}
	
	

}
