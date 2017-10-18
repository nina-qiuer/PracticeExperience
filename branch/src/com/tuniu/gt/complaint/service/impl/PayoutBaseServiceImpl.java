package com.tuniu.gt.complaint.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tuniu.gt.complaint.dao.impl.PayoutBaseDao;
import com.tuniu.gt.complaint.entity.PayoutBaseEntity;
import com.tuniu.gt.complaint.service.IPayoutBaseService;
import com.tuniu.gt.uc.datastructure.TreeNode;

import tuniu.frm.core.ServiceBaseImpl;

@Service("complaint_service_complaint_impl-payout_base")
public class PayoutBaseServiceImpl extends ServiceBaseImpl<PayoutBaseDao> implements IPayoutBaseService {
	@Autowired
	@Qualifier("complaint_dao_impl-payout_base")
	public void setDao(PayoutBaseDao dao) {
		this.dao = dao;
	};

	@Override
	public List<TreeNode<PayoutBaseEntity>> getPayoutBaseTree(Integer father_id) {
//		List<TreeNode<PayoutBaseEntity>> payoutBaseTree = (List<TreeNode<PayoutBaseEntity>>) MemcachesUtil
//				.getObj("PAYOUTBASETREE");
//		if (payoutBaseTree == null) {
//			payoutBaseTree = buildPayoutBaseTree();
//			MemcachesUtil.setInToday("PAYOUTBASETREE", payoutBaseTree);
//		}
		List<TreeNode<PayoutBaseEntity>> payoutBaseTree = buildPayoutBaseTree(father_id);
		return payoutBaseTree;
	}

	private List<TreeNode<PayoutBaseEntity>> buildPayoutBaseTree(Integer father_id) {
		List<PayoutBaseEntity> payoutBaseList = (List<PayoutBaseEntity>) fetchList();
		return getPayoutChild(payoutBaseList, father_id);
	}

	private List<TreeNode<PayoutBaseEntity>> getPayoutChild(List<PayoutBaseEntity> payoutBaseList, Integer fatherId) {
		List<TreeNode<PayoutBaseEntity>> result = new ArrayList<TreeNode<PayoutBaseEntity>>();
		for (PayoutBaseEntity payoutBase : payoutBaseList) {
			if (fatherId != null && fatherId.equals(payoutBase.getFatherId())) {
				TreeNode<PayoutBaseEntity> tempTreeNode = new TreeNode<PayoutBaseEntity>(null);
				tempTreeNode.setData(payoutBase);
				tempTreeNode.setChilds(getPayoutChild(payoutBaseList, payoutBase.getId()));
				result.add(tempTreeNode);
			}
		}
		return result;
	}
}
