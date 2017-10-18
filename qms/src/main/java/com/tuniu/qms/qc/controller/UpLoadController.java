package com.tuniu.qms.qc.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.tuniu.qms.access.client.CmpClient;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.OperationLogService;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.common.util.ExcelImportUtil;
import com.tuniu.qms.common.util.HandlerResult;
import com.tuniu.qms.common.util.UpLoadUtil;
import com.tuniu.qms.qc.dto.QcBillDto;
import com.tuniu.qms.qc.model.AssignConfigCmp;
import com.tuniu.qms.qc.model.DevUpLoadAttach;
import com.tuniu.qms.qc.model.QcBill;
import com.tuniu.qms.qc.model.QcMonitor;
import com.tuniu.qms.qc.model.QcMonitorRelation;
import com.tuniu.qms.qc.model.QcPointAttach;
import com.tuniu.qms.qc.model.QcType;
import com.tuniu.qms.qc.model.UpLoadAttach;
import com.tuniu.qms.qc.service.AssignConfigCmpService;
import com.tuniu.qms.qc.service.DevUpLoadService;
import com.tuniu.qms.qc.service.InnerQcBillService;
import com.tuniu.qms.qc.service.QcBillService;
import com.tuniu.qms.qc.service.QcMonitorRelationService;
import com.tuniu.qms.qc.service.QcMonitorService;
import com.tuniu.qms.qc.service.QcPointAttachService;
import com.tuniu.qms.qc.service.QcTypeService;
import com.tuniu.qms.qc.service.UpLoadService;
import com.tuniu.qms.qc.util.AttachBillType;
import com.tuniu.qms.qc.util.QcConstant;

/**
 * @author chenhaitao
 */
@Controller
@RequestMapping("/qc/upload")
@SessionAttributes("loginUser")
public class UpLoadController {

    private static final Logger logger = LoggerFactory.getLogger(UpLoadController.class);

    @Autowired
    private UpLoadService upLoadService;

    @Autowired
    private DevUpLoadService devLoadService;

    @Autowired
    private QcPointAttachService qcPointService;

    @Autowired
    private QcBillService qcBillService;

    @Autowired
    private QcTypeService qcTypeService;

    @Autowired
    private InnerQcBillService innerQcBillService;

    @Autowired
    private AssignConfigCmpService assignConfigCmpService;

    @Autowired
    private OperationLogService operLogService;

    @Autowired
    private QcMonitorService qcMonitorService;
    
    @Autowired
    private QcMonitorRelationService qcMonitorRelationService;
    
    /**
     * 上传质量问题附件
     * 
     * @param request
     * @param iqcId
     * @return
     */
    @RequestMapping("/{qpId}/qcUploadFile")
    public String qcUploadFile(@PathVariable("qpId") Integer qpId, HttpServletRequest request) {

        request.setAttribute("qpId", qpId);
        return "qc/questionProblemUpload";

    }

    /**
     * 上传文件　单个文件上传
     * 
     * @param context
     *            前台传参
     * @return HandlerResult
     */
    @RequestMapping(value = "fileUpload", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public HandlerResult uploadFile(HttpServletRequest request) {
        HandlerResult result = HandlerResult.newInstance();
        try{

            User user = (User)request.getSession().getAttribute("loginUser");
            // 设置上下方文
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession()
                    .getServletContext());
            // 检查form是否有enctype="multipart/form-data"
            if(multipartResolver.isMultipart(request)){
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;

                MultipartFile mf = multiRequest.getFile("fileInput");
                long size = mf.getSize();
                if(size > 10485760){ // 10M

                    result.setRetCode(Constant.SYS_FAILED);
                    result.setRetObj("文件大小不能超过10M!");
                    return result;
                }
                String filename = mf.getOriginalFilename(); // 获得文件名
                File file = UpLoadUtil.mfToFile(mf);// MultipartFile转成File
                String extType = multiRequest.getParameter("extType");// 获取文件后缀
                String qpId = multiRequest.getParameter("qpId");// 质量问题单
                String url = UpLoadUtil.uploadFile(file, filename);
                if(!"".equals(url)){

                    UpLoadAttach attch = new UpLoadAttach();
                    attch.setPath(url);
                    attch.setQpId(Integer.parseInt(qpId));
                    attch.setName(filename);
                    attch.setType(extType);
                    attch.setDelFlag(0);
                    attch.setAddPerson(user.getRealName());
                    upLoadService.add(attch);
                    result.setRetCode(Constant.SYS_SUCCESS);
                    result.setRetObj("文件上传成功!");
                }
                else{
                    result.setRetCode(Constant.SYS_FAILED);
                    result.setRetObj("文件上传失败,请刷新页面重新上传!");
                }
            }
        }
        catch(Exception e){
            logger.error("文件导入失败", e);
            result.setRetCode(Constant.SYS_FAILED);
            result.setRetObj("文件上传出错,请刷新页面重新上传!");
        }

        return result;
    }

    /**
     * @param request
     * @return
     */
    @RequestMapping("deleteUpLoad")
    @ResponseBody
    public HandlerResult deleteUpLoad(HttpServletRequest request, @RequestParam("upId") Integer upId) {

        HandlerResult result = HandlerResult.newInstance();
        try{

            upLoadService.delete(upId);
            result.setRetCode(Constant.SYS_SUCCESS);
            result.setRetObj("删除成功!");
        }
        catch(Exception e){

            e.printStackTrace();
            result.setRetCode(Constant.SYS_FAILED);
            result.setRetObj("删除失败!");

        }

        return result;
    }

    /**
     * 上传研发质检附件
     * 
     * @param request
     * @param iqcId
     * @return
     */
    @RequestMapping("/{devId}/devUploadFile")
    public String devUploadFile(@PathVariable("devId") Integer devId, HttpServletRequest request) {

        request.setAttribute("devId", devId);
        return "qc/qc_development/devUpload";
    }

    /**
     * 上传文件　单个文件上传
     * 
     * @param context
     *            前台传参
     * @return HandlerResult
     */
    @RequestMapping(value = "devFileUpload", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public HandlerResult devFileUpload(HttpServletRequest request) {
        HandlerResult result = HandlerResult.newInstance();
        try{

            User user = (User)request.getSession().getAttribute("loginUser");
            // 设置上下方文
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession()
                    .getServletContext());
            // 检查form是否有enctype="multipart/form-data"
            if(multipartResolver.isMultipart(request)){
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;

                MultipartFile mf = multiRequest.getFile("fileInput");
                long size = mf.getSize();
                if(size > 10485760){ // 10M

                    result.setRetCode(Constant.SYS_FAILED);
                    result.setRetObj("文件大小不能超过10M!");
                    return result;
                }
                String filename = mf.getOriginalFilename(); // 获得文件名
                File file = UpLoadUtil.mfToFile(mf);// MultipartFile转成File
                String extType = multiRequest.getParameter("extType");// 获取文件后缀
                String devId = multiRequest.getParameter("devId");// 质量问题单
                String url = UpLoadUtil.uploadFile(file, filename);
                if(!"".equals(url)){

                    DevUpLoadAttach attch = new DevUpLoadAttach();
                    attch.setPath(url);
                    attch.setDevId(Integer.parseInt(devId));
                    attch.setName(filename);
                    attch.setType(extType);
                    attch.setDelFlag(0);
                    attch.setAddPerson(user.getRealName());
                    devLoadService.add(attch);
                    result.setRetCode(Constant.SYS_SUCCESS);
                    result.setRetObj("文件上传成功!");
                }
                else{
                    result.setRetCode(Constant.SYS_FAILED);
                    result.setRetObj("文件上传失败,请刷新页面重新上传!");
                }
            }
        }
        catch(Exception e){
            logger.error("文件导入失败", e);
            result.setRetCode(Constant.SYS_FAILED);
            result.setRetObj("文件上传出错,请刷新页面重新上传!");
        }

        return result;
    }

    /**
     * @param request
     * @return
     */
    @RequestMapping("deleteDevUpLoad")
    @ResponseBody
    public HandlerResult deleteDevUpLoad(HttpServletRequest request, @RequestParam("upId") Integer upId) {

        HandlerResult result = HandlerResult.newInstance();
        try{

            devLoadService.delete(upId);
            result.setRetCode(Constant.SYS_SUCCESS);
            result.setRetObj("删除成功!");
        }
        catch(Exception e){
            logger.error("文件导入失败", e);
            result.setRetCode(Constant.SYS_FAILED);
            result.setRetObj("删除失败!");
        }

        return result;
    }

    /**
     * 上传运营质检附件
     * 
     * @param request
     * @param iqcId
     * @return
     */
    @RequestMapping("/{qcId}/innerQcUploadFile")
    public String innerQcUploadFile(@PathVariable("qcId") Integer qcId, HttpServletRequest request) {

        request.setAttribute("qcId", qcId);
        return "qc/qc_inner/innerQcUpload";

    }

    /**
     * 上传文件　单个文件上传
     * 
     * @param context
     *            前台传参
     * @return HandlerResult
     */
    @RequestMapping(value = "innerfileUpload", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public HandlerResult innerfileUpload(HttpServletRequest request) {
        HandlerResult result = HandlerResult.newInstance();
        try{

            User user = (User)request.getSession().getAttribute("loginUser");
            // 设置上下方文
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession()
                    .getServletContext());
            // 检查form是否有enctype="multipart/form-data"
            if(multipartResolver.isMultipart(request)){
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;

                MultipartFile mf = multiRequest.getFile("fileInput");
                long size = mf.getSize();
                if(size > 10485760){ // 10M

                    result.setRetCode(Constant.SYS_FAILED);
                    result.setRetObj("文件大小不能超过10M!");
                    return result;
                }
                String filename = mf.getOriginalFilename(); // 获得文件名
                File file = UpLoadUtil.mfToFile(mf);// MultipartFile转成File
                String extType = multiRequest.getParameter("extType");// 获取文件后缀
                String qcId = multiRequest.getParameter("qcId");// 质检问题单
                String url = UpLoadUtil.uploadFile(file, filename);
                if(!"".equals(url)){

                    QcPointAttach attch = new QcPointAttach();
                    attch.setPath(url);
                    attch.setQcId(Integer.parseInt(qcId));
                    attch.setName(filename);
                    attch.setType(extType);
                    attch.setDelFlag(0);
                    attch.setAddPerson(user.getRealName());
                    attch.setBillType(Constant.ATTACH_BILL_TYPE_QC);// 关联单据类型
                    qcPointService.add(attch);
                    result.setRetCode(Constant.SYS_SUCCESS);
                    result.setRetObj("文件上传成功!");
                }
                else{
                    result.setRetCode(Constant.SYS_FAILED);
                    result.setRetObj("文件上传失败,请刷新页面重新上传!");
                }
            }
        }
        catch(Exception e){
            logger.error("文件导入失败", e);
            result.setRetCode(Constant.SYS_FAILED);
            result.setRetObj("文件上传出错,请刷新页面重新上传!");
        }

        return result;
    }

    /**
     * @param request
     * @return
     */
    @RequestMapping("deleteQcUpLoad")
    @ResponseBody
    public HandlerResult deleteQcUpLoad(HttpServletRequest request, @RequestParam("upId") Integer upId) {

        HandlerResult result = HandlerResult.newInstance();
        try{
            qcPointService.delete(upId);
            result.setRetCode(Constant.SYS_SUCCESS);
            result.setRetObj("删除成功!");
        }
        catch(Exception e){
            logger.error("文件导入失败", e);
            result.setRetCode(Constant.SYS_FAILED);
            result.setRetObj("删除失败!");

        }

        return result;

    }

    /**
     * 上传文件　记分管理
     * 
     * @param context
     *            前台传参
     * @return HandlerResult
     */
    @RequestMapping(value = "scoreUpload", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public HandlerResult scoreUpload(HttpServletRequest request) {

        HandlerResult result = HandlerResult.newInstance();
        try{
            // 设置上下方文
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession()
                    .getServletContext());
            // 检查form是否有enctype="multipart/form-data"
            if(multipartResolver.isMultipart(request)){
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;

                MultipartFile mf = multiRequest.getFile("fileInput");
                long size = mf.getSize();
                if(size > 10485760){ // 10M
                    result.setRetCode(Constant.SYS_FAILED);
                    result.setRetObj("文件大小不能超过10M!");
                    return result;
                }
                /* String filename = mf.getOriginalFilename(); */// 获得文件名
                // File file = UpLoadUtil.mfToFile(mf);// MultipartFile转成File
                User user = (User)request.getSession().getAttribute("loginUser");

                upLoadService.saveScoreRecord(mf, user);

                result.setRetCode(Constant.SYS_SUCCESS);
                result.setRetObj("记分导入成功!");
            }
        }
        catch(Exception e){
            logger.error("记分导入失败", e);
            result.setRetCode(Constant.SYS_FAILED);
            result.setRetObj("文件上传出错,请刷新页面重新上传!");
        }

        return result;
    }

    /**
     * 内部质检申请导入
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "innerQcApplyUpload", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public HandlerResult innerQcApplyUpload(HttpServletRequest request) {
        HandlerResult result = HandlerResult.newInstance();
        try{

            User user = (User)request.getSession().getAttribute("loginUser");
            // 设置上下方文
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession()
                    .getServletContext());
            // 检查form是否有enctype="multipart/form-data"
            if(multipartResolver.isMultipart(request)){
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;

                MultipartFile mf = multiRequest.getFile("fileInput");
                long size = mf.getSize();
                if(size > 10485760){ // 10M

                    result.setRetCode(Constant.SYS_FAILED);
                    result.setRetObj("文件大小不能超过10M!");
                    return result;
                }
                String filename = mf.getOriginalFilename(); // 获得文件名
                File file = UpLoadUtil.mfToFile(mf);// MultipartFile转成File
                // String extType = multiRequest.getParameter("extType");//获取文件后缀
                Workbook book = ExcelImportUtil.createWorkBook(file, filename);
                saveInnerQcBill(book, user);
                result.setRetCode(Constant.SYS_SUCCESS);
                result.setRetObj("文件上传成功!");
            }
        }
        catch(Exception e){
            logger.error("文件导入失败", e);
            result.setRetCode(Constant.SYS_FAILED);
            result.setRetObj("文件上传出错,请刷新页面重新上传!");
        }

        return result;
    }

    /**
     * 内部质检申请保存
     * 
     * @param book
     * @param user
     */
    private void saveInnerQcBill(Workbook book, User user) throws Exception {
        Sheet sheet = book.getSheetAt(0);
        List<QcBill> qcBillList = new ArrayList<QcBill>();

        int size = sheet.getLastRowNum();
        for(int i = 1; i <= size; i++){
            // 第二行开始为数据
            Row row = sheet.getRow(i);

            QcBill qcBill = new QcBill();

            // 内部质检
            qcBill.setGroupFlag(Constant.QC_INNER);
            // 发起中
            qcBill.setState(QcConstant.QC_BILL_STATE_WAIT);
            qcBill.setDelFlag(Constant.NO);
            qcBill.setUserFlag(QcConstant.USER_FLAG_TRUE);
            qcBill.setAddPerson(user.getRealName());
            qcBill.setAddPersonId(user.getId());

            if(cellNotNull(row.getCell(1))){
                qcBill.setOrdId((int)row.getCell(1).getNumericCellValue());
            }
            else{
                qcBill.setOrdId(0);
            }

            if(cellNotNull(row.getCell(2))){
                qcBill.setPrdId((int)row.getCell(2).getNumericCellValue());
            }
            else{
                qcBill.setPrdId(0);
            }

            qcBill.setLossAmount((double)row.getCell(3).getNumericCellValue());
            QcType qcType = qcTypeService.getTypeName(row.getCell(4).getStringCellValue());
            if(null != qcType){

                qcBill.setQcTypeId(qcType.getId());
            }
            // 质检事宜概述
            qcBill.setQcAffairSummary(row.getCell(5).getStringCellValue().trim());

            if(cellNotNull(row.getCell(6))){
                // 质检事宜详述
                qcBill.setQcAffairDesc(row.getCell(6).getStringCellValue().trim());
            }
            else{
                qcBill.setQcAffairDesc("");
            }

            qcBillList.add(qcBill);
        }

        innerQcBillService.batchAddInQcBill(qcBillList);
    }

    /**
     * 单元格非空判断
     * 
     * @param cell
     * @return
     */
    private boolean cellNotNull(Cell cell) {
        Boolean flag = false;

        if(cell != null){
            String cellContent = cell.toString().trim();

            if(!("").equals(cellContent)){
                flag = true;
            }
        }

        return flag;
    }

    @RequestMapping(value = "cmpQcApplyUpload", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public HandlerResult cmpQcApplyUpload(HttpServletRequest request) {
        HandlerResult result = HandlerResult.newInstance();
        try{
            User user = (User)request.getSession().getAttribute("loginUser");

            // 设置上下方文
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession()
                    .getServletContext());
            // 检查form是否有enctype="multipart/form-data"
            if(multipartResolver.isMultipart(request)){
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;

                MultipartFile mf = multiRequest.getFile("fileInput");
                long size = mf.getSize();
                if(size > 10485760){ // 10M

                    result.setRetCode(Constant.SYS_FAILED);
                    result.setRetObj("文件大小不能超过10M!");
                    return result;
                }
                String filename = mf.getOriginalFilename(); // 获得文件名
                File file = UpLoadUtil.mfToFile(mf);// MultipartFile转成File

                Workbook book = ExcelImportUtil.createWorkBook(file, filename);
                saveCmpQcBill(result, book, user);
            }

        }
        catch(Exception e){
            logger.info("投诉质检导入失败!", e);
            result.setRetCode(Constant.SYS_FAILED);
            result.setRetObj("文件上传出错,请刷新页面重新上传!");
        }
        return result;
    }

    /**
     * 投诉质检导入
     * 
     * @param result
     * @param book
     * @param user
     */
    private void saveCmpQcBill(HandlerResult result, Workbook book, User user) {
        java.sql.Date groupDate = DateUtil.parseSqlDate("1970-01-01");// 团期判断
        StringBuilder[] errorMsg = new StringBuilder[]{new StringBuilder(""), new StringBuilder(""),
                new StringBuilder(""), new StringBuilder("")};// 错误信息提示
        boolean flag = true;// 数据验证是否通过
        QcBillDto dto;
        Set<Integer> cmpIdSet = new HashSet<Integer>();

        List<QcBill> qcBillList = new ArrayList<QcBill>();// 待插入列表
        Sheet sheet = book.getSheetAt(0);
        int size = sheet.getLastRowNum();
        for(int i = 1; i <= size; i++){
            Row row = sheet.getRow(i);// 从第二行开始取数据

            if(cellNotNull(row.getCell(0))){
                Integer cmpId = (int)row.getCell(0).getNumericCellValue();

                dto = new QcBillDto();// 用于判断投诉单是否已经有质检单
                dto.setCmpId(cmpId);
                QcBill bill = qcBillService.getQcBill(dto);// 质检单
                if(bill != null){
                    errorMsg[0].append(cmpId).append(",");
                    flag = false;
                    continue;
                }

                QcBill qcBill = CmpClient.getQcInfo(cmpId);// 调投诉接口,获得投诉单的一些信息
                if(qcBill != null){// 是否存在投诉单号

                    AssignConfigCmp qcPerson = assignConfigCmpService.getQcPersonByName(row.getCell(1).toString());// 质检员
                                                                                                                   // 根据姓名查询
                    if(qcPerson == null){// 质检员不存在
                        flag = false;
                        errorMsg[2].append(row.getCell(1).toString()).append(", ");
                        continue;
                    }

                    if(cmpIdSet.contains(cmpId)){// 如果投诉单号重复
                        flag = false;
                        errorMsg[3].append(cmpId).append(",");
                        continue;
                    }
                    else{
                        cmpIdSet.add(cmpId);
                    }

                    if(qcBill.getGroupDate().toString().equals(groupDate.toString())){// 团期
                        qcBill.setGroupDate(null);
                    }
                    qcBill.setState(QcConstant.QC_BILL_STATE_QCBEGIN);// 处理中
                    qcBill.setAddPerson(user.getRealName());
                    qcBill.setAddPersonId(user.getId());
                    qcBill.setQcPersonId(qcPerson.getQcPersonId());// 质检员
                    qcBill.setQcPerson(qcPerson.getQcPersonName());

                    qcBillList.add(qcBill);
                }
                else{
                    flag = false;
                    errorMsg[1].append(cmpId).append(",");
                }
            }
        }

        if(flag){// 质检单添加
            for(QcBill qcBill : qcBillList){
                qcBillService.add(qcBill);

                operLogService.addQcOperationLog(qcBill.getId(), user, "质检导入", "质检单：" + qcBill.getId() + " 导入");
            }
            result.setRetCode(Constant.SYS_SUCCESS);
            result.setRetObj("文件上传成功!");
        }
        else{// 错误信息返回
            result.setRetCode(Constant.SYS_FAILED);
            result.setRetObj(createErrorMsg(errorMsg));
        }

    }

    /**
     * 导入错误信息汇总
     * 
     * @param errorMsg
     *            错误信息
     * @return
     */
    private String createErrorMsg(StringBuilder[] errorMsg) {
        StringBuilder errorMsgs = new StringBuilder();
        String cmpIdNoExtis = "投诉单号：";

        if(errorMsg[0].length() > 0){// 已有质检
            errorMsgs.append(cmpIdNoExtis).append(errorMsg[0]).append("已有质检, 请删除后重新导入！<br>");
        }
        if(errorMsg[1].length() > 0){// 投诉单不存在
            errorMsgs.append(cmpIdNoExtis).append(errorMsg[1]).append("不存在,请删除后重新导入！<br>");
        }
        if(errorMsg[2].length() > 0){// 质检员不存在
            errorMsgs.append("质检员").append(errorMsg[2]).append("不存在,请删除后重新导入！<br>");
        }
        if(errorMsg[3].length() > 0){// 投诉单重复
            errorMsgs.append(cmpIdNoExtis).append(errorMsg[3]).append("重复,请删除后重新导入！<br>");
        }

        return errorMsgs.toString();
    }

    /**
     * 上传运营质检附件
     * 
     * @param request
     * @param iqcId
     * @return
     */
    @RequestMapping("/{impId}/improveUploadFile")
    public String improveUploadFile(@PathVariable("impId") Integer impId, HttpServletRequest request) {

        request.setAttribute("impId", impId);
        return "qs/improve/improveUpload";
    }

    /**
     * 上传文件　单个文件上传
     * 
     * @param context
     *            前台传参
     * @return HandlerResult
     */
    @RequestMapping(value = "improvefileUpload", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public HandlerResult improvefileUpload(HttpServletRequest request) {
        HandlerResult result = HandlerResult.newInstance();
        try{

            saveAttach(request, result, Constant.ATTACH_BILL_TYPE_IMPROVE);

        }
        catch(Exception e){
            logger.error("文件导入失败", e);
            result.setRetCode(Constant.SYS_FAILED);
            result.setRetObj("文件上传出错,请刷新页面重新上传!");
        }

        return result;
    }

    /**
     * 产品上线附件上传
     * 
     * @param punishPrdId
     * @param request
     * @return
     */
    @RequestMapping("/{punishPrdId}/prdOnlineUploadFile")
    public String prdOnlineUploadFile(@PathVariable("punishPrdId") Integer punishPrdId, HttpServletRequest request) {

        request.setAttribute("punishPrdId", punishPrdId);
        return "qs/punishPrd/onlineUpload";
    }

    @RequestMapping(value = "prdOnlinefileUpload", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public HandlerResult prdOnlinefileUpload(HttpServletRequest request) {
        HandlerResult result = HandlerResult.newInstance();

        try{
            saveAttach(request, result, Constant.ATTACH_BILL_TYPE_PUNISHPRD_ONLINE);
        }
        catch(Exception e){
            logger.error("文件导入失败", e);
            result.setRetCode(Constant.SYS_FAILED);
            result.setRetObj("文件上传出错,请刷新页面重新上传!");
        }

        return result;
    }

    private void saveAttach(HttpServletRequest request, HandlerResult result, int attachBillType) throws Exception {
        // 设置上下方文
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession()
                .getServletContext());
        // 检查form是否有enctype="multipart/form-data"
        if(multipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;

            MultipartFile mf = multiRequest.getFile("fileInput");
            if(mf.getSize() > 10485760){ // 10M
                result.setRetCode(Constant.SYS_FAILED);
                result.setRetObj("文件大小不能超过10M!");
                return;
            }

            User user = (User)request.getSession().getAttribute("loginUser");
            boolean successFlag = savePointAttach(multiRequest, user, attachBillType);

            if(successFlag){
                result.setRetCode(Constant.SYS_SUCCESS);
                result.setRetObj("文件上传成功!");
            }
            else{
                result.setRetCode(Constant.SYS_FAILED);
                result.setRetObj("文件上传失败,请刷新页面重新上传!");
            }
        }
    }

    private boolean savePointAttach(MultipartHttpServletRequest multiRequest, User user, int attachBillType)
            throws Exception {
        MultipartFile mf = multiRequest.getFile("fileInput");

        String url = UpLoadUtil.uploadFile(UpLoadUtil.mfToFile(mf), mf.getOriginalFilename());

        if(!"".equals(url)){
            QcPointAttach attch = new QcPointAttach();

            String parameter = AttachBillType.getContents(attachBillType);
            attch.setQcId(Integer.parseInt(multiRequest.getParameter(parameter)));
            attch.setName(mf.getOriginalFilename());
            attch.setType(multiRequest.getParameter("extType"));
            attch.setPath(url);
            attch.setDelFlag(0);
            attch.setAddPerson(user.getRealName());
            attch.setBillType(attachBillType);

            qcPointService.add(attch);

            return true;
        }

        return false;
    }

    @RequestMapping(value = "qcMonitorUpload", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public HandlerResult qcMonitorUpload(HttpServletRequest request) {
        HandlerResult result = HandlerResult.newInstance();
        try{

            User user = (User)request.getSession().getAttribute("loginUser");
            // 设置上下方文
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession()
                    .getServletContext());
            // 检查form是否有enctype="multipart/form-data"
            if(multipartResolver.isMultipart(request)){
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;

                MultipartFile mf = multiRequest.getFile("fileInput");
                long size = mf.getSize();
                if(size > 10485760){ // 10M

                    result.setRetCode(Constant.SYS_FAILED);
                    result.setRetObj("文件大小不能超过10M!");
                    return result;
                }
                String filename = mf.getOriginalFilename(); // 获得文件名
                File file = UpLoadUtil.mfToFile(mf);// MultipartFile转成File
                // String extType = multiRequest.getParameter("extType");//获取文件后缀
                Workbook book = ExcelImportUtil.createWorkBook(file, filename);
                Map<String, Object> importResMap = saveQcMonitor(book, user);
                result.setRetCode(Constant.SYS_SUCCESS);
                result.setRetObj("文件上传成功!");
                if(Integer.parseInt(importResMap.get("failSize").toString()) > 0){
                    result.setResMsg("导入总数据为"+ importResMap.get("size") +"条,失败" + importResMap.get("failSize") + "条,失败数据为第" + importResMap.get("failList").toString() + "行，请知悉");
                }else{
                    result.setResMsg("导入总数据为"+ importResMap.get("size") +"条,失败" + importResMap.get("failSize") + "条,请知悉");
                }
            }
        }
        catch(Exception e){
            logger.error("文件导入失败", e);
            result.setRetCode(Constant.SYS_FAILED);
            result.setRetObj("文件上传出错,请刷新页面重新上传!");
        }

        return result;
    }

    @Transactional
    private Map<String, Object> saveQcMonitor(Workbook book, User user) throws Exception {
        Sheet sheet = book.getSheetAt(0);
        int size = sheet.getLastRowNum();
        
        Map<String, Object> importResMap = new HashMap<String, Object>();
        importResMap.put("size", size);
        List<Integer> failList = new ArrayList<Integer>(); // 导入失败数据

        for(int i = 1; i <= size; i++){
            // 第二行开始为数据
            Row row = sheet.getRow(i);
            QcMonitor qcMonitor = new QcMonitor();

            if(cellNotNull(row.getCell(0))){
                qcMonitor.setCircle((int)row.getCell(0).getNumericCellValue());
            }
            else{
//                qcMonitor.setCircle(null);
                failList.add(i+1); // 增加导入失败
                continue;
            }

            if(cellNotNull(row.getCell(1))){
                qcMonitor.setQcType(row.getCell(1).getStringCellValue());
            }
            else{
//                qcMonitor.setQcType(null);
                failList.add(i+1); // 增加导入失败
                continue;
            }

            if(cellNotNull(row.getCell(2))){
                qcMonitor.setType(row.getCell(2).getStringCellValue());
            }
            else{
                qcMonitor.setType(null);
            }

            if(cellNotNull(row.getCell(3))){
                qcMonitor.setOrderId((int)row.getCell(3).getNumericCellValue());
            }
            else{
                qcMonitor.setOrderId(null);
            }

            if(cellNotNull(row.getCell(4))){
                qcMonitor.setRouteId((int)row.getCell(4).getNumericCellValue());
            }
            else{
                qcMonitor.setRouteId(null);
            }
            
            if(cellNotNull(row.getCell(11))){
                
                if("合格".equals(row.getCell(11).getStringCellValue().trim())){
                    qcMonitor.setIsQualified(1);
                }else if("不合格".equals(row.getCell(11).getStringCellValue().trim())){
                    qcMonitor.setIsQualified(0);
                }else{
                    failList.add(i+1); // 增加导入失败
                    continue;
                }
            }
            else{
                qcMonitor.setIsQualified(null);
            }

            if(cellNotNull(row.getCell(12))){
                qcMonitor.setQuestionType(row.getCell(12).getStringCellValue());
            }
            else{
                qcMonitor.setQuestionType(null);
            }

            if(cellNotNull(row.getCell(13))){
                qcMonitor.setPoorMoney((double)row.getCell(13).getNumericCellValue());
            }
            else{
                qcMonitor.setPoorMoney(null);
            }

            if(cellNotNull(row.getCell(14))){
                qcMonitor.setReceivableMoney((double)row.getCell(14).getNumericCellValue());
            }
            else{
                qcMonitor.setReceivableMoney(null);
            }

            if(cellNotNull(row.getCell(15))){
                qcMonitor.setQcConclusion(row.getCell(15).getStringCellValue());
            }
            else{
                qcMonitor.setQcConclusion(null);
            }

            if(cellNotNull(row.getCell(16))){
                qcMonitor.setQcPerson(row.getCell(16).getStringCellValue());
            }
            else{
//                qcMonitor.setQcPerson(null);
                failList.add(i+1); // 增加导入失败
                continue;
            }

            if(cellNotNull(row.getCell(17))){
                String dateStr = row.getCell(17).getStringCellValue();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
                try{
                    Date qcDate = sdf.parse(dateStr);
                    qcMonitor.setQcDate(qcDate);
                }
                catch(Exception e){
                    e.printStackTrace();
                    failList.add(i+1); // 增加导入失败
                    continue;
                }
            }
            else{
//                qcMonitor.setQcDate(null);
                failList.add(i+1); // 增加导入失败
                continue;
            }
            qcMonitorService.add(qcMonitor); // 新增
            QcMonitorRelation qcMonitorRelation1 = new QcMonitorRelation();

            if(cellNotNull(row.getCell(5))){
                qcMonitorRelation1.setRelevantPerson(row.getCell(5).getStringCellValue());
            }
            else{
                qcMonitorRelation1.setRelevantPerson(null);
            }

            if(cellNotNull(row.getCell(6))){
                qcMonitorRelation1.setRelevantPost(row.getCell(6).getStringCellValue());
            }
            else{
                qcMonitorRelation1.setRelevantPost(null);
            }

            if(cellNotNull(row.getCell(7))){
                qcMonitorRelation1.setRelevantDepartment(row.getCell(7).getStringCellValue());
            }
            else{
                qcMonitorRelation1.setRelevantDepartment(null);
            }

            qcMonitorRelation1.setMonitorId(qcMonitor.getId());
            qcMonitorRelation1.setRelatedFlag(1);
            qcMonitorRelationService.add(qcMonitorRelation1); // 新增
            QcMonitorRelation qcMonitorRelation2 = new QcMonitorRelation();

            if(cellNotNull(row.getCell(8))){
                qcMonitorRelation2.setRelevantPerson(row.getCell(8).getStringCellValue());
            }
            else{
                qcMonitorRelation2.setRelevantPerson(null);
            }

            if(cellNotNull(row.getCell(9))){
                qcMonitorRelation2.setRelevantPost(row.getCell(9).getStringCellValue());
            }
            else{
                qcMonitorRelation2.setRelevantPost(null);
            }

            if(cellNotNull(row.getCell(10))){
                qcMonitorRelation2.setRelevantDepartment(row.getCell(10).getStringCellValue());
            }
            else{
                qcMonitorRelation2.setRelevantDepartment(null);
            }
            qcMonitorRelation2.setMonitorId(qcMonitor.getId());
            qcMonitorRelation2.setRelatedFlag(0);
            qcMonitorRelationService.add(qcMonitorRelation2); // 新增
        }
        
        importResMap.put("failList", failList); // 导入失败的数据
        importResMap.put("failSize", failList.size()); // 导入失败的个数
        return importResMap;
    }
}
