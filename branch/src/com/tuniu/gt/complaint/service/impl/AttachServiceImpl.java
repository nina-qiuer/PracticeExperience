package com.tuniu.gt.complaint.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.complaint.dao.impl.AttachDao;
import com.tuniu.gt.complaint.entity.AttachEntity;
import com.tuniu.gt.complaint.restful.ComplaintRestClient;
import com.tuniu.gt.complaint.service.IAttachService;
@Service("complaint_service_impl-attach")
public class AttachServiceImpl extends ServiceBaseImpl<AttachDao> implements IAttachService {
	@Autowired
	@Qualifier("complaint_dao_impl-attach")
	public void setDao(AttachDao dao) {
		this.dao = dao;
	}
	
//	private String savePath;
//	private String title;
//	private File pic;// 文件名 与视图层的名称一致
//	private String picContentType;// 文件名+ContentType
//	private String picFileName;// 文件名+FileName
//
//	public String getSavePath() {
//		return ServletActionContext.getServletContext().getRealPath(savePath);
//	}


	@Override
	public  int uploadFile(Map<String, Object> fileMap) {
		File file = (File) fileMap.get("pic");
		String fileName = (String) fileMap.get("fileName");
		Integer complaintId = Integer.parseInt(fileMap.get("complaintId")==null?"0":fileMap.get("complaintId")+"") ;
		String addPerson = (String) fileMap.get("addPerson");
		Integer connectionId = (Integer)fileMap.get("connectionId");
		String tableName = (String)fileMap.get("tableName");
		
		String url = ComplaintRestClient.uploadFile(file, fileName);
		//上传失败处理结果
		if("FAIL".equals(url)){
			return -1;
		}
        String type=fileName.substring(fileName.lastIndexOf("."));
        //保存附件
        AttachEntity attach=new AttachEntity();
        attach.setDelFlag(1);
        if(tableName!=null){
        	attach.setTableName(tableName);
        }
        if(connectionId!=null){
        	attach.setConnectionId(connectionId);
        }
        attach.setName(fileName);
        attach.setPath(url);
        attach.setType(type);
        attach.setComplaintId(complaintId);
        attach.setAddPerson(addPerson);
        return dao.insert(attach);
	}

	@Override
	public  int uploadQcFile(Map<String, Object> fileMap) {
		File file = (File) fileMap.get("pic");
		String fileName = (String) fileMap.get("fileName");
		Integer complaintId = Integer.parseInt(fileMap.get("complaintId")==null?"0":fileMap.get("complaintId")+"") ;
		String addPerson = (String) fileMap.get("addPerson");
		Integer connectionId = (Integer)fileMap.get("connectionId");
		String tableName = (String)fileMap.get("tableName");
		
		String url = ComplaintRestClient.uploadFile(file, fileName);
		//上传失败处理结果
		if("FAIL".equals(url) || ("").equals(url)){
			return -1;
		}
        String type=fileName.substring(fileName.lastIndexOf("."));
        //保存附件
        AttachEntity attach=new AttachEntity();
        attach.setDelFlag(1);
        if(tableName!=null){
        	attach.setTableName(tableName);
        }
        if(connectionId!=null){
        	attach.setConnectionId(connectionId);
        }
        attach.setName(fileName);
        attach.setPath(url);
        attach.setType(type);
        attach.setComplaintId(complaintId);
        attach.setAddPerson(addPerson);
        return dao.insertQcAttach(attach);
	}
	@Override
	public AttachEntity getAttach(String tableName, String connectionId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("tableName", tableName);
		paramMap.put("connectionId", connectionId);
		
		return dao.fetchOne(paramMap);
	}

	@SuppressWarnings("unchecked")
	public List<AttachEntity> getAttachList(String tableName,
			Integer connectionId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("tableName", tableName);
		paramMap.put("connectionId", connectionId);
		
		return (List<AttachEntity>) dao.fetchList(paramMap);
	}
	
	public List<AttachEntity> getAttachList(Integer complaintId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("complaintId", complaintId);
		return (List<AttachEntity>) dao.fetchList(paramMap);
	}
	
	public List<AttachEntity> getQcAttachList(Map<String, Object> paramMap) {
		return (List<AttachEntity>) dao.fetchQcList(paramMap);
	}

	@Override
	public AttachEntity getQcAttach(Map<String, Object> fileMap) {

		return dao.getQcAttach(fileMap);
	}

	@Override
	public void updateQcAttach(AttachEntity entity) {

		dao.updateQcAttach(entity);
	}

	@Override
	public void deleteImproveAttach(Map<String, Object> paramMap) {
		dao.deleteImproveAttach(paramMap);
	}

	@Override
	public int insertAttach(AttachEntity entity) {
		return dao.insert(entity);
	}
}
