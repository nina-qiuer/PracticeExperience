/**
 * SUNING APPLIANCE CHAINS. Copyright (c) 2010-2010 All Rights Reserved.
 */
package com.tuniu.gt.complaint.tsp;

/**
 * <pre>
 * 管理门户错误码常量类
 * 
 * Change:
 *    CRM接口代码:http://phoenix.tuniu.org/wiki/interface-code
 *     -- 11xxxx
 *    
 *    其中后四位xxxx描述
 *      以0开头（0xxx）表接口级别的错误，如参数错误，空值等
 *      非0开头表示业务级别的错误，如1xxx表示老带新接口业务不允许的规则错误等
 *      
 *     111xxx: 老带新业务
 *     112xxx: 
 *     113xxx:
 * </pre>
 * 
 * @author liml
 * @author baomingfeng
 */
public class AdminErrorCode {

    public static final String MSG_SAVE_SUCCESS = "msg.save.success";

    public static final String MSG_SAVE_FAIL = "msg.save.fail";

    public static final String MSG_DEL_SUCCESS = "msg.del.success";

    public static final String MSG_DEL_FAIL = "msg.del.fail";

    /** 示例 */
    public static final String ERROR_USER_NOT_EXIST = "error.user.not.exist";

    /** 日期格式错误 */
    public static final String ERROR_DATE_FORMATE = "error.dateformate";

    // 执行成功
    public static final Integer OPERATION_SUCCESS = 110000;

    // 参数格式有误
    public static final Integer PARAMETER_FORMAT_ERROR = 110001;

    // 参数为空
    public static final Integer PARAMETER_IS_NULL = 110002;

    // 接口处理逻辑后返回为空
    public static final Integer RETURN_IS_NULL = 110003;

    // 参数非法
    public static final Integer PARAMETER_IS_INVALID = 110004;

    public final static Integer GROUP_CODE_AMOUNT = 100;
    // 内部处理错误,接口异常
    public static final Integer INTERNAL_ERROR = 110005;

    // 第三方接口返回错误
    public static final Integer THIRD_INTERFACE_ERROR = 110006;

    // Ip限制
    public static final Integer IP_RESTRICT = 110007;

    // 发送个数最大数限
    public static final Integer SEND_MAX_NUM_RESTRICT = 110008;
}
