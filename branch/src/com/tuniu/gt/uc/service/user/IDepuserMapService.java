package com.tuniu.gt.uc.service.user;

import java.util.List;

import com.tuniu.gt.uc.entity.DepuserMapEntity;

import tuniu.frm.core.IServiceBase;
public interface IDepuserMapService extends IServiceBase {
	public void deleteByUserId(int userId);
	/**
	 * 根据用户编号取兼职的部门编号
	 * @param userId
	 * @return List<Integer>
	 */
	public List<DepuserMapEntity> getDepIdByUser(int userId);
}
