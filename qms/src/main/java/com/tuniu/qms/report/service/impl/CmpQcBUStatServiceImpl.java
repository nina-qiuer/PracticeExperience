package com.tuniu.qms.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.common.util.MathUtil;
import com.tuniu.qms.report.dao.CmpQcBUStatMapper;
import com.tuniu.qms.report.dto.CmpQcBUStatDto;
import com.tuniu.qms.report.model.CmpQcBUStat;
import com.tuniu.qms.report.service.CmpQcBUStatService;

@Service
public class CmpQcBUStatServiceImpl implements CmpQcBUStatService {
	
	@Autowired
	private CmpQcBUStatMapper mapper;

	@Override
	public void add(CmpQcBUStat obj) {}

	@Override
	public void delete(Integer id) {}

	@Override
	public void update(CmpQcBUStat obj) {}

	@Override
	public CmpQcBUStat get(Integer id) {
		return null;
	}

	@Override
	public void loadPage(CmpQcBUStatDto dto) {}
	
	@Override
	public List<CmpQcBUStat> list(CmpQcBUStatDto dto) {
		List<CmpQcBUStat> dataList = mapper.list(dto);
		for (CmpQcBUStat data : dataList) {
			int doneTotalNum = data.getQcDoneNum() + data.getCancelNum();
			data.setDoneTotalNum(doneTotalNum);
			
			if (doneTotalNum > 0) {
				double cancelRate = MathUtil.div(data.getCancelNum(), doneTotalNum, 3);
				data.setCancelRate(cancelRate);
			}
		}
		return dataList;
	}
	
	/**
	 * 部门数据汇总
	 * @param dataList
	 * @return
	 */
	@Override
	public CmpQcBUStat departmentTotal(List<CmpQcBUStat> dataList) {
		
		int[] nums = new int[17];
		CmpQcBUStat departmentData = new CmpQcBUStat();
		
		for(CmpQcBUStat data : dataList){
			//质检单数
			nums[0] += data.getQcNum();
			//质检中单数
			nums[1] += data.getQcIngNum();
			//质检完成单数
			nums[2] += data.getQcDoneNum(); 
			//撤销单数
			nums[3] += data.getCancelNum();
			//总完成单数
			nums[4] += data.getDoneTotalNum();
			//来电投诉
			nums[5] += data.getComeFrom1();
			//其他
			nums[6] += data.getComeFrom2();
			//回访
			nums[7] += data.getComeFrom3();
			//门市
			nums[8] += data.getComeFrom4();
			//旅游局
			nums[9] += data.getComeFrom5();
			//微博
			nums[10] += data.getComeFrom6();
			//CS邮箱
			nums[11] += data.getComeFrom7();
			//点评
			nums[12] += data.getComeFrom8();
			//网站
			nums[13] += data.getComeFrom9();
			//当地质检
			nums[14] += data.getComeFrom10();
			//变更
			nums[15] += data.getComeFrom11();
			//APP
			nums[16] += data.getComeFrom12();
		}
		
		departmentData.setBusinessUnit("【合计】");
		//撤销率
		if(nums[4] > 0){
			departmentData.setCancelRate(MathUtil.div(nums[3], nums[4], 3));
		}
		departmentData.setQcNum(nums[0]);
		departmentData.setQcIngNum(nums[1]);
		departmentData.setQcDoneNum(nums[2]);
		departmentData.setCancelNum(nums[3]);
		departmentData.setDoneTotalNum(nums[4]);
		departmentData.setComeFrom1(nums[5]);
		departmentData.setComeFrom2(nums[6]);
		departmentData.setComeFrom3(nums[7]);
		departmentData.setComeFrom4(nums[8]);
		departmentData.setComeFrom5(nums[9]);
		departmentData.setComeFrom6(nums[10]);
		departmentData.setComeFrom7(nums[11]);
		departmentData.setComeFrom8(nums[12]);
		departmentData.setComeFrom9(nums[13]);
		departmentData.setComeFrom10(nums[14]);
		departmentData.setComeFrom11(nums[15]);
		departmentData.setComeFrom12(nums[16]);
		
		return departmentData;
	}
	
}
