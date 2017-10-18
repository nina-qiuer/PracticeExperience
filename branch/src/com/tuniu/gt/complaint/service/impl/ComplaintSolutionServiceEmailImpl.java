/*
 * Copyright (C) 2006-2017 Tuniu All rights reserved
 * Author:fangyouming
 * Date：2017年7月24日
 * Description: 功能描述
 */
package com.tuniu.gt.complaint.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.complaint.Constans;
import com.tuniu.gt.complaint.dao.impl.ComplaintSolutionEmailDao;
import com.tuniu.gt.complaint.entity.ComplaintEmailInfoEntity;
import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.entity.ComplaintSolutionEmailEntity;
import com.tuniu.gt.complaint.service.IComplaintEmailConfigService;
import com.tuniu.gt.complaint.service.IComplaintEmailInfoService;
import com.tuniu.gt.complaint.service.IComplaintSolutionEmailService;
import com.tuniu.gt.complaint.service.IComplaintTSPService;
import com.tuniu.gt.frm.action.DBConfigManager;
import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.frm.service.system.ApplicationContextUtils;
import com.tuniu.gt.frm.service.system.IUserService;
import com.tuniu.gt.toolkit.DateUtil;
import com.tuniu.gt.toolkit.MailerThread;

/**
 * ComplaintSolutionServiceEmailImpl.java
 *
 * @author fangyouming
 */
@Service("complaint_service_complaint_impl-complaint_solution_email")
public class ComplaintSolutionServiceEmailImpl extends ServiceBaseImpl<ComplaintSolutionEmailDao> implements
        IComplaintSolutionEmailService {
    private static Logger logger = Logger.getLogger(ComplaintSolutionServiceEmailImpl.class);
    private static List<String> thirdPartyComplaintSources = new ArrayList<String>();
    static{
        thirdPartyComplaintSources.add("网站");
        thirdPartyComplaintSources.add("当地质检");
        thirdPartyComplaintSources.add("旅游局");
        thirdPartyComplaintSources.add("微博");
    }
    @Autowired
    private ApplicationContextUtils applicationContextUtils;
    @Autowired
    @Qualifier("complaint_service_complaint_impl-complaint_email_config")
    private IComplaintEmailConfigService emailConfigService;

    @Autowired
    @Qualifier("complaint_dao_impl-complaint_solution_email")
    public void setDao(ComplaintSolutionEmailDao dao) {
        this.dao = dao;
    }

    @Autowired
    @Qualifier("tspService")
    private IComplaintTSPService tspService;
    @Autowired
    @Qualifier("complaint_service_complaint_impl-complaint_email_info")
    private IComplaintEmailInfoService complaintEmailInfoService;
    @Autowired
    @Qualifier("frm_service_system_impl-user")
    private IUserService userService;

    @Override
    public ComplaintSolutionEmailEntity getComplaintSolutionEmailBysolutionId(Integer solutionId) {
        ComplaintSolutionEmailEntity complaintSolutionEmailEntity = dao.getBySolutionId(solutionId);
        return complaintSolutionEmailEntity;
    }

    @Override
    public void saveComplaintSolutionEmail(ComplaintSolutionEmailEntity entity) {
        dao.insert(entity);
    }

    @Override
    public void updateComplaintSolutionEmail(ComplaintSolutionEmailEntity entity) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("e", entity);
        dao.updateById(map);
    }

    @Override
    public void sendComplaintSolutionEmail(ComplaintSolutionEmailEntity entity, ComplaintEntity complaintEntity,
            UserEntity user) {
        String title = entity.getEmailName();
        String content = getEmailContent(entity, user);
        tspService.sendMail(new MailerThread(getReEmails(entity, complaintEntity),
                getCcEmails(entity, complaintEntity), title, content));

    }

    private String getEmailContent(ComplaintSolutionEmailEntity entity, UserEntity user) {
        String content = "<strong>邮件名称:</strong>" + entity.getEmailName() + "<br>";
        content += "<strong>线路名称:</strong>" + entity.getRoute() + "<br>";
        content += "<strong>订单号:</strong>" + entity.getOrderId() + "<br>";
        content += "<strong>供应商名称:</strong>" + entity.getAgencyName() + "<br>";
        content += "<strong>出游日期:</strong>" + DateUtil.formatDate(entity.getStartDate(), "yyyy-MM-dd") + "<br>";
        if(entity.getEmailType() == 2){
            content += "<strong>出游人数:</strong>" + entity.getGuestNum() + "<br>";
            content += "<strong>受伤客人信息:</strong>" + entity.getPassengerInfo() + "<br>";
        }
        if(entity.getEmailType() == 3){
            content += "<strong>同团订单:</strong>" + entity.getGroupOrders() + "<br>";
        }
        content += "<strong>客人投诉点:</strong>" + entity.getRemark() + "<br>";
        content += "<strong>核实处理情况:</strong>" + entity.getCheckProgress() + "<br>";
        if(entity.getEmailType() == 1 && StringUtils.isNotBlank(entity.getMakeBetter())){
            content += "<strong>建议改进点:</strong>" + entity.getMakeBetter() + "<br>";
        }
        content += "<strong>发送人:</strong>" + user.getRealName() + "<br>";
        return content;
    }

    /**
     * 获取邮件主送人员（订单相关、非配置）
     * 
     * @return
     */
    private String[] getReEmails(ComplaintSolutionEmailEntity cseEntity, ComplaintEntity entity) {
        Set<String> reEmails = new LinkedHashSet<String>();
        ComplaintEmailInfoEntity complaintEmailInfoEntity = complaintEmailInfoService
                .getComplaintEmailInfoByComplaintId(entity.getId());
        if(complaintEmailInfoEntity != null){
            String[] re = complaintEmailInfoEntity.getEmailRec().split(",");
            List<String> b = Arrays.asList(re);
            reEmails.addAll(b);
        }
        String[] res = cseEntity.getReceiveName().split(",");
        if(res != null && res.length > 0){
            List<String> reList = new ArrayList<String>();
            for(String name : res){

                UserEntity user = userService.getUserByRealName(name);
                if(null != user){

                    reList.add(user.getEmail());
                }

            }
            reEmails.addAll(reList);
        }
        reEmails.remove("");
        return reEmails.toArray(new String[reEmails.size()]);
    }

    /**
     * 获取邮件抄送人员（订单无关、配置的人员）
     */
    private String[] getCcEmails(ComplaintSolutionEmailEntity cseEntity, ComplaintEntity entity) {

        Set<String> ccEmails = new LinkedHashSet<String>();

        ComplaintEmailInfoEntity complaintEmailInfoEntity = complaintEmailInfoService
                .getComplaintEmailInfoByComplaintId(entity.getId());
        if(complaintEmailInfoEntity != null){
            String[] cc = complaintEmailInfoEntity.getEmailCc().split(",");
            List<String> b = Arrays.asList(cc);
            ccEmails.addAll(b);
        }
        String[] ccs = cseEntity.getCcName().split(",");
        if(ccs != null && ccs.length > 0){
            List<String> ccList = new ArrayList<String>();
            for(String name : ccs){

                UserEntity user = userService.getUserByRealName(name);
                if(null != user){

                    ccList.add(user.getEmail());
                }

            }
            ccEmails.addAll(ccList);
        }
        String souEmailKey = "cc.solution.one.email.受伤报备";
        if(cseEntity.getEmailType() == 2 || cseEntity.getEmailType() == 3){
            ccEmails.addAll(DBConfigManager.getConfigAsList(souEmailKey));
        }
        ccEmails.remove("");
        return ccEmails.toArray(new String[ccEmails.size()]);
    }
}
