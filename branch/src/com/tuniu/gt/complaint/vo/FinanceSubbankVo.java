/**
 * 
 */
package com.tuniu.gt.complaint.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangye
 *
 */
public class FinanceSubbankVo {
    private String bankName; // 银行名称
    
    private String subbankProvince; // 分行省份
    
    private String subbankCity; // 分行城市

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
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
    
}
