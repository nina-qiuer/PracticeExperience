package com.tuniu.gt.complaint.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.complaint.dao.impl.ComplaintLaunchCountDao;
import com.tuniu.gt.complaint.entity.ComplaintLaunchCountEntity;
import com.tuniu.gt.complaint.entity.ComplaintLaunchEntity;
import com.tuniu.gt.complaint.service.IComplaintLaunchCountService;
import com.tuniu.gt.uc.datastructure.TreeNode;

@Service("complaint_service_complaint_impl-complaint_launch_count")
public class ComplaintLaunchCountService extends
		ServiceBaseImpl<ComplaintLaunchCountDao> implements
		IComplaintLaunchCountService {
	private static Logger logger = Logger
			.getLogger(ComplaintLaunchCountService.class);

	@Autowired
	@Qualifier("complaint_dao_impl-complaint_launch_count")
	public void setDao(ComplaintLaunchCountDao dao) {
		this.dao = dao;
	}

	@Override
	public List<TreeNode<ComplaintLaunchCountEntity>> getComplaintLaunchCountList(
			Map<String, Object> paramMap) {
		BigDecimal countNumAll = BigDecimal.valueOf(dao
				.getComplaintLaunchCountNum(paramMap));
		List<ComplaintLaunchCountEntity> complaintLaunchCountLists = dao
				.getComplaintLaunchCountList(paramMap);
		List<TreeNode<ComplaintLaunchCountEntity>> treeNodeLists = new ArrayList<TreeNode<ComplaintLaunchCountEntity>>();
		for (int i = 0; i < complaintLaunchCountLists.size(); i++) {
			ComplaintLaunchCountEntity tempComplaintLaunch = complaintLaunchCountLists
					.get(i);
			tempComplaintLaunch.setCountPercent(BigDecimal
					.valueOf(tempComplaintLaunch.getCountNum())
					.multiply(BigDecimal.valueOf(100))
					.divide(countNumAll, 2, BigDecimal.ROUND_HALF_EVEN)
					+ "%");
			if (tempComplaintLaunch.getFather_dep().equals("")
					&& !tempComplaintLaunch.getDep_name().equals("")) {
				TreeNode<ComplaintLaunchCountEntity> tempTreeNode = new TreeNode<ComplaintLaunchCountEntity>(
						null);
				tempTreeNode.setData(tempComplaintLaunch);
				List<TreeNode<ComplaintLaunchCountEntity>> tempTreeNodeLists = new ArrayList<TreeNode<ComplaintLaunchCountEntity>>();
				tempTreeNode.setChilds(setChildByFatherDep(
						complaintLaunchCountLists,
						tempComplaintLaunch.getDep_name(), ""));
				treeNodeLists.add(tempTreeNode);
			}
		}
		return treeNodeLists;
	}

	// 递归构建层级关系
	public List<TreeNode<ComplaintLaunchCountEntity>> setChildByFatherDep(
			List<ComplaintLaunchCountEntity> complaintLaunchCountLists,
			String father_dep, String grandpa_dep) {
		List<TreeNode<ComplaintLaunchCountEntity>> treeNodeLists = new ArrayList<TreeNode<ComplaintLaunchCountEntity>>();
		for (int i = 0; i < complaintLaunchCountLists.size(); i++) {
			ComplaintLaunchCountEntity tempComplaintLaunch = complaintLaunchCountLists
					.get(i);
			if (tempComplaintLaunch.getFather_dep().equals(father_dep)
					&& !tempComplaintLaunch.getDep_name().equals("")
					&& (grandpa_dep.equals("") || grandpa_dep
							.equals(tempComplaintLaunch.getGrandpa_dep()))) {
				TreeNode<ComplaintLaunchCountEntity> tempTreeNode = new TreeNode<ComplaintLaunchCountEntity>(
						null);
				tempTreeNode.setData(tempComplaintLaunch);
				tempTreeNode.setChilds(setChildByFatherDep(
						complaintLaunchCountLists,
						tempComplaintLaunch.getDep_name(),
						tempComplaintLaunch.getFather_dep()));
				treeNodeLists.add(tempTreeNode);
			}
		}
		return treeNodeLists;
	}

	@Override
	public void insertComplaintLaunchByDay(Map<String, Object> paramMap) {
		dao.insertComplaintLaunchByDay(paramMap);
	}

	@Override
	public void updateDepartmentToReportOne() {
		dao.updateDepartmentToReportOne();
	}

	@Override
	public void updateDepartmentToReportTwo() {
		dao.updateDepartmentToReportTwo();
	}

	@Override
	public void updateDepartmentToReportThree() {
		dao.updateDepartmentToReportThree();
	}
	
	@Override
	public List<ComplaintLaunchEntity> getComplaintLaunchList(Map<String, Object> paraMap){
		return dao.getComplaintLaunchList(paraMap);
	}
	
	@Override
	public Integer getComplaintLaunchListCount(Map<String, Object> paraMap){
		return dao.getComplaintLaunchListCount(paraMap);
	}
}
