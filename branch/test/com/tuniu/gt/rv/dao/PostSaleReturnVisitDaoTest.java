/**
 * 
 */
package com.tuniu.gt.rv.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;

import tuniu.frm.service.Bean;

import com.tuniu.gt.TestCaseExtend;
import com.tuniu.gt.returnvisit.dao.impl.PostSaleReturnVisitDao;
import com.tuniu.gt.returnvisit.entity.PostSaleReturnVisitEntity;
import com.tuniu.gt.returnvisit.service.impl.PostSaleReturnVisitServiceImpl;
import com.tuniu.gt.returnvisit.vo.ReportResultSetVo;
import com.tuniu.gt.returnvisit.vo.ReturnVisitReportVo;

/**
 * @author jiangye
 *
 */
public class PostSaleReturnVisitDaoTest extends TestCaseExtend {
	
	
    PostSaleReturnVisitDao dao = (PostSaleReturnVisitDao) Bean.get("returnVisit_dao_impl-postSaleReturnVisit");
    PostSaleReturnVisitServiceImpl service = (PostSaleReturnVisitServiceImpl)Bean.get("rv_service_impl-postSaleReturnVisit");
	
	@Test
	public void testDao() throws Exception {
		assertNotNull(dao);
		assertNotNull(service);
		
	}
	
	@Test
    public void testGet() throws Exception {
        System.out.println(JSONObject.fromObject(dao.get(1)));
    }
	
	@Test
    public void testAdd() throws Exception {
        PostSaleReturnVisitEntity e  = new PostSaleReturnVisitEntity();
        e.setComplaintId(1233);
        e.setTel("18551847579");
        e.setBusinessUnitName("酒店BU");
        e.setDepartmentName("二级部门");
        e.setGroupName("幸运小组");
        e.setReturnVisitDate(new Date());
        System.out.println(dao.insert(e));
        
    }
	
	@Test
    public void testUpdate() throws Exception {
	    PostSaleReturnVisitEntity e  = new PostSaleReturnVisitEntity();
	    e.setId(1);
	    e.setScore(3);
//	    e.setUnsatisfyReason(2);
	    e.setUpdateTime(new Date());
	    System.out.println(dao.update(e));
    }
	
	@Test
    public void testGetCount() throws Exception {
	    dao.getCount(1233,"出游前");
    }
	
	@Test
    public void testGetByParam() throws Exception {
	    Map<String, Object> paramMap = new HashMap<String, Object>();
	    paramMap.put("tel", "18551847579");
	    paramMap.put("receivedTime", new Date());
//	    paramMap.put("returnType", "satisfaction");
	    paramMap.put("returnType", "reason");
//        System.out.println(dao.getIdByParam(paramMap));
    }
	
	@Test
    public void testList() throws Exception {
        System.out.println(JSONArray.fromObject(dao.fetchList()));
    }
	
	@Test
    public void testReport() throws Exception {
	    
        List<ReportResultSetVo>  voList = dao.getReport(null);
        List<ReturnVisitReportVo> reportList = new ArrayList<ReturnVisitReportVo>();
        for(ReportResultSetVo reportResultSetVo : voList) {
//            ReturnVisitReportVo reportVo = 
        }
    }
	
	@Test
    public void testService() throws Exception {
    }
	
	@Test
    public void testGetReportTotal() throws Exception {
	    String fields = "businessUnitName,departmentName,groupName";
    }
	
	
	
}
