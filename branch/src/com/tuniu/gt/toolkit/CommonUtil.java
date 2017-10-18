package com.tuniu.gt.toolkit;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

public class CommonUtil {
	
	private static Logger logger = Logger.getLogger(CommonUtil.class);
	
	public static String arrToStr(Object[] arr) {
		StringBuffer sb = new StringBuffer();
		if (null != arr && arr.length > 0) {
			for (int i=0; i<arr.length; i++) {
				sb.append(arr[i].toString());
				if (i < arr.length-1) {
					sb.append(",");
				}
			}
		}
		return sb.toString();
	}
	
	public static boolean strInArray(String str, String[] array) {
		boolean result = false;
		for(int i = 0 ; i < array.length ; i++) {
			if(array[i].equals(str)) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	public static String numToBigStr(int num) {
		String bigStr = "";
		switch (num) {
			case 1:
				bigStr = "一";
				break;
			case 2:
				bigStr = "二";
				break;
			case 3:
				bigStr = "三";
				break;
			case 4:
				bigStr = "四";
				break;
			case 5:
				bigStr = "五";
				break;
			case 6:
				bigStr = "六";
				break;
			case 7:
				bigStr = "七";
				break;
			case 8:
				bigStr = "八";
				break;
			case 9:
				bigStr = "九";
				break;
			case 0:
				bigStr = "〇";
				break;
		}
		return bigStr;
	}
	
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	
	public static void writeResponse(Object data) {
		HttpServletResponse response = ServletActionContext.getResponse(); // 获取原始的PrintWriter对象,以便输出响应结果,而不用跳转到某个试图
		response.setContentType("text/plain"); // 设置输出为文字流
		response.setCharacterEncoding("UTF-8"); // 设置字符集
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.println(data);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 将"(\r\n|\r|\n|\n\r)"替换成<br>
	 * @param oldString
	 * @return
	 */
	public static String replaceEnter(String oldString) {
		// System.out.println("old:"+oldString.length());
		Pattern pattern = Pattern.compile("(\r\n|\r|\n|\n\r)");

		// 正则表达式的匹配一定要是这样，单个替换\r|\n的时候会错误
		Matcher matcher = pattern.matcher(oldString);
		String newString = matcher.replaceAll("<br>");
		// System.out.println("new:"+newString.length());
		return newString;
	}
	
	public static Map<String, Object> getPositionMap() {
		Map<String, Object> positionMap = new LinkedHashMap<String, Object>();
		positionMap.put("1", "供应商");
		positionMap.put("2", "售前客服");
		positionMap.put("3", "客服经理");
		positionMap.put("4", "产品专员");
		positionMap.put("5", "产品经理");
		positionMap.put("6", "售后客服");
		positionMap.put("7", "出游中客服");
		positionMap.put("8", "售后经理");
		positionMap.put("9", "签证专员");
		positionMap.put("10", "签证经理");
		positionMap.put("11", "预订员");
		positionMap.put("12", "资金专员");
		positionMap.put("39", "资金经理");
		positionMap.put("13", "结算专员");
		positionMap.put("14", "门市前台");
		positionMap.put("15", "机票专员");
		positionMap.put("16", "计调专员");
		positionMap.put("17", "酒店门票预定专员");
		positionMap.put("18", "采购专家");
		positionMap.put("19", "确认专员");
		positionMap.put("20", "直采导游");
		positionMap.put("21", "项目负责人");
		positionMap.put("22", "软件工程师");
		positionMap.put("23", "研发主管");
		positionMap.put("24", "运营专员");
		positionMap.put("25", "运营经理");
		positionMap.put("26", "采购经理");
		positionMap.put("27", "售后出游前客服");
		positionMap.put("28", "售后出游前客服经理");
		positionMap.put("29", "分公司总经理");
		positionMap.put("30", "区域分部经理");
		positionMap.put("31", "门市经理");
		positionMap.put("32", "审核专员");
		positionMap.put("33", "审核经理");
		positionMap.put("34", "营销专员");
		positionMap.put("35", "营销经理");
		positionMap.put("36", "VIP顾问");
		positionMap.put("37", "VIP顾问经理");
		positionMap.put("38", "销售专家");
		positionMap.put("39", "客服专员");
		
		return positionMap;
	}
	
	
	
	public static String getProductTypeName(Integer productType) {
		String productTypeName = "";
		switch (productType) {
		case 1:
			productTypeName = "跟团游";
			break;
		case 2:
			productTypeName = "自助游";
			break;
		case 3:
			productTypeName = "酒店";
			break;
		case 4:
			productTypeName = "机票";
			break;
		case 5:
			productTypeName = "团队游";
			break;
		case 6:
			productTypeName = "门票";
			break;
		case 8:
			productTypeName = "自驾游";
			break;
		case 10:
			productTypeName = "签证";
			break;
		case 12:
			productTypeName = "邮轮";
			break;
		case 14:
			productTypeName = "火车票";
			break;
		case 22:
			productTypeName = "目的地服务";
			break;
		case 44:
			productTypeName = "用车服务";
			break;
		case 52:
			productTypeName = "金融";
			break;
		case 54:
			productTypeName = "汽车票";
			break;
		case 61:
			productTypeName = "票务";
			break;
		default:
			break;
		}
		return productTypeName;
	}
	
	public static String getProductTypeById(Integer productType) {
		String productTypeName = "";
		switch (productType) {
		case 1:
			productTypeName = "跟团游";
			break;
		case 2:
			productTypeName = "自助游";
			break;
		case 3:
			productTypeName = "酒店";
			break;
		case 4:
			productTypeName = "机票";
			break;
		case 5:
			productTypeName = "团队游";
			break;
		case 6:
			productTypeName = "门票";
			break;
		case 8:
			productTypeName = "自驾游";
			break;
		case 9:
			productTypeName = "签证";
			break;
		case 10:
			productTypeName = "邮轮";
			break;
		case 11:
			productTypeName = "火车票";
			break;
		default:
			break;
		}
		return productTypeName;
	}
}
