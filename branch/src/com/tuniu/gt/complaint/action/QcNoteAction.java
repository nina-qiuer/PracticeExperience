package com.tuniu.gt.complaint.action;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;
import tuniu.frm.service.Constant;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import com.tuniu.gt.complaint.entity.AttachEntity;
import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.entity.QcNoteEntity;
import com.tuniu.gt.complaint.service.IAttachService;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.complaint.service.IQcNoteService;

@Service("complaint_action-qc_note")
@Scope("prototype")
public class QcNoteAction extends FrmBaseAction<IQcNoteService, QcNoteEntity>
{
	private static final long serialVersionUID = 1L;

	@Autowired
	@Qualifier("complaint_service_impl-qc_note")
	public void setService(IQcNoteService service)
	{
		this.service = service;
	};
	
	// 上传文件
	@Autowired
	@Qualifier("complaint_service_impl-attach")
	private IAttachService attachService;

	private List<QcNoteEntity> qcNoteList = new ArrayList<QcNoteEntity>();
	
	private List<File> noteFile;// 质检备忘附件
	private List<String> noteFileFileName;// 文件名+FileName

	private static Logger logger = Logger.getLogger(QcNoteAction.class);

	public QcNoteAction()
	{
		setManageUrl("qcNote");
	}


	public String execute()
	{
		String qcId = (String)request.getParameter("qcId");
		request.setAttribute("qcId", qcId);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("qcId", qcId);
		super.execute(paramMap);
		
		request.setAttribute("user", user);
		qcNoteList = (List<QcNoteEntity>)request.getAttribute("dataList");
		this.queryAttachList();
		
		return "qc_note";
	}

	/**
	 * 删除质检备忘
	 * @return
	 */
	public String delete()
	{
		String id = request.getParameter("id");
		entity.setId(Integer.valueOf(id));
		entity.setDelFlag(1);
		service.update(entity);
		return this.execute();
	}

	
	/**
	 * 更新质检备忘
	 * @return
	 */
	public String update()
	{
		String id = request.getParameter("id");
		entity = (QcNoteEntity)service.get(id);
		String remark = request.getParameter("remark");
		entity.setRemark(remark);
		service.update(entity);
		return "qc_note";

	}

	/**
	 * 插入质检备忘
	 * @return
	 */
	public String insert()
	{
		String remark = request.getParameter("remark");
		String qcId = (String)request.getParameter("qcId");
		entity.setQcId(Integer.valueOf(qcId));
		entity.setRemark(remark);
		entity.setAddPerson(user.getRealName());
		service.insert(entity);

		return this.execute();
	}

	/**
	 * 查询质检备忘附件列表
	 * @return
	 */
	public String queryAttachList()
	{
		String qcId = (String)request.getParameter("qcId");
		request.setAttribute("qcId", qcId);
		List<AttachEntity> attachList = attachService.getAttachList("ct_qc",Integer.valueOf(qcId));
		request.setAttribute("attachList", attachList);
		request.setAttribute("user", user);
		return "qc_note_attach";
	}

	/**
	 * 上传质检备忘附件
	 * @return
	 * @throws FileUploadException
	 * @throws UnsupportedEncodingException
	 */
	public String upload() throws FileUploadException, UnsupportedEncodingException
	{
		String savePath = Constant.CONFIG.getProperty("agencyUploadFilePath");
		Map<String, Object> fileMap = new HashMap<String, Object>();
		String qcId = (String)request.getParameter("qcId");
		for(int i = 0; i < noteFileFileName.size(); i++)
		{
			String fileName = noteFileFileName.get(i);
			fileMap.put("tableName", "ct_qc");
			fileMap.put("connectionId", Integer.valueOf(qcId));
			fileMap.put("savePath", savePath);
			fileMap.put("pic", noteFile.get(i));
			fileMap.put("fileName", fileName);
			fileMap.put("addPerson", user.getRealName());
			
			attachService.uploadFile(fileMap);
		}
		return this.queryAttachList();
	}

	/**
	 * 删除质检备忘附件
	 * 
	 * @author jiangye 2015-05-16
	 * @version 1.0 Copyright by Tuniu.com
	 */
	public String delNoteFile()
	{
		int attachId = Integer.parseInt(request.getParameter("attachId"));
		Map<String, Object> fileMap = new HashMap<String, Object>();
		fileMap.put("id", attachId);
		AttachEntity toolFile = (AttachEntity)attachService.fetchOne(fileMap);
		toolFile.setDelFlag(0);
		toolFile.setUpdateTime(new Date());
		attachService.update(toolFile);
		
		String qcId = (String)request.getParameter("qcId");
		List<AttachEntity> attachList = attachService.getAttachList("ct_qc",Integer.valueOf(qcId));
		request.setAttribute("attachList", attachList);
		String pageFrom = request.getParameter("pageFrom");
		if("notePage".equals(pageFrom)){
			return this.execute();
		}else{
			return this.queryAttachList();
		}
	}

	public List<QcNoteEntity> getQcNoteList()
	{
		return qcNoteList;
	}

	public void setQcNoteList(List<QcNoteEntity> qcNoteList)
	{
		this.qcNoteList = qcNoteList;
	}


	public List<File> getNoteFile()
    {
    	return noteFile;
    }


	public void setNoteFile(List<File> noteFile)
    {
    	this.noteFile = noteFile;
    }


	public List<String> getNoteFileFileName()
    {
    	return noteFileFileName;
    }


	public void setNoteFileFileName(List<String> noteFileFileName)
    {
    	this.noteFileFileName = noteFileFileName;
    }
	

}
