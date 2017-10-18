package com.tuniu.gt.complaint.schedule.task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.tuniu.gt.satisfaction.entity.FrmUserDelEntity;
import com.tuniu.gt.satisfaction.service.IFrmUserDelService;
import com.tuniu.gt.uc.entity.UcDepartmentDelEntity;
import com.tuniu.gt.uc.service.user.IUcDepartmentDelService;
/**
 * 售前客服满意度定时任务
 * @author yuxiang
 */
public class PresatisfyForConstrutsSendTask {
	private static Logger logger = Logger.getLogger(PresatisfyForConstrutsSendTask.class);
	// 引入备份组织架构的service
	@Autowired
	@Qualifier("uc_service_user_impl-ucdepartment")
	private IUcDepartmentDelService ucDepartmentDelService;
	
	@Autowired
	@Qualifier("uc_service_user_impl-ucuserdel")
	private IFrmUserDelService frmUserDelService;
	public void copyConstrutsForNextM() {//月末凌晨12点之前执行
		System.out.println("copy constucts for next mounth start:-------"+new Date());
		//初始化：备份本月末的组织架构以及人员
		List<UcDepartmentDelEntity> ucDepartmentDelList= (List<UcDepartmentDelEntity>)ucDepartmentDelService.fetchList();
		for(int i = 0; i < ucDepartmentDelList.size(); i++){
			int dep_id = ucDepartmentDelList.get(i).getCurrentId();
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("id", dep_id);
			map.put("depId", dep_id);
			UcDepartmentDelEntity existUcDepAno = (UcDepartmentDelEntity)ucDepartmentDelService.get(map);
			List<FrmUserDelEntity> existFrmUserList = (List<FrmUserDelEntity>)frmUserDelService.fetchList(map);
			if(existUcDepAno==null){
				ucDepartmentDelService.copyConstructsForNextMonth(ucDepartmentDelList.get(i));//copy组织架构
			}
			if(existFrmUserList.size()==0){
				frmUserDelService.copyPersonForNextMonth(map);//copy来自frm_user的人员
			}
		}
		System.out.println("copy constucts for next mounth over::-------"+new Date());
	}
}
