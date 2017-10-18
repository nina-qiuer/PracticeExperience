/**
 * 
 */
package com.tuniu.gt.tac.action;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;
import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.entity.AttachEntity;
import com.tuniu.gt.complaint.service.IAttachService;
import com.tuniu.gt.frm.action.DBConfigManager;
import com.tuniu.gt.frm.service.system.IUserService;
import com.tuniu.gt.tac.entity.CommissionSummary;
import com.tuniu.gt.tac.service.ICommissionSummaryService;
import com.tuniu.gt.tac.vo.CommissionSummaryVo;
import com.tuniu.gt.toolkit.CellDataMapperHandler;
import com.tuniu.gt.toolkit.DateUtil;
import com.tuniu.gt.toolkit.POIUtil;
import com.tuniu.gt.toolkit.lang.CollectionUtil;
import com.tuniu.gt.warning.common.Page;

/**
 * @author jiangye
 *
 */
@Service("tac_action-commissionSummary")
@Scope("prototype")
public class CommissionSummaryAction  extends FrmBaseAction<ICommissionSummaryService, CommissionSummary> {
	
	private Logger logger = Logger.getLogger(getClass());
	
	private CommissionSummaryVo searchVo; //列表搜索条件
	private Page page;
	private Map<String,Object>  info;
	
	private List<File> attachFile;// 附件
    private List<String> attachFileFileName;// 文件名+FileName
    
    private List<String> categorys;
	
	@Autowired
	@Qualifier("frm_service_system_impl-user")
	private IUserService userService;
	
    @Autowired
    @Qualifier("complaint_service_impl-attach")
    private IAttachService attachService;
	
	public CommissionSummaryAction() {
		setManageUrl("tac");
		info = new HashMap<String, Object>(1);
	}
	
	@Autowired
	@Qualifier("tac_service_impl-commissionSummary")
	public void setService(ICommissionSummaryService service) {
		this.service = service;
	}
	
	/* 
	 * 查询列表与发起工单页
	 */
	public String execute(){
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(searchVo != null){
			paramMap = searchVo.toMap();
		}
		
		if(page == null){
			page = new Page();
			page.setPageSize(20);
		}
		
		paramMap.put("dataLimitStart", (page.getCurrentPage()-1)*page.getPageSize());
		paramMap.put("dataLimitEnd", page.getPageSize());
		
		List<CommissionSummary>  dataList = (List<CommissionSummary>) service.fetchList(paramMap);
		page.setCount(service.count(paramMap));
		page.setCurrentPageCount(dataList.size());
		request.setAttribute("dataList", dataList);
		
		return "commission_summary_list";
	}
	
	public String toAddOrUpdate(){
		if(id!=null){
			entity = (CommissionSummary) service.get(id);
			request.setAttribute("entity", entity);
		}
		
		return "commission_summary_form";
	}
	
	
	public String add(){
	    entity.setCustomer(user.getRealName());
	    service.insert(entity);
		return "success";
	}

    public String update(){
        entity.setId(id);
		service.update(entity);
		return "success";
	}
	
	
	public String checkExportsCount(){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (null == searchVo) {
            searchVo = new CommissionSummaryVo();
        }
        paramMap.putAll(searchVo.toMap());
        Integer count  = service.count(paramMap);
        
        if(count ==0 ) {
            info.put("success", false);
            info.put("msg", "没有数据");
        }else if(count > 10000){
            info.put("success", false);
            info.put("msg", "导出数据过大，请增加筛选条件后再执行");
        }else {
            info.put("success", true);
        }
        
        return "info";
    }
	
	public String exports() {
        
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap = searchVo.toMap();
        
        List<CommissionSummary>  dataList = (List<CommissionSummary>) service.fetchList(paramMap);
        if(CollectionUtil.isNotEmpty(dataList)) {
            try {
                String[] headers = new String[]{"编号", "添加日期", "类别","客服专员", "备注"};
                String[] exportsFields = new String[]{"id", "addTime", "category","customer", "remark"};

                new POIUtil<CommissionSummary>().exportExcel(Arrays.asList(headers), Arrays.asList(exportsFields), dataList,
                        new CellDataMapperHandler() {
                            @Override
                            public String mapToCell(String fieldName, Object value) {
                                String textValue = "";
                                if(value instanceof Date) {
                                    Date date = (Date) value;
                                    textValue = DateUtil.formatDate(date);
                                    return textValue;
                                }
                                
                                textValue=value==null?"":value+"";
                                
                                return textValue;
                            }
                        });
            } catch(Exception e) {
            	logger.error(e.getMessage(), e);
            }

        }
        
        return null;
	}
	
	public String toUploadList() {
        List<AttachEntity> attachList = attachService.getAttachList("tac_commission_summary",id);
        request.setAttribute("attachList", attachList);
        request.setAttribute("user", user);
	    return "commission_summary_attach";
	}
	
	public String upload() {
	    String savePath = Constant.CONFIG.getProperty("agencyUploadFilePath");
        Map<String, Object> fileMap = new HashMap<String, Object>();
        if(CollectionUtil.isNotEmpty(attachFile)){
            for(int i = 0; i < attachFileFileName.size(); i++)
            {
                String fileName = attachFileFileName.get(i);
                fileMap.put("tableName", "tac_commission_summary");
                fileMap.put("connectionId", id);
                fileMap.put("savePath", savePath);
                fileMap.put("pic", attachFile.get(i));
                fileMap.put("fileName", fileName);
                fileMap.put("addPerson", user.getRealName());
                
                attachService.uploadFile(fileMap);
            }
        }
        return this.toUploadList();
	}
	
	public String delAttach(){
	    int attachId = Integer.parseInt(request.getParameter("attachId"));
        Map<String, Object> fileMap = new HashMap<String, Object>();
        fileMap.put("id", attachId);
        AttachEntity toolFile = (AttachEntity)attachService.fetchOne(fileMap);
        toolFile.setDelFlag(0);
        toolFile.setUpdateTime(new Date());
        attachService.update(toolFile);
        
        List<AttachEntity> attachList = attachService.getAttachList("tac_commission_summary",id);
        request.setAttribute("attachList", attachList);
        return this.toUploadList();
	}
	
	
    public CommissionSummaryVo getSearchVo() {
        return searchVo;
    }

    public void setSearchVo(CommissionSummaryVo searchVo) {
        this.searchVo = searchVo;
    }

    public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

    public Map<String, Object> getInfo() {
		return info;
	}

	public void setInfo(Map<String, Object> info) {
		this.info = info;
	}

    public List<File> getAttachFile() {
        return attachFile;
    }

    public void setAttachFile(List<File> attachFile) {
        this.attachFile = attachFile;
    }

    public List<String> getAttachFileFileName() {
        return attachFileFileName;
    }

    public void setAttachFileFileName(List<String> attachFileFileName) {
        this.attachFileFileName = attachFileFileName;
    }

    public List<String> getCategorys() {
        return DBConfigManager.getConfigAsList("business.tac.commision.category");
    }

    public void setCategorys(List<String> categorys) {
        this.categorys = categorys;
    }

}
