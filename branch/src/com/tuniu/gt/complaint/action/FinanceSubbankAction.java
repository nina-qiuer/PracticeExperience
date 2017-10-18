/**
 * 
 */
package com.tuniu.gt.complaint.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;

import com.tuniu.gt.complaint.entity.FinanceSubbankEntity;
import com.tuniu.gt.complaint.service.IComplaintTSPService;
import com.tuniu.gt.complaint.service.IFinanceSubbankService;
import com.tuniu.gt.complaint.vo.FinanceSubbankVo;

/**
 * 售后报表action
 * @author jiangye
 *
 */
@Service("complaint_action-finance_subbank")
@Scope("prototype")
public class FinanceSubbankAction  extends FrmBaseAction<IFinanceSubbankService, FinanceSubbankEntity>{
    
	private Logger logger = Logger.getLogger(getClass());
	
	private FinanceSubbankVo searchVo;
	
	private List<FinanceSubbankEntity> dataList = new ArrayList<FinanceSubbankEntity>();
	
	private String lastUpdateDate;
	
	@Autowired
    @Qualifier("tspService")
    private IComplaintTSPService tspService;
	
	@Autowired
    @Qualifier("complaint_service_complaint_impl-finance_subbank")
    public void setService(IFinanceSubbankService service) {
        this.service = service;
    };
    
    public String execute(){
        if(searchVo == null){
            searchVo = new FinanceSubbankVo();
        }else{
             dataList = service.fetchList(searchVo);
        }
        
        return "finance_subbank_list";
    }
    
    public String toSynchSubbankTask(){
        return "synch_subbank_task";
    }
    
    public String synchSubbankTask(){
        return "info";
    }

    public FinanceSubbankVo getSearchVo() {
        return searchVo;
    }

    public void setSearchVo(FinanceSubbankVo searchVo) {
        this.searchVo = searchVo;
    }

    public List<FinanceSubbankEntity> getDataList() {
        return dataList;
    }

    public void setDataList(List<FinanceSubbankEntity> dataList) {
        this.dataList = dataList;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
    
}
