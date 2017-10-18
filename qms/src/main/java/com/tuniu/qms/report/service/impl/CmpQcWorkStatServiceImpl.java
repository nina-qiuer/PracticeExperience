package com.tuniu.qms.report.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.common.util.MathUtil;
import com.tuniu.qms.qc.util.QcConstant;
import com.tuniu.qms.report.dao.CmpQcWorkStatMapper;
import com.tuniu.qms.report.dto.CmpQcWorkStatDto;
import com.tuniu.qms.report.dto.CmpQcWorkStatReportDto;
import com.tuniu.qms.report.model.CmpQcWorkProportion;
import com.tuniu.qms.report.model.CmpQcWorkStat;
import com.tuniu.qms.report.service.CmpQcWorkStatService;

@Service
public class CmpQcWorkStatServiceImpl implements CmpQcWorkStatService {
	
	@Autowired
	private CmpQcWorkStatMapper mapper;

	@Override
	public void add(CmpQcWorkStat obj) {}

	@Override
	public void delete(Integer id) {}

	@Override
	public void update(CmpQcWorkStat obj) {}

	@Override
	public CmpQcWorkStat get(Integer id) {
		return null;
	}

	@Override
	public void loadPage(CmpQcWorkStatDto dto) {}
	
	@Override
	public List<CmpQcWorkStat> list(CmpQcWorkStatDto dto) {
		return null;
	}
	
	@Override
	public Map<String, Object> getCmpQcWorkStatReport(
			CmpQcWorkStatDto dto) {
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<CmpQcWorkStatReportDto> dataList = new ArrayList<CmpQcWorkStatReportDto>();
		CmpQcWorkStat departmentTotal = new CmpQcWorkStat();
		CmpQcWorkProportion departmentWorPro = new CmpQcWorkProportion();
		//获得现在分单配置表中所有人员数据
		List<CmpQcWorkStatReportDto> personDataList = mapper.getPersonDataList(dto);
		//过滤掉数据全部为0的人员，防止人均时出错
		passPerson(personDataList);
		
		if(personDataList.size() > 0){
			//获得分组
			List<String> groupList = mapper.getGroupList();
			
			//组数据汇总
			List<CmpQcWorkStat> groupTotalList = groupTotal(personDataList, groupList);
			
			//全部门汇总
			departmentTotal = departmentTotal(groupTotalList);
			
			//对数据进行其他列集成
			for(CmpQcWorkStat groupData : groupTotalList){
				compute(groupData);
			}
			for(CmpQcWorkStatReportDto data : personDataList){
				compute(data.getData());
			}
			compute(departmentTotal);
			
			//数据结构组合，组--子LIst
			for(CmpQcWorkStat groupData : groupTotalList){
				CmpQcWorkStatReportDto  cmpQcWorkStatReportDto = new CmpQcWorkStatReportDto();
				List<CmpQcWorkStatReportDto> childrenList = new ArrayList<CmpQcWorkStatReportDto>();
				cmpQcWorkStatReportDto.setData(groupData);
				cmpQcWorkStatReportDto.setName(groupData.getDepartmentName());
				//子节点添加
				for(CmpQcWorkStatReportDto person : personDataList){
					if(person.getData().getDepartmentName().equals(groupData.getDepartmentName())){
						childrenList.add(person);
					}
				}
				cmpQcWorkStatReportDto.setChildren(childrenList);
				//组人均数据计算
				CmpQcWorkProportion workPro = getWorkPro(cmpQcWorkStatReportDto);
				cmpQcWorkStatReportDto.setCmpQcWorkProportion(workPro);
				
				dataList.add(cmpQcWorkStatReportDto);
			}
			
			//部门人均
			CmpQcWorkStatReportDto departmentAverage = new CmpQcWorkStatReportDto();
			
			departmentAverage.setData(departmentTotal);
			departmentAverage.setChildren(personDataList);
			
			departmentWorPro = getWorkPro(departmentAverage);
			departmentWorPro.setQcPerson("【部门人均】");
			
		}else{
			departmentTotal = null;
		}
		
		dataMap.put("dataList", dataList);
		dataMap.put("departmentTotal", departmentTotal);
		dataMap.put("departmentWorPro", departmentWorPro);
		
		return dataMap;
	}
	
	/**
	 * 过滤掉数据全部为0的人员，防止人均时出错
	 * @param personDataList
	 */
	private void passPerson(List<CmpQcWorkStatReportDto> personDataList) {
		String sb = "";
		//不等于0 直接进行下次循环
		for(int i = 0; i < personDataList.size(); i++){
			if(personDataList.get(i).data.getDistribNum() != 0){
				continue;
			}
			if(personDataList.get(i).data.getDoneTotalNum() != 0){
				continue;
			}
			if(personDataList.get(i).data.getCmpUnDoneQcDoneNum() != 0){
				continue;
			}
			if(personDataList.get(i).data.getCancelNum() != 0){
				continue;
			}
			if(personDataList.get(i).data.getWorkDayNum() != 0){
				continue;
			}
			if(personDataList.get(i).data.getTimelyDoneNum() != 0){
				continue;
			}
			if(personDataList.get(i).data.getExpireNum() != 0){
				continue;
			}
			if(personDataList.get(i).data.getQcPeriodBgnDoneNum() != 0){
				continue;
			}
			if(personDataList.get(i).data.getQcPeriodBgnNum() != 0){
				continue;
			}
			if(personDataList.get(i).data.getZxNum() != 0){
				continue;
			}
			if(personDataList.get(i).data.getLcxtNum() != 0){
				continue;
			}
			if(personDataList.get(i).data.getGysNum() != 0){
				continue;
			}
			if(personDataList.get(i).data.getDmyNum() != 0){
				continue;
			}
			sb = sb + i + ",";
		}
		
		if(sb.length() > 0){
			String[] indexs= sb.split(",");
			for(int j = 0; j < indexs.length; j++){
				int ind = Integer.parseInt(indexs[j]);
				personDataList.remove(ind - j);
			}
		}
	}

	/**
	 * 全部门汇总
	 * @param groupTotalList
	 * @return
	 */
	private CmpQcWorkStat departmentTotal(List<CmpQcWorkStat> groupTotalList) {
		
		int[] nums = new int[13];
		CmpQcWorkStat cmpQcWorkStat = new CmpQcWorkStat();
		
		for(CmpQcWorkStat group : groupTotalList){
			//分配单数
			nums[0] += group.getDistribNum();
			//完成单数
			nums[1] += group.getDoneTotalNum();
			//未完成单数
			nums[2] += group.getCmpUnDoneQcDoneNum();
			//取消单数
			nums[3] += group.getCancelNum();
			//执行问题个数
			nums[4] += group.getZxNum();
			//流程系统问题个数
			nums[5] += group.getLcxtNum();
			//供应商问题个数
			nums[6] += group.getGysNum();
			//低满意度问题个数
			nums[7] += group.getDmyNum();
			//上班天数
			nums[8] += group.getWorkDayNum();
			//及时完成单数
			nums[9] += group.getTimelyDoneNum();
			//到期单数
			nums[10] += group.getExpireNum();
			//质检期开始已完成单数
			nums[11] += group.getQcPeriodBgnDoneNum();
			//质检期开始总单数
			nums[12] += group.getQcPeriodBgnNum();
		}
		
		cmpQcWorkStat.setDepartmentName("【全部门】");
		cmpQcWorkStat.setDistribNum(nums[0]);
		cmpQcWorkStat.setDoneTotalNum(nums[1]);
		cmpQcWorkStat.setCmpUnDoneQcDoneNum(nums[2]);
		cmpQcWorkStat.setCancelNum(nums[3]);
		cmpQcWorkStat.setZxNum(nums[4]);
		cmpQcWorkStat.setLcxtNum(nums[5]);
		cmpQcWorkStat.setGysNum(nums[6]);
		cmpQcWorkStat.setDmyNum(nums[7]);
		cmpQcWorkStat.setWorkDayNum(nums[8]);
		cmpQcWorkStat.setTimelyDoneNum(nums[9]);
		cmpQcWorkStat.setExpireNum(nums[10]);
		cmpQcWorkStat.setQcPeriodBgnDoneNum(nums[11]);
		cmpQcWorkStat.setQcPeriodBgnNum(nums[12]);
		
		return cmpQcWorkStat;
	}

	/**
	 * 分组数据汇总
	 * @param personDataList
	 * @param groupList
	 * @return 
	 */
	private List<CmpQcWorkStat> groupTotal(List<CmpQcWorkStatReportDto> personDataList,List<String> groupList) {
		List<CmpQcWorkStat> groupTotalList = new ArrayList<CmpQcWorkStat>();
		
		for(String group : groupList){
			
			CmpQcWorkStat cmpQcWorkStat = new CmpQcWorkStat();
			int[] nums = new int[13];
			//该分组下是否有子节点
			Boolean flag = false;
			
			for(CmpQcWorkStatReportDto data : personDataList ){
				//分组的数据汇总
				if(group.equals( data.getData().getDepartmentName() ) ){
					//分配单数
					nums[0] += data.getData().getDistribNum();
					//完成单数
					nums[1] += data.getData().getDoneTotalNum();
					//未完成单数
					nums[2] += data.getData().getCmpUnDoneQcDoneNum();
					//取消单数
					nums[3] += data.getData().getCancelNum();
					//执行问题个数
					nums[4] += data.getData().getZxNum();
					//流程系统问题个数
					nums[5] += data.getData().getLcxtNum();
					//供应商问题个数
					nums[6] += data.getData().getGysNum();
					//低满意度问题个数
					nums[7] += data.getData().getDmyNum();
					//上班天数
					nums[8] += data.getData().getWorkDayNum();
					//及时完成单数
					nums[9] += data.getData().getTimelyDoneNum();
					//到期单数
					nums[10] += data.getData().getExpireNum();
					//质检期开始已完成单数
					nums[11] += data.getData().getQcPeriodBgnDoneNum();
					//质检期开始总单数
					nums[12] += data.getData().getQcPeriodBgnNum();
					
					flag = true;
				}
			}
			//有下级节点填充
			if(flag){
				cmpQcWorkStat.setDepartmentName(group);
				cmpQcWorkStat.setDistribNum(nums[0]);
				cmpQcWorkStat.setDoneTotalNum(nums[1]);
				cmpQcWorkStat.setCmpUnDoneQcDoneNum(nums[2]);
				cmpQcWorkStat.setCancelNum(nums[3]);
				cmpQcWorkStat.setZxNum(nums[4]);
				cmpQcWorkStat.setLcxtNum(nums[5]);
				cmpQcWorkStat.setGysNum(nums[6]);
				cmpQcWorkStat.setDmyNum(nums[7]);
				cmpQcWorkStat.setWorkDayNum(nums[8]);
				cmpQcWorkStat.setTimelyDoneNum(nums[9]);
				cmpQcWorkStat.setExpireNum(nums[10]);
				cmpQcWorkStat.setQcPeriodBgnDoneNum(nums[11]);
				cmpQcWorkStat.setQcPeriodBgnNum(nums[12]);
				
				groupTotalList.add(cmpQcWorkStat);
			}
			
		}
		
		return groupTotalList;
		
	}

	private void compute(CmpQcWorkStat data) {
		int problemTotalNum = data.getZxNum() + data.getLcxtNum() + data.getGysNum() + data.getDmyNum();
		data.setProblemTotalNum(problemTotalNum);
		
		int doneTotalNum = data.getDoneTotalNum();
		if (doneTotalNum > 0) {
			double cancelRate = MathUtil.div(data.getCancelNum(), doneTotalNum, 3);
			double problemRate = MathUtil.div(problemTotalNum, doneTotalNum, 3);
			data.setCancelRate(cancelRate);
			data.setProblemRate(problemRate);
		}
		
		int workDayNum = data.getWorkDayNum();
		if (workDayNum > 0) {
			double avgDailyDoneNum = MathUtil.div(doneTotalNum, workDayNum, 1);
			double avgDailyProblemNum = MathUtil.div(problemTotalNum, workDayNum, 1);
			data.setAvgDailyDoneNum(avgDailyDoneNum);
			data.setAvgDailyProblemNum(avgDailyProblemNum);
		}
		
		int expireNum = data.getExpireNum();
		if (expireNum > 0) {
			double timelyRate = MathUtil.div(data.getTimelyDoneNum(), expireNum, 3);
			data.setTimelyRate(timelyRate);
		}
		
		int qcPeriodBgnNum = data.getQcPeriodBgnNum();
		if (qcPeriodBgnNum > 0) {
			double doneRate = MathUtil.div(data.getQcPeriodBgnDoneNum(), qcPeriodBgnNum, 3);
			data.setDoneRate(doneRate);
		}
	}
	
	public CmpQcWorkProportion getWorkPro(CmpQcWorkStatReportDto cmpQcWorkStatReport){
		
		CmpQcWorkProportion wrok = new CmpQcWorkProportion();
		CmpQcWorkStat allWork = cmpQcWorkStatReport.getData();
		int size = cmpQcWorkStatReport.getChildren().size();
		wrok.setQcPerson(QcConstant.PROPORTION);
		wrok.setDistribNum(MathUtil.div(allWork.getDistribNum(), size,1));	//人均分配单数
		wrok.setDoneTotalNum(MathUtil.div(allWork.getDoneTotalNum(), size,1));//人均完成单数 
		wrok.setCmpUnDoneQcDoneNum(MathUtil.div(allWork.getCmpUnDoneQcDoneNum(), size,1));//人均投诉未完成质检完成单数 
		wrok.setCancelNum(MathUtil.div(allWork.getCancelNum(), size,1));//人均撤销单数
		wrok.setCancelRate(allWork.getCancelRate());//人均撤销率
		wrok.setProblemTotalNum(MathUtil.div(allWork.getProblemTotalNum(), size,1));  //人均产出问题点数
		wrok.setProblemRate(allWork.getProblemRate());//人均问题率
		
		int num =0;
		for(int i =0;i<size;i++){
			
			num += cmpQcWorkStatReport.getChildren().get(i).getData().getWorkDayNum();
			
		}
		wrok.setWorkDayNum(MathUtil.div(num, size,1));//人均上班天数
		if(num>0){
			
			wrok.setAvgDailyDoneNum(MathUtil.div(allWork.getDoneTotalNum(), num,1));	//人均 日均完成单数
			wrok.setAvgDailyProblemNum(MathUtil.div(allWork.getProblemTotalNum(),num,1));//人均 日均产出问题点数
		}
		wrok.setZxNum(MathUtil.div(allWork.getZxNum(), size,1)); //人均执行问题个数 
		wrok.setLcxtNum(MathUtil.div(allWork.getLcxtNum(), size,1));//人均流程系统问题个数 
		wrok.setGysNum(MathUtil.div(allWork.getGysNum(), size,1));//人均供应商问题个数 
		wrok.setDmyNum(MathUtil.div(allWork.getDmyNum(), size,1));//人均低满意度问题个数 
		wrok.setTimelyDoneNum(MathUtil.div(allWork.getTimelyDoneNum(), size,1));//人均 及时完成单数
		wrok.setExpireNum(MathUtil.div(allWork.getExpireNum(), size,1)); //人均 到期单数
		wrok.setTimelyRate(allWork.getTimelyRate());//人均及时率
		wrok.setQcPeriodBgnDoneNum(MathUtil.div(allWork.getQcPeriodBgnDoneNum(), size,1)); //人均质检期开始已完成单数
		wrok.setQcPeriodBgnNum(MathUtil.div(allWork.getQcPeriodBgnNum(), size,1)); //人均质检期开始总单数
		wrok.setDoneRate(allWork.getDoneRate());//人均及时率
		return wrok;
		
	}
}
