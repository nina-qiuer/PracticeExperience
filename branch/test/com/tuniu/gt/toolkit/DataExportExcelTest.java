package com.tuniu.gt.toolkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

public class DataExportExcelTest {

	@Test
	public void testCreateExcel() {
		 HttpServletResponse response = null;
		 Map<String, List<Object>> data = new HashMap<String, List<Object>>();
		 List<Object> list1 = new ArrayList<Object>();
		 List<Object> list2 = new ArrayList<Object>();
		 List<Object> list3 = new ArrayList<Object>();
		 List<Object> list4 = new ArrayList<Object>();
		 
		 list1.add("1");
		 list1.add("2");
		 list1.add("3");
		 list1.add("4");
		 
		 list2.add("a");
		 list2.add("b");
		 list2.add("c");
		 list2.add("d");
		 
		 list3.add("啊");
		 list3.add("嗯");
		 list3.add("哈");
		 list3.add("heng");
		 
		 list4.add("hello");
		 list4.add("world");
		 list4.add("hello");
		 list4.add("world");
		 
		 data.put("第一列", list1);
		 data.put("第二列", list2);
		 data.put("第三列", list3);
		 data.put("第si列", list4);
		 
		 DataExportExcel dee = new DataExportExcel(data, "test.xls", "这是报表", response);
		 dee.createExcelFromMap();
	}
	
	@Test
	public void testCreateExcelFromList(){
		 HttpServletResponse response = null;
		 List<List<Object>> data = new ArrayList<List<Object>>();
		 List<Object> list1 = new ArrayList<Object>();
		 List<Object> list2 = new ArrayList<Object>();
		 List<Object> list3 = new ArrayList<Object>();
		 List<Object> list4 = new ArrayList<Object>();
		 
		 list1.add("column1");
		 list1.add("column2");
		 list1.add("column3");
		 list1.add("column4");
		 
		 list2.add("a");
		 list2.add("b");
		 list2.add("c");
		 list2.add("d");
		 
		 list3.add("啊");
		 list3.add("嗯");
		 list3.add("哈");
		 list3.add("heng");
		 
		 list4.add("hello1");
		 list4.add("world2");
		 list4.add("hello3");
		 list4.add("world4");
		 
		 data.add(list1);
		 data.add(list2);
		 data.add(list3);
		 data.add(list4);
		 
		 DataExportExcel dee = new DataExportExcel(data, "test2.xls", "这是报表", response);
		 dee.createExcelFromList();
	}
	


}
