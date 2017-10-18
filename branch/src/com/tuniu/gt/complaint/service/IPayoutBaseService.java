package com.tuniu.gt.complaint.service;

import java.util.List;

import com.tuniu.gt.complaint.entity.PayoutBaseEntity;
import com.tuniu.gt.uc.datastructure.TreeNode;

import tuniu.frm.core.IServiceBase;
public interface IPayoutBaseService extends IServiceBase {

	/**
	 * 获取
	 * @return
	 */
	List<TreeNode<PayoutBaseEntity>> getPayoutBaseTree(Integer father_id);

}
