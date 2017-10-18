package com.tuniu.gt.complaint.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import com.tuniu.gt.complaint.entity.AttachEntity;
import com.tuniu.gt.complaint.entity.SupportShareEntity;
import com.tuniu.gt.complaint.service.IAttachService;
import com.tuniu.gt.complaint.service.ISupportShareService;
import com.tuniu.gt.complaint.vo.SupportShareVo;

/**
 * 
 * @author weiweiqin create by 2010/4/13
 */

@SuppressWarnings("serial")
@Service("complaint_action-support_share")
@Scope("prototype")
public class SupportShareAction extends
		FrmBaseAction<ISupportShareService, SupportShareEntity> {
	/**
	 * 通过投诉id查看供应商承担列表页面
	 */
	private static final String SUPPORT_SHARE_LIST = "support_share_list";

	/**
	 * 供应商附件对应的表名
	 */
	private static final String CT_SHARE_SOLUTION_TABNAME = "ct_share_solution";
	/**
	 * 注入附件业务类
	 */
	@Autowired
	@Qualifier("complaint_service_impl-attach")
	private IAttachService attachService;

	/**
	 * 用于页面展示供应商承担列表信息(包含附件信息)
	 */
	private List<SupportShareVo> supportShareVoList;

	/**
	 * 接受页面的投诉id
	 */
	private String complaintId;

	/**
	 * 通过投诉id查看供应商列表，页面输入参数 complaintId
	 * 
	 * @return
	 * @throws MySQLSyntaxErrorException
	 */
	public String getSupportSharesByCompId() throws MySQLSyntaxErrorException {
		this.supportShareVoList = new ArrayList<SupportShareVo>();
		SupportShareVo supportShareVo = null;
		// 通过投诉id查询供应商列表
		List<SupportShareEntity> supportShareList = service
				.getSupportListByComplaintId(this.complaintId);
		if (null != supportShareList) {
			for (SupportShareEntity supportShareEntity : supportShareList) {
				supportShareVo = new SupportShareVo();
				supportShareVo.setSupprotShareEntity(supportShareEntity);
				// 查询该供应商下的附件信息
				AttachEntity attachEntity = attachService.getAttach(
						CT_SHARE_SOLUTION_TABNAME,
						String.valueOf(supportShareEntity.getId()));
				supportShareVo.setAttachEntity(attachEntity);
				this.supportShareVoList.add(supportShareVo);
			}
		}
		return SUPPORT_SHARE_LIST;
	}

	@Autowired
	@Qualifier("complaint_service_complaint_impl-support_share")
	public void setService(ISupportShareService service) {
		this.service = service;
	}

	public List<SupportShareVo> getSupportShareVoList() {
		return supportShareVoList;
	}

	public void setSupportShareVoList(List<SupportShareVo> supportShareVoList) {
		this.supportShareVoList = supportShareVoList;
	}

	public String getComplaintId() {
		return complaintId;
	}

	public void setComplaintId(String complaintId) {
		this.complaintId = complaintId;
	}

}
