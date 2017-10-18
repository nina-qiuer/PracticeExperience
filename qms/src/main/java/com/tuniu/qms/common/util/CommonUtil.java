package com.tuniu.qms.common.util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;

import com.tuniu.qms.common.model.Department;
import com.tuniu.qms.common.model.Job;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.qc.model.QcType;
import com.tuniu.qms.qc.model.QualityProblemType;


public class CommonUtil {
	
	public static String decodeBase64Str(String base64Str) {
		String str = null;
		try {
			str =  new String(Base64.decodeBase64(base64Str.getBytes(Constant.ENCODING)), Constant.ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	public static String encodeBase64Str(String str) {
		String base64Str = null;
		try {
			base64Str = Base64.encodeBase64String(str.getBytes(Constant.ENCODING));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return base64Str;
	}
	
	public static List<String> getDepNames(List<Department> depList) {
		List<String> depNames = new ArrayList<String>();
		for (Department dep : depList) {
			depNames.add(dep.getFullName());
		}
		return depNames;
	}
	
	public static List<String> getQtNames(List<QcType> qtList) {
		List<String> qtNames = new ArrayList<String>();
		for (QcType qt : qtList) {
			qtNames.add(qt.getFullName());
		}
		return qtNames;
	}
	
	public static List<String> getJobNames(List<Job> jobList) {
		List<String> jobNames = new ArrayList<String>();
		for (Job job : jobList) {
			jobNames.add(job.getName());
		}
		return jobNames;
	}
	
	
	public static List<String> getQpTypeNames(List<QualityProblemType> qpList) {
		List<String> qpTypeNames = new ArrayList<String>();
		for (QualityProblemType qp : qpList) {
			qpTypeNames.add(qp.getFullName());
		}
		return qpTypeNames;
	}
	
	public static List<Map<String, Object>> getUserNames(List<User> userList) {
		List<Map<String, Object>> userNames = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = null;
		for (User user : userList) {
			map = new HashMap<String, Object>();
			map.put("realName", user.getRealName());
			map.put("label", user.getRealName() + user.getUserName());
			userNames.add(map);
		}
		return userNames;
	}
	
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
	
	/** 判断是否是纯数字 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
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
	
	public static String getDestCateName(Integer destCateId) {
		String destCateName = "";
		switch (destCateId) {
		case 2:
			destCateName = "亚洲";
			break;
		case 5:
			destCateName = "出境当地参团";
			break;
		case 8:
			destCateName = "出境短线";
			break;
		case 9:
			destCateName = "出境长线";
			break;
		case 10:
			destCateName = "国内当地参团";
			break;	
		case 11:
			destCateName = "国内长线";
			break;
		case 12:
			destCateName = "周边";
			break;
		case 15:
			destCateName = "非洲";
			break;
		case 19:
			destCateName = "美洲";
			break;
		case 20:
			destCateName = "欧洲";
			break;
		case 71:
			destCateName = "澳洲";
			break;
		case 76:
			destCateName = "出境";
			break;
		case 80:
			destCateName = "境内";
			break;
		case 81:
			destCateName = "境外";
			break;
		default:
			break;
		}
		return destCateName;
	}
	
	/**
	 * Unicode 转换成utf "\\u006A\\u0061"
	 * @param str
	 * @return
	 */
	public static String decodeUnicode(String str) {  
        Charset set = Charset.forName("UTF-16");  
        Pattern p = Pattern.compile("\\\\u([0-9a-fA-F]{4})");  
        Matcher m = p.matcher( str );  
        int start = 0 ;  
        int start2 = 0 ;  
        StringBuffer sb = new StringBuffer();  
        while( m.find( start ) ) {  
            start2 = m.start() ;  
            if( start2 > start ){  
                String seg = str.substring(start, start2) ;  
                sb.append( seg );  
            }  
            String code = m.group( 1 );  
            int i = Integer.valueOf( code , 16 );  
            byte[] bb = new byte[ 4 ] ;  
            bb[ 0 ] = (byte) ((i >> 8) & 0xFF );  
            bb[ 1 ] = (byte) ( i & 0xFF ) ;  
            ByteBuffer b = ByteBuffer.wrap(bb);  
            sb.append( String.valueOf( set.decode(b) ).trim() );  
            start = m.end() ;  
        }  
        start2 = str.length() ;  
        if( start2 > start ){  
            String seg = str.substring(start, start2) ;  
            sb.append( seg );  
        }  
        return sb.toString() ;  
    }
	
	/**
	 * 是否大于0
	 * @param num
	 * @return
	 */
	public static boolean isGreaterThanZero(Integer num){
		if(null != num && num > 0){
			return true;
		}
		
		return false;
	}
}
