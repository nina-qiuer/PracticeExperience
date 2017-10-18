package com.tuniu.gt.complaint.service.impl;

import java.util.Arrays;

import net.sf.json.JSONObject;

import org.junit.Test;

import tuniu.frm.service.Bean;

import com.tuniu.gt.TestCaseExtend;
import com.tuniu.gt.complaint.service.IComplaintService;



public class ComplaintServiceTest  extends TestCaseExtend 
{
		
    IComplaintService service =  get("complaint_service_complaint_impl-complaint"); 
		
		@Test
        public void testtestName() throws Exception {
            System.out.println(JSONObject.fromObject(service.getAfterSaleReport(null)));
        }
		
		@Test
        public void testGetaaa() throws Exception {
		    System.out.println(service.getLeastCompalintCountUserName(Arrays.asList("高文艳","陈海涛")));
        }
}
