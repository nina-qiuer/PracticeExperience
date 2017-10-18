/**
 * 
 */
package com.tuniu.gt.complaint.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tuniu.gt.frm.entity.EntityBase;
import com.tuniu.gt.toolkit.DateUtil;

/**
 * @author jiangye
 *
 */
public class FinanceSubbankEntity extends EntityBase {
   
    private String bankName;
    
    private String subbankName;
    
    private Integer bankBranchId;
    
    private String subbankProvince;
    
    private String subbankCity;
    
    private Date subbankExpiryDate;
    
    private Date lastUpdateDate;
    
    private Date addTime;
    
    private Date updateTime;
    
    public static FinanceSubbankEntity fromMap(Map<String,Object> subBankMap){
        FinanceSubbankEntity subBankEntity = new FinanceSubbankEntity();
        subBankEntity.setBankName( subBankMap.get("bankName")+"");
        subBankEntity.setSubbankName(subBankMap.get("subbankName")+"");
        subBankEntity.setBankBranchId(Integer.valueOf(subBankMap.get("bankBranchId")+""));
        subBankEntity.setSubbankProvince(subBankMap.get("subbankProvince")+"");
        subBankEntity.setSubbankCity(subBankMap.get("subbankCity")+"");
        subBankEntity.setLastUpdateDate(DateUtil.parseDateTime(subBankMap.get("lastUpdateDate")+""));
        subBankEntity.setSubbankExpiryDate(DateUtil.parseDate(subBankMap.get("subbankExpiryDate")+""));
        return subBankEntity;
    }
    
    public static List<FinanceSubbankEntity> fromMapList(List<Map<String,Object>> subBankMapList){
        List<FinanceSubbankEntity> subBankList = new ArrayList<FinanceSubbankEntity>();
        for(Map<String, Object> subBank : subBankMapList) {
            subBankList.add(fromMap(subBank));
        }
        return subBankList;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getSubbankName() {
        return subbankName;
    }

    public void setSubbankName(String subbankName) {
        this.subbankName = subbankName;
    }

    public String getSubbankProvince() {
        return subbankProvince;
    }

    public void setSubbankProvince(String subbankProvince) {
        this.subbankProvince = subbankProvince;
    }

    public String getSubbankCity() {
        return subbankCity;
    }

    public void setSubbankCity(String subbankCity) {
        this.subbankCity = subbankCity;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getSubbankExpiryDate() {
        return subbankExpiryDate;
    }

    public void setSubbankExpiryDate(Date subbankExpiryDate) {
        this.subbankExpiryDate = subbankExpiryDate;
    }

    public Integer getBankBranchId() {
        return bankBranchId;
    }

    public void setBankBranchId(Integer bankBranchId) {
        this.bankBranchId = bankBranchId;
    }
    
}
