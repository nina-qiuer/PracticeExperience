package com.tuniu.gt.complaint.service;

import java.util.List;
import java.util.Map;

import com.tuniu.gt.complaint.entity.AttachEntity;

import tuniu.frm.core.IServiceBase;
/**
 * 文件上传service接口
 * @author zhonghaiyang
 *
 */
public interface IAttachService extends IServiceBase {
	
	/**
	 * 上传文件
	 * @param savePath //存储路径 类似 upload/test 
	 * @param file //文件实体一般是复制到tomcat temp的文件实体
	 * @param fileName //上传文件名
	 * @param tableName //附件表找欧诺个关联表的表名
	 * @param connectId //关联对象的id
	 * @return
	 */
	public int uploadFile(Map<String, Object> fileMap);
	
	
	public int uploadQcFile(Map<String, Object> fileMap);
	
	/**
	 * 根据关联表名和关联id娶附件对象
	 * @param tableName 关联表名
	 * @param connectionId 关联id
	 * @return
	 */
	public AttachEntity getAttach(String tableName, String connectionId);
	
	/**
	 * 根据关联表名和关联id娶附件对象
	 * @param tableName 关联表名
	 * @param integer 关联id
	 * @return List<AttachEntity>
	 */
	public List<AttachEntity> getAttachList(String tableName, Integer connectionId);
	
	/**
	 * 根据投诉单id取附件对象
	 * @param integer 投诉单id
	 * @return List<AttachEntity>
	 */
	public List<AttachEntity> getAttachList(Integer complaintId);
	
	/**
	 * 根据投诉单id取附件对象
	 * @param paramMap  投诉单id  table_name
	 * @return List<AttachEntity>
	 */
	public List<AttachEntity> getQcAttachList(Map<String, Object> paramMap);
	
	public AttachEntity  getQcAttach(Map<String, Object> fileMap);
	
	public void updateQcAttach(AttachEntity entity);

	/**
	 * 删除改进报告添加的附件
	 * @param paramMap
	 */
	public void deleteImproveAttach(Map<String, Object> paramMap);
	
	public int insertAttach(AttachEntity entity);
}
