package com.tuniu.gt.complaint.action;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;

import com.tuniu.gt.complaint.Constans;
import com.tuniu.gt.complaint.entity.AppointManagerEntity;
import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.entity.HandlerResult;
import com.tuniu.gt.complaint.entity.TimeBucketEntity;
import com.tuniu.gt.complaint.entity.TimePlanEntity;
import com.tuniu.gt.complaint.service.IComplaintCollectService;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.toolkit.CommonUtil;
import com.tuniu.gt.toolkit.MemcachesUtil;

/**
 * 排班系统一期
 * @author chenhaitao
 *
 */
@Service("complaint_action-shiftSystem_plan")
@Scope("prototype")
public class ShiftSystemAction extends FrmBaseAction<IComplaintService, ComplaintEntity> {

	private static final long serialVersionUID = 1L;
	
	//private static Logger logger = Logger.getLogger(ShiftSystemAction.class);
	
	private String start;//开始时间
	
	private String end;//结束时间

	private String perNum;//人均单数
	
	private String titNum;//比例
	
	private String  personname; //内容
	
	//private final static int perNum =4;//平均每小时4单
	
	private Integer  id;//表id
	public ShiftSystemAction() {
		setManageUrl("shiftSystem_plan");
	}
	
	@Autowired
	private IComplaintCollectService complaintCollectService;
	
	/**
	 * 打开排班主界面
	 */
	public String execute() {
		
		String flag="";
		String userName =user.getRealName();
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("type", AppointManagerEntity.TOURING_OFFICER);
		map.put("flag", 0);
		map.put("userName", userName);
		List<Map<String, Object>>  list = complaintCollectService.queryManagerList(map);
		if(list.size()>0&&null!=list){
			
			flag ="0";
			
		}else{
			
			flag ="1";
		}
		
		request.setAttribute("flag", flag);
		return "shiftSystem_list";
	}
	
	/**
	 * 保存配置文件
	 * @return
	 */
	public String saveDeploy(){
		
		HandlerResult result =new HandlerResult();
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("perNum", perNum);
		map.put("titNum", titNum);
		String flag= complaintCollectService.saveDeploy(map);
		 if("0".equals(flag)){
		    	result.setRetObj("success");
		   }else{
		    	
		    	result.setRetObj("error");
		   }
		JSONObject json =JSONObject.fromObject(result);
		CommonUtil.writeResponse(json);
		return "shiftSystem_list";
		
		
	}
	
	@SuppressWarnings("unchecked")
	public String queryPlan() throws ParseException
	{
		    int perNumber =4;
		    double titNumber = 1; 
		    Map<String,Object> permap = complaintCollectService.queryDeploy();
		    if(null!=permap&&!"".equals(permap)){
		    	
		    	if(!"".equals(permap.get("perNum").toString())){
		    	
		    	  perNumber  = Integer.parseInt(permap.get("perNum").toString());
		    	}
		    	if(!"".equals(permap.get("titNum").toString())){
		    	
		    	 titNumber = Double.parseDouble(permap.get("titNum").toString());
		    }
		    }
		    String month =start.substring(5,7);
		    SimpleDateFormat formatM = new SimpleDateFormat("MM-dd"); 
		    Calendar c1= Calendar.getInstance();  
		    c1.set(Calendar.MONTH, Integer.parseInt(month));
		    c1.set(Calendar.DAY_OF_MONTH, 1);
		    c1.add(Calendar.DAY_OF_MONTH, -1);
	        //获取当前月最后一天
	        String last = formatM.format(c1.getTime());
	         //当月第一天
	        c1.set(Calendar.DAY_OF_MONTH, 1);
	        String first = formatM.format(c1.getTime());
		    
	        Calendar c = Calendar.getInstance();  
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy");
		    Date date = sdf.parse(start.substring(0,10));
	        c.setTime(date);   //设置当前日期  
	        c.add(Calendar.YEAR, -1); // 减一年
	        date = c.getTime();  
	  	    String year1 = sdf.format(date);	
	  	    c.add(Calendar.YEAR, -1); //
	  	    date = c.getTime();  
		    String year2 = sdf.format(date);	
		    c.add(Calendar.YEAR, -1); //
	  	    date = c.getTime();  
		    String year3 = sdf.format(date);	
		    //前一年
		    String startDate1 = year1 + "-"+ first;
		    String endDate1 = year1 + "-" +last;
		    //前两年
		    String startDate2 = year2 + "-"+ first;
		    String endDate2 =year2 + "-" +last;
		    //前三年
		    String startDate3 = year3 + "-"+ first;
		    String endDate3 = year3 + "-" +last;
		
		    Map<String, Object> map =new HashMap<String, Object>();
		    map.put("startDate1", startDate1);
		    map.put("endDate1", endDate1);
		    map.put("startDate2", startDate2);
		    map.put("endDate2", endDate2);
		    map.put("startDate3", startDate3);
		    map.put("endDate3", endDate3);
		    map.put("order_state",Constans.ORDER_STATE );
		    List<Map<String, Object>> historyDataList =new ArrayList<Map<String,Object>>();
		    //获取前三年某月投诉单数据量
		    String  monthAll = start.substring(0,7);
		    if(null!= MemcachesUtil.getObj("historyData"+monthAll)&&!"".equals(MemcachesUtil.getObj("historyData"+monthAll))){
		    	
		    	historyDataList  =  (List<Map<String, Object>>) MemcachesUtil.getObj("historyData"+monthAll);
		    	
		    }else{
		    	
		    	 historyDataList = complaintCollectService.queryhistoryData(map);
				 MemcachesUtil.set("historyData" + monthAll, historyDataList);
		    	
		    }
		    end = end.substring(0,10).concat(" 07:00:00");
		  
		    List<Map<String, Object>> list =new ArrayList<Map<String,Object>>();
		    if(null!= MemcachesUtil.getObj("historyDetail"+monthAll+"-"+perNumber+"-"+titNumber)&&!"".equals(MemcachesUtil.getObj("historyDetail"+monthAll+"-"+perNumber+"-"+titNumber))){
				
		    	//list = (List<Map<String, Object>>) MemcachesUtil.getObj("historyDetail"+monthAll+"-"+perNumber+"-"+titNumber);
		    	  list = complaintCollectService.queryCalendar(start, end);
		    	
			}else{
		        //先删除某个月全部排班数据
				complaintCollectService.deleteDetail(monthAll);
			    List<TimeBucketEntity> countList = getEveryDayHourCount(historyDataList);//封装某月某日每小时订单量
			    int  zaoban =0;
			    int  zhongban1 =0;
			    int  zhongban2 =0;
			    int  wanban =0;
			    int  yeban =0;
			    Calendar c2 = Calendar.getInstance();  
			    for(int i=0 ;i<countList.size();i++){
			    	
			    	TimePlanEntity plan =new TimePlanEntity();
			    	String day = countList.get(i).getDay();
			    	String nowDate = c2.get(Calendar.YEAR) + "-" + month + "-" + day;
			    	double bucket1 =countList.get(i).getBucket1() ;
			    	double bucket2 =countList.get(i).getBucket2() ;
			    	double bucket3 =countList.get(i).getBucket3() ;
			    	double bucket4 =countList.get(i).getBucket4() ;
			    	double bucket5 =countList.get(i).getBucket5() ;
			    	double bucket6 =countList.get(i).getBucket6() ;
			    	double bucket7 =countList.get(i).getBucket7() ;
			    	double bucket8 =countList.get(i).getBucket8() ;
			    	double bucket9 =countList.get(i).getBucket9() ;
			        zaoban = (int) Math.ceil((((bucket2/perNumber/3/2)+(bucket3/perNumber/3)+(bucket4/perNumber/3/4/2)+(bucket5/perNumber/3/3/2)+(bucket6/perNumber/3/4))/5)*titNumber);
			        zhongban1 = (int) Math.ceil((((bucket4/perNumber/3/4/2)+(bucket5/perNumber/3/3/2)+(bucket6/perNumber/3/4)+(bucket7/perNumber/3/3/2))/4)*titNumber) ;
			        zhongban2 = (int) Math.ceil((((bucket5/perNumber/3/3/2)+(bucket6/perNumber/3/4)+(bucket7/perNumber/3/3/2)+(bucket8/perNumber/4/3/2))/4)*titNumber);
			        wanban  = (int) Math.ceil((((bucket6/perNumber/3/4)+(bucket7/perNumber/3/3/2)+(bucket8/perNumber/4/3/2)+(bucket9/perNumber/3/2))/4)*titNumber);
			        yeban  = (int) Math.ceil((((bucket2/perNumber/3/2)+(bucket1/perNumber/7))/2)*titNumber);
			        plan.setDay(nowDate);
			        plan.setZaoban(zaoban);
			        plan.setZhongban1(zhongban1);
			        plan.setZhongban2(zhongban2);
			        plan.setWanban(wanban);
			        plan.setYeban(yeban);
			        plan.setMonth(monthAll);
			        //插入排班数据
			        complaintCollectService.saveDetailPlan(plan);
			    }
			     
			       list = complaintCollectService.queryCalendar(start, end);
			       MemcachesUtil.set("historyDetail" + monthAll+"-"+perNumber+"-"+titNumber, list);
			}
			JSONArray json =JSONArray.fromObject(list);
			// 设置字符集
			CommonUtil.writeResponse(json);
		    return "shiftSystem_list";
		}

 
	public String savePlan(){
		
	    HandlerResult result =new HandlerResult();
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("id", id);
		map.put("personname", personname);
		
		String returnType =complaintCollectService.savePlan(map);
		// 设置字符集
		result.setRetObj(returnType);
		JSONObject json =JSONObject.fromObject(result);
		CommonUtil.writeResponse(json);
		return "shiftSystem_list";
	}
	

	
	/**
	 * 获取一个月每天每时的订单量
	 * @param historyDataList
	 * @return
	 */
	private  List<TimeBucketEntity> getEveryDayHourCount( List<Map<String, Object>> historyDataList){
		
		  TimeBucketEntity bucket1 =new TimeBucketEntity();
		  TimeBucketEntity bucket2 =new TimeBucketEntity();
		  TimeBucketEntity bucket3 =new TimeBucketEntity();
		  TimeBucketEntity bucket4 =new TimeBucketEntity();
		  TimeBucketEntity bucket5 =new TimeBucketEntity();
		  TimeBucketEntity bucket6 =new TimeBucketEntity();
		  TimeBucketEntity bucket7 =new TimeBucketEntity();
		  TimeBucketEntity bucket8 =new TimeBucketEntity();
		  TimeBucketEntity bucket9 =new TimeBucketEntity();
		  TimeBucketEntity bucket10 =new TimeBucketEntity();
		  TimeBucketEntity bucket11 =new TimeBucketEntity();
		  TimeBucketEntity bucket12 =new TimeBucketEntity();
		  TimeBucketEntity bucket13 =new TimeBucketEntity();
		  TimeBucketEntity bucket14 =new TimeBucketEntity();
		  TimeBucketEntity bucket15 =new TimeBucketEntity();
		  TimeBucketEntity bucket16 =new TimeBucketEntity();
		  TimeBucketEntity bucket17 =new TimeBucketEntity();
		  TimeBucketEntity bucket18 =new TimeBucketEntity();
		  TimeBucketEntity bucket19 =new TimeBucketEntity();
		  TimeBucketEntity bucket20 =new TimeBucketEntity();
		  TimeBucketEntity bucket21 =new TimeBucketEntity();
		  TimeBucketEntity bucket22 =new TimeBucketEntity();
		  TimeBucketEntity bucket23 =new TimeBucketEntity();
		  TimeBucketEntity bucket24 =new TimeBucketEntity();
		  TimeBucketEntity bucket25 =new TimeBucketEntity();
		  TimeBucketEntity bucket26 =new TimeBucketEntity();
		  TimeBucketEntity bucket27 =new TimeBucketEntity();
		  TimeBucketEntity bucket28 =new TimeBucketEntity();
		  TimeBucketEntity bucket29 =new TimeBucketEntity();
		  TimeBucketEntity bucket30 =new TimeBucketEntity();
		  TimeBucketEntity bucket31 =new TimeBucketEntity();
		   //1号
		   int count_8_12_1 =0;
		   int count_12_14_1 =0;
		   int count_15_17_1 =0;
		   int count_17_21_1 =0;
		   int count_21_23_1 =0;
		   int count_23_06_1 =0;
		   //2号
		   int count_8_12_2 =0;
		   int count_12_14_2 =0;
		   int count_15_17_2 =0;
		   int count_17_21_2 =0;
		   int count_21_23_2 =0;
		   int count_23_06_2 =0;
		   //3号
		   int count_8_12_3 =0;
		   int count_12_14_3 =0;
		   int count_15_17_3 =0;
		   int count_17_21_3 =0;
		   int count_21_23_3 =0;
		   int count_23_06_3 =0;
		   //4号
		   int count_8_12_4 =0;
		   int count_12_14_4 =0;
		   int count_15_17_4 =0;
		   int count_17_21_4 =0;
		   int count_21_23_4 =0;
		   int count_23_06_4 =0;
		   //5号
		   int count_8_12_5 =0;
		   int count_12_14_5 =0;
		   int count_15_17_5 =0;
		   int count_17_21_5 =0;
		   int count_21_23_5 =0;
		   int count_23_06_5 =0;
		   //6号
		   int count_8_12_6 =0;
		   int count_12_14_6 =0;
		   int count_15_17_6 =0;
		   int count_17_21_6 =0;
		   int count_21_23_6 =0;
		   int count_23_06_6 =0;
		   //7号
		   int count_8_12_7 =0;
		   int count_12_14_7 =0;
		   int count_15_17_7 =0;
		   int count_17_21_7 =0;
		   int count_21_23_7 =0;
		   int count_23_06_7 =0;
		   //8号
		   int count_8_12_8 =0;
		   int count_12_14_8 =0;
		   int count_15_17_8 =0;
		   int count_17_21_8 =0;
		   int count_21_23_8 =0;
		   int count_23_06_8 =0;
		   //9号
		   int count_8_12_9 =0;
		   int count_12_14_9 =0;
		   int count_15_17_9 =0;
		   int count_17_21_9 =0;
		   int count_21_23_9 =0;
		   int count_23_06_9 =0;
		   //10号
		   int count_8_12_10 =0;
		   int count_12_14_10 =0;
		   int count_15_17_10 =0;
		   int count_17_21_10=0;
		   int count_21_23_10 =0;
		   int count_23_06_10 =0;
		   //11号
		   int count_8_12_11 =0;
		   int count_12_14_11 =0;
		   int count_15_17_11 =0;
		   int count_17_21_11 =0;
		   int count_21_23_11 =0;
		   int count_23_06_11 =0;
		   //12号
		   int count_8_12_12 =0;
		   int count_12_14_12 =0;
		   int count_15_17_12 =0;
		   int count_17_21_12 =0;
		   int count_21_23_12 =0;
		   int count_23_06_12 =0;
		   //13号
		   int count_8_12_13 =0;
		   int count_12_14_13 =0;
		   int count_15_17_13=0;
		   int count_17_21_13 =0;
		   int count_21_23_13 =0;
		   int count_23_06_13 =0; 
		   //14号
		   int count_8_12_14 =0;
		   int count_12_14_14 =0;
		   int count_15_17_14 =0;
		   int count_17_21_14 =0;
		   int count_21_23_14 =0;
		   int count_23_06_14 =0;
		   //15号
		   int count_8_12_15 =0;
		   int count_12_14_15 =0;
		   int count_15_17_15 =0;
		   int count_17_21_15 =0;
		   int count_21_23_15 =0;
		   int count_23_06_15 =0;
		   //16号
		   int count_8_12_16 =0;
		   int count_12_14_16 =0;
		   int count_15_17_16 =0;
		   int count_17_21_16 =0;
		   int count_21_23_16 =0;
		   int count_23_06_16 =0;
		   //17号
		   int count_8_12_17 =0;
		   int count_12_14_17 =0;
		   int count_15_17_17 =0;
		   int count_17_21_17 =0;
		   int count_21_23_17 =0;
		   int count_23_06_17 =0;
		   //18号
		   int count_8_12_18 =0;
		   int count_12_14_18 =0;
		   int count_15_17_18 =0;
		   int count_17_21_18 =0;
		   int count_21_23_18 =0;
		   int count_23_06_18 =0;
		   //19号
		   int count_8_12_19 =0;
		   int count_12_14_19 =0;
		   int count_15_17_19 =0;
		   int count_17_21_19 =0;
		   int count_21_23_19 =0;
		   int count_23_06_19 =0;
		   //20号
		   int count_8_12_20 =0;
		   int count_12_14_20 =0;
		   int count_15_17_20 =0;
		   int count_17_21_20 =0;
		   int count_21_23_20 =0;
		   int count_23_06_20 =0;
		   //21号
		   int count_8_12_21 =0;
		   int count_12_14_21 =0;
		   int count_15_17_21 =0;
		   int count_17_21_21 =0;
		   int count_21_23_21 =0;
		   int count_23_06_21 =0;
		   //22号
		   int count_8_12_22 =0;
		   int count_12_14_22 =0;
		   int count_15_17_22 =0;
		   int count_17_21_22 =0;
		   int count_21_23_22 =0;
		   int count_23_06_22 =0;
		   //23号
		   int count_8_12_23 =0;
		   int count_12_14_23 =0;
		   int count_15_17_23 =0;
		   int count_17_21_23 =0;
		   int count_21_23_23 =0;
		   int count_23_06_23 =0;
		   //24号
		   int count_8_12_24 =0;
		   int count_12_14_24 =0;
		   int count_15_17_24 =0;
		   int count_17_21_24 =0;
		   int count_21_23_24 =0;
		   int count_23_06_24 =0;
		   //25号
		   int count_8_12_25 =0;
		   int count_12_14_25 =0;
		   int count_15_17_25 =0;
		   int count_17_21_25 =0;
		   int count_21_23_25 =0;
		   int count_23_06_25 =0;
		   //26号
		   int count_8_12_26 =0;
		   int count_12_14_26 =0;
		   int count_15_17_26 =0;
		   int count_17_21_26 =0;
		   int count_21_23_26 =0;
		   int count_23_06_26 =0;
		   //27号
		   int count_8_12_27 =0;
		   int count_12_14_27 =0;
		   int count_15_17_27 =0;
		   int count_17_21_27 =0;
		   int count_21_23_27 =0;
		   int count_23_06_27 =0;
		   //28号
		   int count_8_12_28 =0;
		   int count_12_14_28 =0;
		   int count_15_17_28 =0;
		   int count_17_21_28 =0;
		   int count_21_23_28 =0;
		   int count_23_06_28 =0;
		   //29号
		   int count_8_12_29 =0;
		   int count_12_14_29 =0;
		   int count_15_17_29 =0;
		   int count_17_21_29 =0;
		   int count_21_23_29 =0;
		   int count_23_06_29 =0;
		   //30号
		   int count_8_12_30 =0;
		   int count_12_14_30  =0;
		   int count_15_17_30  =0;
		   int count_17_21_30  =0;
		   int count_21_23_30  =0;
		   int count_23_06_30  =0;
		   //31号
		   int count_8_12_31 =0;
		   int count_12_14_31 =0;
		   int count_15_17_31 =0;
		   int count_17_21_31 =0;
		   int count_21_23_31 =0;
		   int count_23_06_31 =0;
		   
		  List<TimeBucketEntity> list =new ArrayList<TimeBucketEntity>();
		  for(int i=0;i<historyDataList.size();i++){
			
			Map<String , Object> map =  historyDataList.get(i);
			
			 if(Constans.TIME_DAY01.equals(map.get("compEday"))){
				 
			 
			   if("06".equals(map.get("compHour"))){//早6-7
				  	
				   bucket1.setBucket2(Integer.parseInt(map.get("compNum").toString()));
				}
			   if("07".equals(map.get("compHour"))){//早7-8
				  	
				   bucket1.setBucket3(Integer.parseInt(map.get("compNum").toString()));
				}
			   if("08".equals(map.get("compHour"))||"09".equals(map.get("compHour"))||"10".equals(map.get("compHour"))||"11".equals(map.get("compHour"))){//早8-12
				  	
				    count_8_12_1 +=Integer.parseInt(map.get("compNum").toString());
				    
				}
			   if("12".equals(map.get("compHour"))||"13".equals(map.get("compHour"))){//早12-14
				  	
				   count_12_14_1 += Integer.parseInt(map.get("compNum").toString());
				   
				}
			   if("14".equals(map.get("compHour"))){//早14-15
				  	
				   bucket1.setBucket6(Integer.parseInt(map.get("compNum").toString()));
				}
				
				if("15".equals(map.get("compHour"))||"16".equals(map.get("compHour"))){//早15-17
				  	
					count_15_17_1 += Integer.parseInt(map.get("compNum").toString());
					
				}
				if("17".equals(map.get("compHour"))||"18".equals(map.get("compHour"))||"19".equals(map.get("compHour"))||"20".equals(map.get("compHour"))){//早17-21
				  	
					count_17_21_1 += Integer.parseInt(map.get("compNum").toString());
				}
				if("21".equals(map.get("compHour"))||"22".equals(map.get("compHour"))){//早21-23
				  	
					count_21_23_1 += Integer.parseInt(map.get("compNum").toString());
				}	
				if("23".equals(map.get("compHour"))||"00".equals(map.get("compHour"))||"01".equals(map.get("compHour"))||"02".equals(map.get("compHour"))||"03".equals(map.get("compHour"))||"04".equals(map.get("compHour"))||"05".equals(map.get("compHour"))){//晚23-早6 
				  	
					count_23_06_1 += Integer.parseInt(map.get("compNum").toString());
				}	
				
				bucket1.setBucket4(count_8_12_1);
				bucket1.setBucket5(count_12_14_1);
				bucket1.setBucket7(count_15_17_1);
				bucket1.setBucket8(count_17_21_1);
				bucket1.setBucket9(count_21_23_1);
				bucket1.setBucket1(count_23_06_1);
				bucket1.setDay(Constans.TIME_DAY01);
				
			}else if(Constans.TIME_DAY02.equals(map.get("compEday"))){
				
				   if("06".equals(map.get("compHour"))){//早6-7
					  	
					   bucket2.setBucket2(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("07".equals(map.get("compHour"))){//早7-8
					  	
					   bucket2.setBucket3(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("08".equals(map.get("compHour"))||"09".equals(map.get("compHour"))||"10".equals(map.get("compHour"))||"11".equals(map.get("compHour"))){//早8-12
					  	
					    count_8_12_2 +=Integer.parseInt(map.get("compNum").toString());
					    
					}
				   if("12".equals(map.get("compHour"))||"13".equals(map.get("compHour"))){//早12-14
					  	
					   count_12_14_2 += Integer.parseInt(map.get("compNum").toString());
					   
					}
				   if("14".equals(map.get("compHour"))){//早14-15
					  	
					   bucket2.setBucket6(Integer.parseInt(map.get("compNum").toString()));
					}
					
					if("15".equals(map.get("compHour"))||"16".equals(map.get("compHour"))){//早15-17
					  	
						count_15_17_2 += Integer.parseInt(map.get("compNum").toString());
						
					}
					if("17".equals(map.get("compHour"))||"18".equals(map.get("compHour"))||"19".equals(map.get("compHour"))||"20".equals(map.get("compHour"))){//早17-21
					  	
						count_17_21_2 += Integer.parseInt(map.get("compNum").toString());
					}
					if("21".equals(map.get("compHour"))||"22".equals(map.get("compHour"))){//早21-23
					  	
						count_21_23_2 += Integer.parseInt(map.get("compNum").toString());
					}	
					if("23".equals(map.get("compHour"))||"00".equals(map.get("compHour"))||"01".equals(map.get("compHour"))||"02".equals(map.get("compHour"))||"03".equals(map.get("compHour"))||"04".equals(map.get("compHour"))||"05".equals(map.get("compHour"))){//晚23-早6 
					  	
						count_23_06_2 += Integer.parseInt(map.get("compNum").toString());
					}	
					
					bucket2.setBucket4(count_8_12_2);
					bucket2.setBucket5(count_12_14_2);
					bucket2.setBucket7(count_15_17_2);
					bucket2.setBucket8(count_17_21_2);
					bucket2.setBucket9(count_21_23_2);
					bucket2.setBucket1(count_23_06_2);
					bucket2.setDay(Constans.TIME_DAY02);
				
			}else if(Constans.TIME_DAY03.equals(map.get("compEday"))){
				
				   if("06".equals(map.get("compHour"))){//早6-7
					  	
					   bucket3.setBucket2(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("07".equals(map.get("compHour"))){//早7-8
					  	
					   bucket3.setBucket3(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("08".equals(map.get("compHour"))||"09".equals(map.get("compHour"))||"10".equals(map.get("compHour"))||"11".equals(map.get("compHour"))){//早8-12
					  	
					    count_8_12_3 +=Integer.parseInt(map.get("compNum").toString());
					    
					}
				   if("12".equals(map.get("compHour"))||"13".equals(map.get("compHour"))){//早12-14
					  	
					   count_12_14_3 += Integer.parseInt(map.get("compNum").toString());
					   
					}
				   if("14".equals(map.get("compHour"))){//早14-15
					  	
					   bucket3.setBucket6(Integer.parseInt(map.get("compNum").toString()));
					}
					
					if("15".equals(map.get("compHour"))||"16".equals(map.get("compHour"))){//早15-17
					  	
						count_15_17_3 += Integer.parseInt(map.get("compNum").toString());
						
					}
					if("17".equals(map.get("compHour"))||"18".equals(map.get("compHour"))||"19".equals(map.get("compHour"))||"20".equals(map.get("compHour"))){//早17-21
					  	
						count_17_21_3 += Integer.parseInt(map.get("compNum").toString());
					}
					if("21".equals(map.get("compHour"))||"22".equals(map.get("compHour"))){//早21-23
					  	
						count_21_23_3 += Integer.parseInt(map.get("compNum").toString());
					}	
					if("23".equals(map.get("compHour"))||"00".equals(map.get("compHour"))||"01".equals(map.get("compHour"))||"02".equals(map.get("compHour"))||"03".equals(map.get("compHour"))||"04".equals(map.get("compHour"))||"05".equals(map.get("compHour"))){//晚23-早6 
					  	
						count_23_06_3 += Integer.parseInt(map.get("compNum").toString());
					}	
					
					bucket3.setBucket4(count_8_12_3);
					bucket3.setBucket5(count_12_14_3);
					bucket3.setBucket7(count_15_17_3);
					bucket3.setBucket8(count_17_21_3);
					bucket3.setBucket9(count_21_23_3);
					bucket3.setBucket1(count_23_06_3);
					bucket3.setDay(Constans.TIME_DAY03);
				
			}else if(Constans.TIME_DAY04.equals(map.get("compEday"))){
				
				   if("06".equals(map.get("compHour"))){//早6-7
					  	
					   bucket4.setBucket2(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("07".equals(map.get("compHour"))){//早7-8
					  	
					   bucket4.setBucket3(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("08".equals(map.get("compHour"))||"09".equals(map.get("compHour"))||"10".equals(map.get("compHour"))||"11".equals(map.get("compHour"))){//早8-12
					  	
					    count_8_12_4 +=Integer.parseInt(map.get("compNum").toString());
					    
					}
				   if("12".equals(map.get("compHour"))||"13".equals(map.get("compHour"))){//早12-14
					  	
					   count_12_14_4 += Integer.parseInt(map.get("compNum").toString());
					   
					}
				   if("14".equals(map.get("compHour"))){//早14-15
					  	
					   bucket4.setBucket6(Integer.parseInt(map.get("compNum").toString()));
					}
					
					if("15".equals(map.get("compHour"))||"16".equals(map.get("compHour"))){//早15-17
					  	
						count_15_17_4 += Integer.parseInt(map.get("compNum").toString());
						
					}
					if("17".equals(map.get("compHour"))||"18".equals(map.get("compHour"))||"19".equals(map.get("compHour"))||"20".equals(map.get("compHour"))){//早17-21
					  	
						count_17_21_4 += Integer.parseInt(map.get("compNum").toString());
					}
					if("21".equals(map.get("compHour"))||"22".equals(map.get("compHour"))){//早21-23
					  	
						count_21_23_4 += Integer.parseInt(map.get("compNum").toString());
					}	
					if("23".equals(map.get("compHour"))||"00".equals(map.get("compHour"))||"01".equals(map.get("compHour"))||"02".equals(map.get("compHour"))||"03".equals(map.get("compHour"))||"04".equals(map.get("compHour"))||"05".equals(map.get("compHour"))){//晚23-早6 
					  	
						count_23_06_4 += Integer.parseInt(map.get("compNum").toString());
					}	
					
					bucket4.setBucket4(count_8_12_4);
					bucket4.setBucket5(count_12_14_4);
					bucket4.setBucket7(count_15_17_4);
					bucket4.setBucket8(count_17_21_4);
					bucket4.setBucket9(count_21_23_4);
					bucket4.setBucket1(count_23_06_4);
					bucket4.setDay(Constans.TIME_DAY04);
					
			}else if(Constans.TIME_DAY05.equals(map.get("compEday"))){
				
				   if("06".equals(map.get("compHour"))){//早6-7
					  	
					   bucket5.setBucket2(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("07".equals(map.get("compHour"))){//早7-8
					  	
					   bucket5.setBucket3(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("08".equals(map.get("compHour"))||"09".equals(map.get("compHour"))||"10".equals(map.get("compHour"))||"11".equals(map.get("compHour"))){//早8-12
					  	
					    count_8_12_5 +=Integer.parseInt(map.get("compNum").toString());
					    
					}
				   if("12".equals(map.get("compHour"))||"13".equals(map.get("compHour"))){//早12-14
					  	
					   count_12_14_5 += Integer.parseInt(map.get("compNum").toString());
					   
					}
				   if("14".equals(map.get("compHour"))){//早14-15
					  	
					   bucket5.setBucket6(Integer.parseInt(map.get("compNum").toString()));
					}
					
					if("15".equals(map.get("compHour"))||"16".equals(map.get("compHour"))){//早15-17
					  	
						count_15_17_5 += Integer.parseInt(map.get("compNum").toString());
						
					}
					if("17".equals(map.get("compHour"))||"18".equals(map.get("compHour"))||"19".equals(map.get("compHour"))||"20".equals(map.get("compHour"))){//早17-21
					  	
						count_17_21_5 += Integer.parseInt(map.get("compNum").toString());
					}
					if("21".equals(map.get("compHour"))||"22".equals(map.get("compHour"))){//早21-23
					  	
						count_21_23_5 += Integer.parseInt(map.get("compNum").toString());
					}	
					if("23".equals(map.get("compHour"))||"00".equals(map.get("compHour"))||"01".equals(map.get("compHour"))||"02".equals(map.get("compHour"))||"03".equals(map.get("compHour"))||"04".equals(map.get("compHour"))||"05".equals(map.get("compHour"))){//晚23-早6 
					  	
						count_23_06_5 += Integer.parseInt(map.get("compNum").toString());
					}	
					
					bucket5.setBucket4(count_8_12_5);
					bucket5.setBucket5(count_12_14_5);
					bucket5.setBucket7(count_15_17_5);
					bucket5.setBucket8(count_17_21_5);
					bucket5.setBucket9(count_21_23_5);
					bucket5.setBucket1(count_23_06_5);
					bucket5.setDay(Constans.TIME_DAY05);
			}else if(Constans.TIME_DAY06.equals(map.get("compEday"))){
				
				   if("06".equals(map.get("compHour"))){//早6-7
					  	
					   bucket6.setBucket2(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("07".equals(map.get("compHour"))){//早7-8
					  	
					   bucket6.setBucket3(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("08".equals(map.get("compHour"))||"09".equals(map.get("compHour"))||"10".equals(map.get("compHour"))||"11".equals(map.get("compHour"))){//早8-12
					  	
					    count_8_12_6 +=Integer.parseInt(map.get("compNum").toString());
					    
					}
				   if("12".equals(map.get("compHour"))||"13".equals(map.get("compHour"))){//早12-14
					  	
					   count_12_14_6 += Integer.parseInt(map.get("compNum").toString());
					   
					}
				   if("14".equals(map.get("compHour"))){//早14-15
					  	
					   bucket6.setBucket6(Integer.parseInt(map.get("compNum").toString()));
					}
					
					if("15".equals(map.get("compHour"))||"16".equals(map.get("compHour"))){//早15-17
					  	
						count_15_17_6 += Integer.parseInt(map.get("compNum").toString());
						
					}
					if("17".equals(map.get("compHour"))||"18".equals(map.get("compHour"))||"19".equals(map.get("compHour"))||"20".equals(map.get("compHour"))){//早17-21
					  	
						count_17_21_6 += Integer.parseInt(map.get("compNum").toString());
					}
					if("21".equals(map.get("compHour"))||"22".equals(map.get("compHour"))){//早21-23
					  	
						count_21_23_6 += Integer.parseInt(map.get("compNum").toString());
					}	
					if("23".equals(map.get("compHour"))||"00".equals(map.get("compHour"))||"01".equals(map.get("compHour"))||"02".equals(map.get("compHour"))||"03".equals(map.get("compHour"))||"04".equals(map.get("compHour"))||"05".equals(map.get("compHour"))){//晚23-早6 
					  	
						count_23_06_6 += Integer.parseInt(map.get("compNum").toString());
					}	
					
					bucket6.setBucket4(count_8_12_6);
					bucket6.setBucket5(count_12_14_6);
					bucket6.setBucket7(count_15_17_6);
					bucket6.setBucket8(count_17_21_6);
					bucket6.setBucket9(count_21_23_6);
					bucket6.setBucket1(count_23_06_6);
					bucket6.setDay(Constans.TIME_DAY06);
			}else if(Constans.TIME_DAY07.equals(map.get("compEday"))){
				
				
				   if("06".equals(map.get("compHour"))){//早6-7
					  	
					   bucket7.setBucket2(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("07".equals(map.get("compHour"))){//早7-8
					  	
					   bucket7.setBucket3(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("08".equals(map.get("compHour"))||"09".equals(map.get("compHour"))||"10".equals(map.get("compHour"))||"11".equals(map.get("compHour"))){//早8-12
					  	
					    count_8_12_7 +=Integer.parseInt(map.get("compNum").toString());
					    
					}
				   if("12".equals(map.get("compHour"))||"13".equals(map.get("compHour"))){//早12-14
					  	
					   count_12_14_7 += Integer.parseInt(map.get("compNum").toString());
					   
					}
				   if("14".equals(map.get("compHour"))){//早14-15
					  	
					   bucket7.setBucket6(Integer.parseInt(map.get("compNum").toString()));
					}
					
					if("15".equals(map.get("compHour"))||"16".equals(map.get("compHour"))){//早15-17
					  	
						count_15_17_7 += Integer.parseInt(map.get("compNum").toString());
						
					}
					if("17".equals(map.get("compHour"))||"18".equals(map.get("compHour"))||"19".equals(map.get("compHour"))||"20".equals(map.get("compHour"))){//早17-21
					  	
						count_17_21_7 += Integer.parseInt(map.get("compNum").toString());
					}
					if("21".equals(map.get("compHour"))||"22".equals(map.get("compHour"))){//早21-23
					  	
						count_21_23_7 += Integer.parseInt(map.get("compNum").toString());
					}	
					if("23".equals(map.get("compHour"))||"00".equals(map.get("compHour"))||"01".equals(map.get("compHour"))||"02".equals(map.get("compHour"))||"03".equals(map.get("compHour"))||"04".equals(map.get("compHour"))||"05".equals(map.get("compHour"))){//晚23-早6 
					  	
						count_23_06_7 += Integer.parseInt(map.get("compNum").toString());
					}	
					
					bucket7.setBucket4(count_8_12_7);
					bucket7.setBucket5(count_12_14_7);
					bucket7.setBucket7(count_15_17_7);
					bucket7.setBucket8(count_17_21_7);
					bucket7.setBucket9(count_21_23_7);
					bucket7.setBucket1(count_23_06_7);
					bucket7.setDay(Constans.TIME_DAY07);
			}else if(Constans.TIME_DAY08.equals(map.get("compEday"))){
				
				   if("06".equals(map.get("compHour"))){//早6-7
					  	
					   bucket8.setBucket2(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("07".equals(map.get("compHour"))){//早7-8
					  	
					   bucket8.setBucket3(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("08".equals(map.get("compHour"))||"09".equals(map.get("compHour"))||"10".equals(map.get("compHour"))||"11".equals(map.get("compHour"))){//早8-12
					  	
					    count_8_12_8 +=Integer.parseInt(map.get("compNum").toString());
					    
					}
				   if("12".equals(map.get("compHour"))||"13".equals(map.get("compHour"))){//早12-14
					  	
					   count_12_14_8 += Integer.parseInt(map.get("compNum").toString());
					   
					}
				   if("14".equals(map.get("compHour"))){//早14-15
					  	
					   bucket8.setBucket6(Integer.parseInt(map.get("compNum").toString()));
					}
					
					if("15".equals(map.get("compHour"))||"16".equals(map.get("compHour"))){//早15-17
					  	
						count_15_17_8 += Integer.parseInt(map.get("compNum").toString());
						
					}
					if("17".equals(map.get("compHour"))||"18".equals(map.get("compHour"))||"19".equals(map.get("compHour"))||"20".equals(map.get("compHour"))){//早17-21
					  	
						count_17_21_8 += Integer.parseInt(map.get("compNum").toString());
					}
					if("21".equals(map.get("compHour"))||"22".equals(map.get("compHour"))){//早21-23
					  	
						count_21_23_8 += Integer.parseInt(map.get("compNum").toString());
					}	
					if("23".equals(map.get("compHour"))||"00".equals(map.get("compHour"))||"01".equals(map.get("compHour"))||"02".equals(map.get("compHour"))||"03".equals(map.get("compHour"))||"04".equals(map.get("compHour"))||"05".equals(map.get("compHour"))){//晚23-早6 
					  	
						count_23_06_8 += Integer.parseInt(map.get("compNum").toString());
					}	
					
					bucket8.setBucket4(count_8_12_8);
					bucket8.setBucket5(count_12_14_8);
					bucket8.setBucket7(count_15_17_8);
					bucket8.setBucket8(count_17_21_8);
					bucket8.setBucket9(count_21_23_8);
					bucket8.setBucket1(count_23_06_8);
					bucket8.setDay(Constans.TIME_DAY08);
			}else if(Constans.TIME_DAY09.equals(map.get("compEday"))){
				
				   if("06".equals(map.get("compHour"))){//早6-7
					  	
					   bucket9.setBucket2(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("07".equals(map.get("compHour"))){//早7-8
					  	
					   bucket9.setBucket3(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("08".equals(map.get("compHour"))||"09".equals(map.get("compHour"))||"10".equals(map.get("compHour"))||"11".equals(map.get("compHour"))){//早8-12
					  	
					    count_8_12_9 +=Integer.parseInt(map.get("compNum").toString());
					    
					}
				   if("12".equals(map.get("compHour"))||"13".equals(map.get("compHour"))){//早12-14
					  	
					   count_12_14_9 += Integer.parseInt(map.get("compNum").toString());
					   
					}
				   if("14".equals(map.get("compHour"))){//早14-15
					  	
					   bucket9.setBucket6(Integer.parseInt(map.get("compNum").toString()));
					}
					
					if("15".equals(map.get("compHour"))||"16".equals(map.get("compHour"))){//早15-17
					  	
						count_15_17_9 += Integer.parseInt(map.get("compNum").toString());
						
					}
					if("17".equals(map.get("compHour"))||"18".equals(map.get("compHour"))||"19".equals(map.get("compHour"))||"20".equals(map.get("compHour"))){//早17-21
					  	
						count_17_21_9 += Integer.parseInt(map.get("compNum").toString());
					}
					if("21".equals(map.get("compHour"))||"22".equals(map.get("compHour"))){//早21-23
					  	
						count_21_23_9 += Integer.parseInt(map.get("compNum").toString());
					}	
					if("23".equals(map.get("compHour"))||"00".equals(map.get("compHour"))||"01".equals(map.get("compHour"))||"02".equals(map.get("compHour"))||"03".equals(map.get("compHour"))||"04".equals(map.get("compHour"))||"05".equals(map.get("compHour"))){//晚23-早6 
					  	
						count_23_06_9 += Integer.parseInt(map.get("compNum").toString());
					}	
					
					bucket9.setBucket4(count_8_12_9);
					bucket9.setBucket5(count_12_14_9);
					bucket9.setBucket7(count_15_17_9);
					bucket9.setBucket8(count_17_21_9);
					bucket9.setBucket9(count_21_23_9);
					bucket9.setBucket1(count_23_06_9);
					bucket9.setDay(Constans.TIME_DAY09);
			}else if(Constans.TIME_DAY10.equals(map.get("compEday"))){
				
				   if("06".equals(map.get("compHour"))){//早6-7
					  	
					   bucket10.setBucket2(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("07".equals(map.get("compHour"))){//早7-8
					  	
					   bucket10.setBucket3(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("08".equals(map.get("compHour"))||"09".equals(map.get("compHour"))||"10".equals(map.get("compHour"))||"11".equals(map.get("compHour"))){//早8-12
					  	
					    count_8_12_10 +=Integer.parseInt(map.get("compNum").toString());
					    
					}
				   if("12".equals(map.get("compHour"))||"13".equals(map.get("compHour"))){//早12-14
					  	
					   count_12_14_10 += Integer.parseInt(map.get("compNum").toString());
					   
					}
				   if("14".equals(map.get("compHour"))){//早14-15
					  	
					   bucket10.setBucket6(Integer.parseInt(map.get("compNum").toString()));
					}
					
					if("15".equals(map.get("compHour"))||"16".equals(map.get("compHour"))){//早15-17
					  	
						count_15_17_10 += Integer.parseInt(map.get("compNum").toString());
						
					}
					if("17".equals(map.get("compHour"))||"18".equals(map.get("compHour"))||"19".equals(map.get("compHour"))||"20".equals(map.get("compHour"))){//早17-21
					  	
						count_17_21_10 += Integer.parseInt(map.get("compNum").toString());
					}
					if("21".equals(map.get("compHour"))||"22".equals(map.get("compHour"))){//早21-23
					  	
						count_21_23_10 += Integer.parseInt(map.get("compNum").toString());
					}	
					if("23".equals(map.get("compHour"))||"00".equals(map.get("compHour"))||"01".equals(map.get("compHour"))||"02".equals(map.get("compHour"))||"03".equals(map.get("compHour"))||"04".equals(map.get("compHour"))||"05".equals(map.get("compHour"))){//晚23-早6 
					  	
						count_23_06_10 += Integer.parseInt(map.get("compNum").toString());
					}	
					
					bucket10.setBucket4(count_8_12_10);
					bucket10.setBucket5(count_12_14_10);
					bucket10.setBucket7(count_15_17_10);
					bucket10.setBucket8(count_17_21_10);
					bucket10.setBucket9(count_21_23_10);
					bucket10.setBucket1(count_23_06_10);
					bucket10.setDay(Constans.TIME_DAY10);
			}else if(Constans.TIME_DAY11.equals(map.get("compEday"))){
				
				   if("06".equals(map.get("compHour"))){//早6-7
					  	
					   bucket11.setBucket2(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("07".equals(map.get("compHour"))){//早7-8
					  	
					   bucket11.setBucket3(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("08".equals(map.get("compHour"))||"09".equals(map.get("compHour"))||"10".equals(map.get("compHour"))||"11".equals(map.get("compHour"))){//早8-12
					  	
					    count_8_12_11 +=Integer.parseInt(map.get("compNum").toString());
					    
					}
				   if("12".equals(map.get("compHour"))||"13".equals(map.get("compHour"))){//早12-14
					  	
					   count_12_14_11 += Integer.parseInt(map.get("compNum").toString());
					   
					}
				   if("14".equals(map.get("compHour"))){//早14-15
					  	
					   bucket11.setBucket6(Integer.parseInt(map.get("compNum").toString()));
					}
					
					if("15".equals(map.get("compHour"))||"16".equals(map.get("compHour"))){//早15-17
					  	
						count_15_17_11 += Integer.parseInt(map.get("compNum").toString());
						
					}
					if("17".equals(map.get("compHour"))||"18".equals(map.get("compHour"))||"19".equals(map.get("compHour"))||"20".equals(map.get("compHour"))){//早17-21
					  	
						count_17_21_11 += Integer.parseInt(map.get("compNum").toString());
					}
					if("21".equals(map.get("compHour"))||"22".equals(map.get("compHour"))){//早21-23
					  	
						count_21_23_11 += Integer.parseInt(map.get("compNum").toString());
					}	
					if("23".equals(map.get("compHour"))||"00".equals(map.get("compHour"))||"01".equals(map.get("compHour"))||"02".equals(map.get("compHour"))||"03".equals(map.get("compHour"))||"04".equals(map.get("compHour"))||"05".equals(map.get("compHour"))){//晚23-早6 
					  	
						count_23_06_11 += Integer.parseInt(map.get("compNum").toString());
					}	
					
					bucket11.setBucket4(count_8_12_11);
					bucket11.setBucket5(count_12_14_11);
					bucket11.setBucket7(count_15_17_11);
					bucket11.setBucket8(count_17_21_11);
					bucket11.setBucket9(count_21_23_11);
					bucket11.setBucket1(count_23_06_11);
					bucket11.setDay(Constans.TIME_DAY11);
				
			}else if(Constans.TIME_DAY12.equals(map.get("compEday"))){
				
				   if("06".equals(map.get("compHour"))){//早6-7
					  	
					   bucket12.setBucket2(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("07".equals(map.get("compHour"))){//早7-8
					  	
					   bucket12.setBucket3(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("08".equals(map.get("compHour"))||"09".equals(map.get("compHour"))||"10".equals(map.get("compHour"))||"11".equals(map.get("compHour"))){//早8-12
					  	
					    count_8_12_12 +=Integer.parseInt(map.get("compNum").toString());
					    
					}
				   if("12".equals(map.get("compHour"))||"13".equals(map.get("compHour"))){//早12-14
					  	
					   count_12_14_12 += Integer.parseInt(map.get("compNum").toString());
					   
					}
				   if("14".equals(map.get("compHour"))){//早14-15
					  	
					   bucket12.setBucket6(Integer.parseInt(map.get("compNum").toString()));
					}
					
					if("15".equals(map.get("compHour"))||"16".equals(map.get("compHour"))){//早15-17
					  	
						count_15_17_12 += Integer.parseInt(map.get("compNum").toString());
						
					}
					if("17".equals(map.get("compHour"))||"18".equals(map.get("compHour"))||"19".equals(map.get("compHour"))||"20".equals(map.get("compHour"))){//早17-21
					  	
						count_17_21_12 += Integer.parseInt(map.get("compNum").toString());
					}
					if("21".equals(map.get("compHour"))||"22".equals(map.get("compHour"))){//早21-23
					  	
						count_21_23_12 += Integer.parseInt(map.get("compNum").toString());
					}	
					if("23".equals(map.get("compHour"))||"00".equals(map.get("compHour"))||"01".equals(map.get("compHour"))||"02".equals(map.get("compHour"))||"03".equals(map.get("compHour"))||"04".equals(map.get("compHour"))||"05".equals(map.get("compHour"))){//晚23-早6 
					  	
						count_23_06_12 += Integer.parseInt(map.get("compNum").toString());
					}	
					
					bucket12.setBucket4(count_8_12_12);
					bucket12.setBucket5(count_12_14_12);
					bucket12.setBucket7(count_15_17_12);
					bucket12.setBucket8(count_17_21_12);
					bucket12.setBucket9(count_21_23_12);
					bucket12.setBucket1(count_23_06_12);
					bucket12.setDay(Constans.TIME_DAY12);
					
			}else if(Constans.TIME_DAY13.equals(map.get("compEday"))){
				
				
				   if("06".equals(map.get("compHour"))){//早6-7
					  	
					   bucket13.setBucket2(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("07".equals(map.get("compHour"))){//早7-8
					  	
					   bucket13.setBucket3(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("08".equals(map.get("compHour"))||"09".equals(map.get("compHour"))||"10".equals(map.get("compHour"))||"11".equals(map.get("compHour"))){//早8-12
					  	
					    count_8_12_13 +=Integer.parseInt(map.get("compNum").toString());
					    
					}
				   if("12".equals(map.get("compHour"))||"13".equals(map.get("compHour"))){//早12-14
					  	
					   count_12_14_13 += Integer.parseInt(map.get("compNum").toString());
					   
					}
				   if("14".equals(map.get("compHour"))){//早14-15
					  	
					   bucket13.setBucket6(Integer.parseInt(map.get("compNum").toString()));
					}
					
					if("15".equals(map.get("compHour"))||"16".equals(map.get("compHour"))){//早15-17
					  	
						count_15_17_13 += Integer.parseInt(map.get("compNum").toString());
						
					}
					if("17".equals(map.get("compHour"))||"18".equals(map.get("compHour"))||"19".equals(map.get("compHour"))||"20".equals(map.get("compHour"))){//早17-21
					  	
						count_17_21_13 += Integer.parseInt(map.get("compNum").toString());
					}
					if("21".equals(map.get("compHour"))||"22".equals(map.get("compHour"))){//早21-23
					  	
						count_21_23_13 += Integer.parseInt(map.get("compNum").toString());
					}	
					if("23".equals(map.get("compHour"))||"00".equals(map.get("compHour"))||"01".equals(map.get("compHour"))||"02".equals(map.get("compHour"))||"03".equals(map.get("compHour"))||"04".equals(map.get("compHour"))||"05".equals(map.get("compHour"))){//晚23-早6 
					  	
						count_23_06_13 += Integer.parseInt(map.get("compNum").toString());
					}	
					
					bucket13.setBucket4(count_8_12_13);
					bucket13.setBucket5(count_12_14_13);
					bucket13.setBucket7(count_15_17_13);
					bucket13.setBucket8(count_17_21_13);
					bucket13.setBucket9(count_21_23_13);
					bucket13.setBucket1(count_23_06_13);
					bucket13.setDay(Constans.TIME_DAY13);
					
			}else if(Constans.TIME_DAY14.equals(map.get("compEday"))){
				
				
				   if("06".equals(map.get("compHour"))){//早6-7
					  	
					   bucket14.setBucket2(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("07".equals(map.get("compHour"))){//早7-8
					  	
					   bucket14.setBucket3(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("08".equals(map.get("compHour"))||"09".equals(map.get("compHour"))||"10".equals(map.get("compHour"))||"11".equals(map.get("compHour"))){//早8-12
					  	
					    count_8_12_14 +=Integer.parseInt(map.get("compNum").toString());
					    
					}
				   if("12".equals(map.get("compHour"))||"13".equals(map.get("compHour"))){//早12-14
					  	
					   count_12_14_14 += Integer.parseInt(map.get("compNum").toString());
					   
					}
				   if("14".equals(map.get("compHour"))){//早14-15
					  	
					   bucket14.setBucket6(Integer.parseInt(map.get("compNum").toString()));
					}
					
					if("15".equals(map.get("compHour"))||"16".equals(map.get("compHour"))){//早15-17
					  	
						count_15_17_14 += Integer.parseInt(map.get("compNum").toString());
						
					}
					if("17".equals(map.get("compHour"))||"18".equals(map.get("compHour"))||"19".equals(map.get("compHour"))||"20".equals(map.get("compHour"))){//早17-21
					  	
						count_17_21_14 += Integer.parseInt(map.get("compNum").toString());
					}
					if("21".equals(map.get("compHour"))||"22".equals(map.get("compHour"))){//早21-23
					  	
						count_21_23_14 += Integer.parseInt(map.get("compNum").toString());
					}	
					if("23".equals(map.get("compHour"))||"00".equals(map.get("compHour"))||"01".equals(map.get("compHour"))||"02".equals(map.get("compHour"))||"03".equals(map.get("compHour"))||"04".equals(map.get("compHour"))||"05".equals(map.get("compHour"))){//晚23-早6 
					  	
						count_23_06_14 += Integer.parseInt(map.get("compNum").toString());
					}	
					
					bucket14.setBucket4(count_8_12_14);
					bucket14.setBucket5(count_12_14_14);
					bucket14.setBucket7(count_15_17_14);
					bucket14.setBucket8(count_17_21_14);
					bucket14.setBucket9(count_21_23_14);
					bucket14.setBucket1(count_23_06_14);
					bucket14.setDay(Constans.TIME_DAY14);
				
			}else if(Constans.TIME_DAY15.equals(map.get("compEday"))){
				
				
				   if("06".equals(map.get("compHour"))){//早6-7
					  	
					   bucket15.setBucket2(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("07".equals(map.get("compHour"))){//早7-8
					  	
					   bucket15.setBucket3(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("08".equals(map.get("compHour"))||"09".equals(map.get("compHour"))||"10".equals(map.get("compHour"))||"11".equals(map.get("compHour"))){//早8-12
					  	
					    count_8_12_15 +=Integer.parseInt(map.get("compNum").toString());
					    
					}
				   if("12".equals(map.get("compHour"))||"13".equals(map.get("compHour"))){//早12-14
					  	
					   count_12_14_15 += Integer.parseInt(map.get("compNum").toString());
					   
					}
				   if("14".equals(map.get("compHour"))){//早14-15
					  	
					   bucket15.setBucket6(Integer.parseInt(map.get("compNum").toString()));
					}
					
					if("15".equals(map.get("compHour"))||"16".equals(map.get("compHour"))){//早15-17
					  	
						count_15_17_15 += Integer.parseInt(map.get("compNum").toString());
						
					}
					if("17".equals(map.get("compHour"))||"18".equals(map.get("compHour"))||"19".equals(map.get("compHour"))||"20".equals(map.get("compHour"))){//早17-21
					  	
						count_17_21_15 += Integer.parseInt(map.get("compNum").toString());
					}
					if("21".equals(map.get("compHour"))||"22".equals(map.get("compHour"))){//早21-23
					  	
						count_21_23_15 += Integer.parseInt(map.get("compNum").toString());
					}	
					if("23".equals(map.get("compHour"))||"00".equals(map.get("compHour"))||"01".equals(map.get("compHour"))||"02".equals(map.get("compHour"))||"03".equals(map.get("compHour"))||"04".equals(map.get("compHour"))||"05".equals(map.get("compHour"))){//晚23-早6 
					  	
						count_23_06_15 += Integer.parseInt(map.get("compNum").toString());
					}	
					
					bucket15.setBucket4(count_8_12_15);
					bucket15.setBucket5(count_12_14_15);
					bucket15.setBucket7(count_15_17_15);
					bucket15.setBucket8(count_17_21_15);
					bucket15.setBucket9(count_21_23_15);
					bucket15.setBucket1(count_23_06_15);
					bucket15.setDay(Constans.TIME_DAY15);
					
			}else if(Constans.TIME_DAY16.equals(map.get("compEday"))){
				
				   if("06".equals(map.get("compHour"))){//早6-7
					  	
					   bucket16.setBucket2(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("07".equals(map.get("compHour"))){//早7-8
					  	
					   bucket16.setBucket3(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("08".equals(map.get("compHour"))||"09".equals(map.get("compHour"))||"10".equals(map.get("compHour"))||"11".equals(map.get("compHour"))){//早8-12
					  	
					    count_8_12_16 +=Integer.parseInt(map.get("compNum").toString());
					    
					}
				   if("12".equals(map.get("compHour"))||"13".equals(map.get("compHour"))){//早12-14
					  	
					   count_12_14_16 += Integer.parseInt(map.get("compNum").toString());
					   
					}
				   if("14".equals(map.get("compHour"))){//早14-15
					  	
					   bucket16.setBucket6(Integer.parseInt(map.get("compNum").toString()));
					}
					
					if("15".equals(map.get("compHour"))||"16".equals(map.get("compHour"))){//早15-17
					  	
						count_15_17_16 += Integer.parseInt(map.get("compNum").toString());
						
					}
					if("17".equals(map.get("compHour"))||"18".equals(map.get("compHour"))||"19".equals(map.get("compHour"))||"20".equals(map.get("compHour"))){//早17-21
					  	
						count_17_21_16 += Integer.parseInt(map.get("compNum").toString());
					}
					if("21".equals(map.get("compHour"))||"22".equals(map.get("compHour"))){//早21-23
					  	
						count_21_23_16 += Integer.parseInt(map.get("compNum").toString());
					}	
					if("23".equals(map.get("compHour"))||"00".equals(map.get("compHour"))||"01".equals(map.get("compHour"))||"02".equals(map.get("compHour"))||"03".equals(map.get("compHour"))||"04".equals(map.get("compHour"))||"05".equals(map.get("compHour"))){//晚23-早6 
					  	
						count_23_06_16 += Integer.parseInt(map.get("compNum").toString());
					}	
					
					bucket16.setBucket4(count_8_12_16);
					bucket16.setBucket5(count_12_14_16);
					bucket16.setBucket7(count_15_17_16);
					bucket16.setBucket8(count_17_21_16);
					bucket16.setBucket9(count_21_23_16);
					bucket16.setBucket1(count_23_06_16);
					bucket16.setDay(Constans.TIME_DAY16);
					
			}else if(Constans.TIME_DAY17.equals(map.get("compEday"))){
				
				
				   if("06".equals(map.get("compHour"))){//早6-7
					  	
					   bucket17.setBucket2(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("07".equals(map.get("compHour"))){//早7-8
					  	
					   bucket17.setBucket3(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("08".equals(map.get("compHour"))||"09".equals(map.get("compHour"))||"10".equals(map.get("compHour"))||"11".equals(map.get("compHour"))){//早8-12
					  	
					    count_8_12_17 +=Integer.parseInt(map.get("compNum").toString());
					    
					}
				   if("12".equals(map.get("compHour"))||"13".equals(map.get("compHour"))){//早12-14
					  	
					   count_12_14_17 += Integer.parseInt(map.get("compNum").toString());
					   
					}
				   if("14".equals(map.get("compHour"))){//早14-15
					  	
					   bucket17.setBucket6(Integer.parseInt(map.get("compNum").toString()));
					}
					
					if("15".equals(map.get("compHour"))||"16".equals(map.get("compHour"))){//早15-17
					  	
						count_15_17_17 += Integer.parseInt(map.get("compNum").toString());
						
					}
					if("17".equals(map.get("compHour"))||"18".equals(map.get("compHour"))||"19".equals(map.get("compHour"))||"20".equals(map.get("compHour"))){//早17-21
					  	
						count_17_21_17 += Integer.parseInt(map.get("compNum").toString());
					}
					if("21".equals(map.get("compHour"))||"22".equals(map.get("compHour"))){//早21-23
					  	
						count_21_23_17 += Integer.parseInt(map.get("compNum").toString());
					}	
					if("23".equals(map.get("compHour"))||"00".equals(map.get("compHour"))||"01".equals(map.get("compHour"))||"02".equals(map.get("compHour"))||"03".equals(map.get("compHour"))||"04".equals(map.get("compHour"))||"05".equals(map.get("compHour"))){//晚23-早6 
					  	
						count_23_06_17 += Integer.parseInt(map.get("compNum").toString());
					}	
					
					bucket17.setBucket4(count_8_12_17);
					bucket17.setBucket5(count_12_14_17);
					bucket17.setBucket7(count_15_17_17);
					bucket17.setBucket8(count_17_21_17);
					bucket17.setBucket9(count_21_23_17);
					bucket17.setBucket1(count_23_06_17);
					bucket17.setDay(Constans.TIME_DAY17);
					
			}else if(Constans.TIME_DAY18.equals(map.get("compEday"))){
				
				   if("06".equals(map.get("compHour"))){//早6-7
					  	
					   bucket18.setBucket2(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("07".equals(map.get("compHour"))){//早7-8
					  	
					   bucket18.setBucket3(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("08".equals(map.get("compHour"))||"09".equals(map.get("compHour"))||"10".equals(map.get("compHour"))||"11".equals(map.get("compHour"))){//早8-12
					  	
					    count_8_12_18 +=Integer.parseInt(map.get("compNum").toString());
					    
					}
				   if("12".equals(map.get("compHour"))||"13".equals(map.get("compHour"))){//早12-14
					  	
					   count_12_14_18 += Integer.parseInt(map.get("compNum").toString());
					   
					}
				   if("14".equals(map.get("compHour"))){//早14-15
					  	
					   bucket18.setBucket6(Integer.parseInt(map.get("compNum").toString()));
					}
					
					if("15".equals(map.get("compHour"))||"16".equals(map.get("compHour"))){//早15-17
					  	
						count_15_17_18 += Integer.parseInt(map.get("compNum").toString());
						
					}
					if("17".equals(map.get("compHour"))||"18".equals(map.get("compHour"))||"19".equals(map.get("compHour"))||"20".equals(map.get("compHour"))){//早17-21
					  	
						count_17_21_18 += Integer.parseInt(map.get("compNum").toString());
					}
					if("21".equals(map.get("compHour"))||"22".equals(map.get("compHour"))){//早21-23
					  	
						count_21_23_18 += Integer.parseInt(map.get("compNum").toString());
					}	
					if("23".equals(map.get("compHour"))||"00".equals(map.get("compHour"))||"01".equals(map.get("compHour"))||"02".equals(map.get("compHour"))||"03".equals(map.get("compHour"))||"04".equals(map.get("compHour"))||"05".equals(map.get("compHour"))){//晚23-早6 
					  	
						count_23_06_18 += Integer.parseInt(map.get("compNum").toString());
					}	
					
					bucket18.setBucket4(count_8_12_18);
					bucket18.setBucket5(count_12_14_18);
					bucket18.setBucket7(count_15_17_18);
					bucket18.setBucket8(count_17_21_18);
					bucket18.setBucket9(count_21_23_18);
					bucket18.setBucket1(count_23_06_18);
					bucket18.setDay(Constans.TIME_DAY18);
				
			}else if(Constans.TIME_DAY19.equals(map.get("compEday"))){
				
				
				   if("06".equals(map.get("compHour"))){//早6-7
					  	
					   bucket19.setBucket2(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("07".equals(map.get("compHour"))){//早7-8
					  	
					   bucket19.setBucket3(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("08".equals(map.get("compHour"))||"09".equals(map.get("compHour"))||"10".equals(map.get("compHour"))||"11".equals(map.get("compHour"))){//早8-12
					  	
					    count_8_12_19 +=Integer.parseInt(map.get("compNum").toString());
					    
					}
				   if("12".equals(map.get("compHour"))||"13".equals(map.get("compHour"))){//早12-14
					  	
					   count_12_14_19 += Integer.parseInt(map.get("compNum").toString());
					   
					}
				   if("14".equals(map.get("compHour"))){//早14-15
					  	
					   bucket19.setBucket6(Integer.parseInt(map.get("compNum").toString()));
					}
					
					if("15".equals(map.get("compHour"))||"16".equals(map.get("compHour"))){//早15-17
					  	
						count_15_17_19 += Integer.parseInt(map.get("compNum").toString());
						
					}
					if("17".equals(map.get("compHour"))||"18".equals(map.get("compHour"))||"19".equals(map.get("compHour"))||"20".equals(map.get("compHour"))){//早17-21
					  	
						count_17_21_19 += Integer.parseInt(map.get("compNum").toString());
					}
					if("21".equals(map.get("compHour"))||"22".equals(map.get("compHour"))){//早21-23
					  	
						count_21_23_19 += Integer.parseInt(map.get("compNum").toString());
					}	
					if("23".equals(map.get("compHour"))||"00".equals(map.get("compHour"))||"01".equals(map.get("compHour"))||"02".equals(map.get("compHour"))||"03".equals(map.get("compHour"))||"04".equals(map.get("compHour"))||"05".equals(map.get("compHour"))){//晚23-早6 
					  	
						count_23_06_19 += Integer.parseInt(map.get("compNum").toString());
					}	
					
					bucket19.setBucket4(count_8_12_19);
					bucket19.setBucket5(count_12_14_19);
					bucket19.setBucket7(count_15_17_19);
					bucket19.setBucket8(count_17_21_19);
					bucket19.setBucket9(count_21_23_19);
					bucket19.setBucket1(count_23_06_19);
					bucket19.setDay(Constans.TIME_DAY19);
					
			}else if(Constans.TIME_DAY20.equals(map.get("compEday"))){
				
				
				   if("06".equals(map.get("compHour"))){//早6-7
					  	
					   bucket20.setBucket2(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("07".equals(map.get("compHour"))){//早7-8
					  	
					   bucket20.setBucket3(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("08".equals(map.get("compHour"))||"09".equals(map.get("compHour"))||"10".equals(map.get("compHour"))||"11".equals(map.get("compHour"))){//早8-12
					  	
					    count_8_12_20 +=Integer.parseInt(map.get("compNum").toString());
					    
					}
				   if("12".equals(map.get("compHour"))||"13".equals(map.get("compHour"))){//早12-14
					  	
					   count_12_14_20 += Integer.parseInt(map.get("compNum").toString());
					   
					}
				   if("14".equals(map.get("compHour"))){//早14-15
					  	
					   bucket20.setBucket6(Integer.parseInt(map.get("compNum").toString()));
					}
					
					if("15".equals(map.get("compHour"))||"16".equals(map.get("compHour"))){//早15-17
					  	
						count_15_17_20 += Integer.parseInt(map.get("compNum").toString());
						
					}
					if("17".equals(map.get("compHour"))||"18".equals(map.get("compHour"))||"19".equals(map.get("compHour"))||"20".equals(map.get("compHour"))){//早17-21
					  	
						count_17_21_20 += Integer.parseInt(map.get("compNum").toString());
					}
					if("21".equals(map.get("compHour"))||"22".equals(map.get("compHour"))){//早21-23
					  	
						count_21_23_20 += Integer.parseInt(map.get("compNum").toString());
					}	
					if("23".equals(map.get("compHour"))||"00".equals(map.get("compHour"))||"01".equals(map.get("compHour"))||"02".equals(map.get("compHour"))||"03".equals(map.get("compHour"))||"04".equals(map.get("compHour"))||"05".equals(map.get("compHour"))){//晚23-早6 
					  	
						count_23_06_20 += Integer.parseInt(map.get("compNum").toString());
					}	
					
					bucket20.setBucket4(count_8_12_20);
					bucket20.setBucket5(count_12_14_20);
					bucket20.setBucket7(count_15_17_20);
					bucket20.setBucket8(count_17_21_20);
					bucket20.setBucket9(count_21_23_20);
					bucket20.setBucket1(count_23_06_20);
					bucket20.setDay(Constans.TIME_DAY20);
					
			}else if(Constans.TIME_DAY21.equals(map.get("compEday"))){
				
				   if("06".equals(map.get("compHour"))){//早6-7
					  	
					   bucket21.setBucket2(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("07".equals(map.get("compHour"))){//早7-8
					  	
					   bucket21.setBucket3(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("08".equals(map.get("compHour"))||"09".equals(map.get("compHour"))||"10".equals(map.get("compHour"))||"11".equals(map.get("compHour"))){//早8-12
					  	
					    count_8_12_21 +=Integer.parseInt(map.get("compNum").toString());
					    
					}
				   if("12".equals(map.get("compHour"))||"13".equals(map.get("compHour"))){//早12-14
					  	
					   count_12_14_21 += Integer.parseInt(map.get("compNum").toString());
					   
					}
				   if("14".equals(map.get("compHour"))){//早14-15
					  	
					   bucket21.setBucket6(Integer.parseInt(map.get("compNum").toString()));
					}
					
					if("15".equals(map.get("compHour"))||"16".equals(map.get("compHour"))){//早15-17
					  	
						count_15_17_21 += Integer.parseInt(map.get("compNum").toString());
						
					}
					if("17".equals(map.get("compHour"))||"18".equals(map.get("compHour"))||"19".equals(map.get("compHour"))||"20".equals(map.get("compHour"))){//早17-21
					  	
						count_17_21_21 += Integer.parseInt(map.get("compNum").toString());
					}
					if("21".equals(map.get("compHour"))||"22".equals(map.get("compHour"))){//早21-23
					  	
						count_21_23_21 += Integer.parseInt(map.get("compNum").toString());
					}	
					if("23".equals(map.get("compHour"))||"00".equals(map.get("compHour"))||"01".equals(map.get("compHour"))||"02".equals(map.get("compHour"))||"03".equals(map.get("compHour"))||"04".equals(map.get("compHour"))||"05".equals(map.get("compHour"))){//晚23-早6 
					  	
						count_23_06_21 += Integer.parseInt(map.get("compNum").toString());
					}	
					
					bucket21.setBucket4(count_8_12_21);
					bucket21.setBucket5(count_12_14_21);
					bucket21.setBucket7(count_15_17_21);
					bucket21.setBucket8(count_17_21_21);
					bucket21.setBucket9(count_21_23_21);
					bucket21.setBucket1(count_23_06_21);
					bucket21.setDay(Constans.TIME_DAY21);
					
			}else if(Constans.TIME_DAY22.equals(map.get("compEday"))){
				
				
				   if("06".equals(map.get("compHour"))){//早6-7
					  	
					   bucket22.setBucket2(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("07".equals(map.get("compHour"))){//早7-8
					  	
					   bucket22.setBucket3(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("08".equals(map.get("compHour"))||"09".equals(map.get("compHour"))||"10".equals(map.get("compHour"))||"11".equals(map.get("compHour"))){//早8-12
					  	
					    count_8_12_22 +=Integer.parseInt(map.get("compNum").toString());
					    
					}
				   if("12".equals(map.get("compHour"))||"13".equals(map.get("compHour"))){//早12-14
					  	
					   count_12_14_22 += Integer.parseInt(map.get("compNum").toString());
					   
					}
				   if("14".equals(map.get("compHour"))){//早14-15
					  	
					   bucket22.setBucket6(Integer.parseInt(map.get("compNum").toString()));
					}
					
					if("15".equals(map.get("compHour"))||"16".equals(map.get("compHour"))){//早15-17
					  	
						count_15_17_22 += Integer.parseInt(map.get("compNum").toString());
						
					}
					if("17".equals(map.get("compHour"))||"18".equals(map.get("compHour"))||"19".equals(map.get("compHour"))||"20".equals(map.get("compHour"))){//早17-21
					  	
						count_17_21_22 += Integer.parseInt(map.get("compNum").toString());
					}
					if("21".equals(map.get("compHour"))||"22".equals(map.get("compHour"))){//早21-23
					  	
						count_21_23_22 += Integer.parseInt(map.get("compNum").toString());
					}	
					if("23".equals(map.get("compHour"))||"00".equals(map.get("compHour"))||"01".equals(map.get("compHour"))||"02".equals(map.get("compHour"))||"03".equals(map.get("compHour"))||"04".equals(map.get("compHour"))||"05".equals(map.get("compHour"))){//晚23-早6 
					  	
						count_23_06_22 += Integer.parseInt(map.get("compNum").toString());
					}	
					
					bucket22.setBucket4(count_8_12_22);
					bucket22.setBucket5(count_12_14_22);
					bucket22.setBucket7(count_15_17_22);
					bucket22.setBucket8(count_17_21_22);
					bucket22.setBucket9(count_21_23_22);
					bucket22.setBucket1(count_23_06_22);
					bucket22.setDay(Constans.TIME_DAY22);
					
			}else if(Constans.TIME_DAY23.equals(map.get("compEday"))){
				
				
				   if("06".equals(map.get("compHour"))){//早6-7
					  	
					   bucket23.setBucket2(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("07".equals(map.get("compHour"))){//早7-8
					  	
					   bucket23.setBucket3(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("08".equals(map.get("compHour"))||"09".equals(map.get("compHour"))||"10".equals(map.get("compHour"))||"11".equals(map.get("compHour"))){//早8-12
					  	
					    count_8_12_23 +=Integer.parseInt(map.get("compNum").toString());
					    
					}
				   if("12".equals(map.get("compHour"))||"13".equals(map.get("compHour"))){//早12-14
					  	
					   count_12_14_23 += Integer.parseInt(map.get("compNum").toString());
					   
					}
				   if("14".equals(map.get("compHour"))){//早14-15
					  	
					   bucket23.setBucket6(Integer.parseInt(map.get("compNum").toString()));
					}
					
					if("15".equals(map.get("compHour"))||"16".equals(map.get("compHour"))){//早15-17
					  	
						count_15_17_23 += Integer.parseInt(map.get("compNum").toString());
						
					}
					if("17".equals(map.get("compHour"))||"18".equals(map.get("compHour"))||"19".equals(map.get("compHour"))||"20".equals(map.get("compHour"))){//早17-21
					  	
						count_17_21_23 += Integer.parseInt(map.get("compNum").toString());
					}
					if("21".equals(map.get("compHour"))||"22".equals(map.get("compHour"))){//早21-23
					  	
						count_21_23_23 += Integer.parseInt(map.get("compNum").toString());
					}	
					if("23".equals(map.get("compHour"))||"00".equals(map.get("compHour"))||"01".equals(map.get("compHour"))||"02".equals(map.get("compHour"))||"03".equals(map.get("compHour"))||"04".equals(map.get("compHour"))||"05".equals(map.get("compHour"))){//晚23-早6 
					  	
						count_23_06_23 += Integer.parseInt(map.get("compNum").toString());
					}	
					
					bucket23.setBucket4(count_8_12_23);
					bucket23.setBucket5(count_12_14_23);
					bucket23.setBucket7(count_15_17_23);
					bucket23.setBucket8(count_17_21_23);
					bucket23.setBucket9(count_21_23_23);
					bucket23.setBucket1(count_23_06_23);
					bucket23.setDay(Constans.TIME_DAY23);
					
			}else if(Constans.TIME_DAY24.equals(map.get("compEday"))){
				
				
				
				   if("06".equals(map.get("compHour"))){//早6-7
					  	
					   bucket24.setBucket2(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("07".equals(map.get("compHour"))){//早7-8
					  	
					   bucket24.setBucket3(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("08".equals(map.get("compHour"))||"09".equals(map.get("compHour"))||"10".equals(map.get("compHour"))||"11".equals(map.get("compHour"))){//早8-12
					  	
					    count_8_12_24 +=Integer.parseInt(map.get("compNum").toString());
					    
					}
				   if("12".equals(map.get("compHour"))||"13".equals(map.get("compHour"))){//早12-14
					  	
					   count_12_14_24 += Integer.parseInt(map.get("compNum").toString());
					   
					}
				   if("14".equals(map.get("compHour"))){//早14-15
					  	
					   bucket24.setBucket6(Integer.parseInt(map.get("compNum").toString()));
					}
					
					if("15".equals(map.get("compHour"))||"16".equals(map.get("compHour"))){//早15-17
					  	
						count_15_17_24 += Integer.parseInt(map.get("compNum").toString());
						
					}
					if("17".equals(map.get("compHour"))||"18".equals(map.get("compHour"))||"19".equals(map.get("compHour"))||"20".equals(map.get("compHour"))){//早17-21
					  	
						count_17_21_24 += Integer.parseInt(map.get("compNum").toString());
					}
					if("21".equals(map.get("compHour"))||"22".equals(map.get("compHour"))){//早21-23
					  	
						count_21_23_24 += Integer.parseInt(map.get("compNum").toString());
					}	
					if("23".equals(map.get("compHour"))||"00".equals(map.get("compHour"))||"01".equals(map.get("compHour"))||"02".equals(map.get("compHour"))||"03".equals(map.get("compHour"))||"04".equals(map.get("compHour"))||"05".equals(map.get("compHour"))){//晚23-早6 
					  	
						count_23_06_24 += Integer.parseInt(map.get("compNum").toString());
					}	
					
					bucket24.setBucket4(count_8_12_24);
					bucket24.setBucket5(count_12_14_24);
					bucket24.setBucket7(count_15_17_24);
					bucket24.setBucket8(count_17_21_24);
					bucket24.setBucket9(count_21_23_24);
					bucket24.setBucket1(count_23_06_24);
					bucket24.setDay(Constans.TIME_DAY24);
					
			}else if(Constans.TIME_DAY25.equals(map.get("compEday"))){
				
				
				   if("06".equals(map.get("compHour"))){//早6-7
					  	
					   bucket25.setBucket2(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("07".equals(map.get("compHour"))){//早7-8
					  	
					   bucket25.setBucket3(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("08".equals(map.get("compHour"))||"09".equals(map.get("compHour"))||"10".equals(map.get("compHour"))||"11".equals(map.get("compHour"))){//早8-12
					  	
					    count_8_12_25 +=Integer.parseInt(map.get("compNum").toString());
					    
					}
				   if("12".equals(map.get("compHour"))||"13".equals(map.get("compHour"))){//早12-14
					  	
					   count_12_14_25 += Integer.parseInt(map.get("compNum").toString());
					   
					}
				   if("14".equals(map.get("compHour"))){//早14-15
					  	
					   bucket25.setBucket6(Integer.parseInt(map.get("compNum").toString()));
					}
					
					if("15".equals(map.get("compHour"))||"16".equals(map.get("compHour"))){//早15-17
					  	
						count_15_17_25 += Integer.parseInt(map.get("compNum").toString());
						
					}
					if("17".equals(map.get("compHour"))||"18".equals(map.get("compHour"))||"19".equals(map.get("compHour"))||"20".equals(map.get("compHour"))){//早17-21
					  	
						count_17_21_25 += Integer.parseInt(map.get("compNum").toString());
					}
					if("21".equals(map.get("compHour"))||"22".equals(map.get("compHour"))){//早21-23
					  	
						count_21_23_25 += Integer.parseInt(map.get("compNum").toString());
					}	
					if("23".equals(map.get("compHour"))||"00".equals(map.get("compHour"))||"01".equals(map.get("compHour"))||"02".equals(map.get("compHour"))||"03".equals(map.get("compHour"))||"04".equals(map.get("compHour"))||"05".equals(map.get("compHour"))){//晚23-早6 
					  	
						count_23_06_25 += Integer.parseInt(map.get("compNum").toString());
					}	
					
					bucket25.setBucket4(count_8_12_25);
					bucket25.setBucket5(count_12_14_25);
					bucket25.setBucket7(count_15_17_25);
					bucket25.setBucket8(count_17_21_25);
					bucket25.setBucket9(count_21_23_25);
					bucket25.setBucket1(count_23_06_25);
					bucket25.setDay(Constans.TIME_DAY25);
					
			}else if(Constans.TIME_DAY26.equals(map.get("compEday"))){
				
				
				
				   if("06".equals(map.get("compHour"))){//早6-7
					  	
					   bucket26.setBucket2(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("07".equals(map.get("compHour"))){//早7-8
					  	
					   bucket26.setBucket3(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("08".equals(map.get("compHour"))||"09".equals(map.get("compHour"))||"10".equals(map.get("compHour"))||"11".equals(map.get("compHour"))){//早8-12
					  	
					    count_8_12_26 +=Integer.parseInt(map.get("compNum").toString());
					    
					}
				   if("12".equals(map.get("compHour"))||"13".equals(map.get("compHour"))){//早12-14
					  	
					   count_12_14_26 += Integer.parseInt(map.get("compNum").toString());
					   
					}
				   if("14".equals(map.get("compHour"))){//早14-15
					  	
					   bucket26.setBucket6(Integer.parseInt(map.get("compNum").toString()));
					}
					
					if("15".equals(map.get("compHour"))||"16".equals(map.get("compHour"))){//早15-17
					  	
						count_15_17_26 += Integer.parseInt(map.get("compNum").toString());
						
					}
					if("17".equals(map.get("compHour"))||"18".equals(map.get("compHour"))||"19".equals(map.get("compHour"))||"20".equals(map.get("compHour"))){//早17-21
					  	
						count_17_21_26 += Integer.parseInt(map.get("compNum").toString());
					}
					if("21".equals(map.get("compHour"))||"22".equals(map.get("compHour"))){//早21-23
					  	
						count_21_23_26 += Integer.parseInt(map.get("compNum").toString());
					}	
					if("23".equals(map.get("compHour"))||"00".equals(map.get("compHour"))||"01".equals(map.get("compHour"))||"02".equals(map.get("compHour"))||"03".equals(map.get("compHour"))||"04".equals(map.get("compHour"))||"05".equals(map.get("compHour"))){//晚23-早6 
					  	
						count_23_06_26 += Integer.parseInt(map.get("compNum").toString());
					}	
					
					bucket26.setBucket4(count_8_12_26);
					bucket26.setBucket5(count_12_14_26);
					bucket26.setBucket7(count_15_17_26);
					bucket26.setBucket8(count_17_21_26);
					bucket26.setBucket9(count_21_23_26);
					bucket26.setBucket1(count_23_06_26);
					bucket26.setDay(Constans.TIME_DAY26);
					
			}else if(Constans.TIME_DAY27.equals(map.get("compEday"))){
				
				   if("06".equals(map.get("compHour"))){//早6-7
					  	
					   bucket27.setBucket2(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("07".equals(map.get("compHour"))){//早7-8
					  	
					   bucket27.setBucket3(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("08".equals(map.get("compHour"))||"09".equals(map.get("compHour"))||"10".equals(map.get("compHour"))||"11".equals(map.get("compHour"))){//早8-12
					  	
					    count_8_12_27 +=Integer.parseInt(map.get("compNum").toString());
					    
					}
				   if("12".equals(map.get("compHour"))||"13".equals(map.get("compHour"))){//早12-14
					  	
					   count_12_14_27 += Integer.parseInt(map.get("compNum").toString());
					   
					}
				   if("14".equals(map.get("compHour"))){//早14-15
					  	
					   bucket27.setBucket6(Integer.parseInt(map.get("compNum").toString()));
					}
					
					if("15".equals(map.get("compHour"))||"16".equals(map.get("compHour"))){//早15-17
					  	
						count_15_17_27 += Integer.parseInt(map.get("compNum").toString());
						
					}
					if("17".equals(map.get("compHour"))||"18".equals(map.get("compHour"))||"19".equals(map.get("compHour"))||"20".equals(map.get("compHour"))){//早17-21
					  	
						count_17_21_27+= Integer.parseInt(map.get("compNum").toString());
					}
					if("21".equals(map.get("compHour"))||"22".equals(map.get("compHour"))){//早21-23
					  	
						count_21_23_27 += Integer.parseInt(map.get("compNum").toString());
					}	
					if("23".equals(map.get("compHour"))||"00".equals(map.get("compHour"))||"01".equals(map.get("compHour"))||"02".equals(map.get("compHour"))||"03".equals(map.get("compHour"))||"04".equals(map.get("compHour"))||"05".equals(map.get("compHour"))){//晚23-早6 
					  	
						count_23_06_27 += Integer.parseInt(map.get("compNum").toString());
					}	
					
					bucket27.setBucket4(count_8_12_27);
					bucket27.setBucket5(count_12_14_27);
					bucket27.setBucket7(count_15_17_27);
					bucket27.setBucket8(count_17_21_27);
					bucket27.setBucket9(count_21_23_27);
					bucket27.setBucket1(count_23_06_27);
					bucket27.setDay(Constans.TIME_DAY27);
					
			}else if(Constans.TIME_DAY28.equals(map.get("compEday"))){
				
				
				   if("06".equals(map.get("compHour"))){//早6-7
					  	
					   bucket28.setBucket2(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("07".equals(map.get("compHour"))){//早7-8
					  	
					   bucket28.setBucket3(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("08".equals(map.get("compHour"))||"09".equals(map.get("compHour"))||"10".equals(map.get("compHour"))||"11".equals(map.get("compHour"))){//早8-12
					  	
					    count_8_12_28 +=Integer.parseInt(map.get("compNum").toString());
					    
					}
				   if("12".equals(map.get("compHour"))||"13".equals(map.get("compHour"))){//早12-14
					  	
					   count_12_14_28 += Integer.parseInt(map.get("compNum").toString());
					   
					}
				   if("14".equals(map.get("compHour"))){//早14-15
					  	
					   bucket28.setBucket6(Integer.parseInt(map.get("compNum").toString()));
					}
					
					if("15".equals(map.get("compHour"))||"16".equals(map.get("compHour"))){//早15-17
					  	
						count_15_17_28 += Integer.parseInt(map.get("compNum").toString());
						
					}
					if("17".equals(map.get("compHour"))||"18".equals(map.get("compHour"))||"19".equals(map.get("compHour"))||"20".equals(map.get("compHour"))){//早17-21
					  	
						count_17_21_28 += Integer.parseInt(map.get("compNum").toString());
					}
					if("21".equals(map.get("compHour"))||"22".equals(map.get("compHour"))){//早21-23
					  	
						count_21_23_28 += Integer.parseInt(map.get("compNum").toString());
					}	
					if("23".equals(map.get("compHour"))||"00".equals(map.get("compHour"))||"01".equals(map.get("compHour"))||"02".equals(map.get("compHour"))||"03".equals(map.get("compHour"))||"04".equals(map.get("compHour"))||"05".equals(map.get("compHour"))){//晚23-早6 
					  	
						count_23_06_28 += Integer.parseInt(map.get("compNum").toString());
					}	
					
					bucket28.setBucket4(count_8_12_28);
					bucket28.setBucket5(count_12_14_28);
					bucket28.setBucket7(count_15_17_28);
					bucket28.setBucket8(count_17_21_28);
					bucket28.setBucket9(count_21_23_28);
					bucket28.setBucket1(count_23_06_28);
					bucket28.setDay(Constans.TIME_DAY28);
					
			}else if(Constans.TIME_DAY29.equals(map.get("compEday"))){
				
				
				   if("06".equals(map.get("compHour"))){//早6-7
					  	
					   bucket29.setBucket2(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("07".equals(map.get("compHour"))){//早7-8
					  	
					   bucket29.setBucket3(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("08".equals(map.get("compHour"))||"09".equals(map.get("compHour"))||"10".equals(map.get("compHour"))||"11".equals(map.get("compHour"))){//早8-12
					  	
					    count_8_12_29 +=Integer.parseInt(map.get("compNum").toString());
					    
					}
				   if("12".equals(map.get("compHour"))||"13".equals(map.get("compHour"))){//早12-14
					  	
					   count_12_14_29 += Integer.parseInt(map.get("compNum").toString());
					   
					}
				   if("14".equals(map.get("compHour"))){//早14-15
					  	
					   bucket29.setBucket6(Integer.parseInt(map.get("compNum").toString()));
					}
					
					if("15".equals(map.get("compHour"))||"16".equals(map.get("compHour"))){//早15-17
					  	
						count_15_17_29 += Integer.parseInt(map.get("compNum").toString());
						
					}
					if("17".equals(map.get("compHour"))||"18".equals(map.get("compHour"))||"19".equals(map.get("compHour"))||"20".equals(map.get("compHour"))){//早17-21
					  	
						count_17_21_29 += Integer.parseInt(map.get("compNum").toString());
					}
					if("21".equals(map.get("compHour"))||"22".equals(map.get("compHour"))){//早21-23
					  	
						count_21_23_29 += Integer.parseInt(map.get("compNum").toString());
					}	
					if("23".equals(map.get("compHour"))||"00".equals(map.get("compHour"))||"01".equals(map.get("compHour"))||"02".equals(map.get("compHour"))||"03".equals(map.get("compHour"))||"04".equals(map.get("compHour"))||"05".equals(map.get("compHour"))){//晚23-早6 
					  	
						count_23_06_29 += Integer.parseInt(map.get("compNum").toString());
					}	
					
					bucket29.setBucket4(count_8_12_29);
					bucket29.setBucket5(count_12_14_29);
					bucket29.setBucket7(count_15_17_29);
					bucket29.setBucket8(count_17_21_29);
					bucket29.setBucket9(count_21_23_29);
					bucket29.setBucket1(count_23_06_29);
					bucket29.setDay(Constans.TIME_DAY29);
					
			}else if(Constans.TIME_DAY30.equals(map.get("compEday"))){
				
				   if("06".equals(map.get("compHour"))){//早6-7
					  	
					   bucket30.setBucket2(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("07".equals(map.get("compHour"))){//早7-8
					  	
					   bucket30.setBucket3(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("08".equals(map.get("compHour"))||"09".equals(map.get("compHour"))||"10".equals(map.get("compHour"))||"11".equals(map.get("compHour"))){//早8-12
					  	
					    count_8_12_30 +=Integer.parseInt(map.get("compNum").toString());
					    
					}
				   if("12".equals(map.get("compHour"))||"13".equals(map.get("compHour"))){//早12-14
					  	
					   count_12_14_30 += Integer.parseInt(map.get("compNum").toString());
					   
					}
				   if("14".equals(map.get("compHour"))){//早14-15
					  	
					   bucket30.setBucket6(Integer.parseInt(map.get("compNum").toString()));
					}
					
					if("15".equals(map.get("compHour"))||"16".equals(map.get("compHour"))){//早15-17
					  	
						count_15_17_30 += Integer.parseInt(map.get("compNum").toString());
						
					}
					if("17".equals(map.get("compHour"))||"18".equals(map.get("compHour"))||"19".equals(map.get("compHour"))||"20".equals(map.get("compHour"))){//早17-21
					  	
						count_17_21_30 += Integer.parseInt(map.get("compNum").toString());
					}
					if("21".equals(map.get("compHour"))||"22".equals(map.get("compHour"))){//早21-23
					  	
						count_21_23_30 += Integer.parseInt(map.get("compNum").toString());
					}	
					if("23".equals(map.get("compHour"))||"00".equals(map.get("compHour"))||"01".equals(map.get("compHour"))||"02".equals(map.get("compHour"))||"03".equals(map.get("compHour"))||"04".equals(map.get("compHour"))||"05".equals(map.get("compHour"))){//晚23-早6 
					  	
						count_23_06_30 += Integer.parseInt(map.get("compNum").toString());
					}	
					
					bucket30.setBucket4(count_8_12_30);
					bucket30.setBucket5(count_12_14_30);
					bucket30.setBucket7(count_15_17_30);
					bucket30.setBucket8(count_17_21_30);
					bucket30.setBucket9(count_21_23_30);
					bucket30.setBucket1(count_23_06_30);
					bucket30.setDay(Constans.TIME_DAY30);
				
			}else if(Constans.TIME_DAY31.equals(map.get("compEday"))){
				
				   
				   if("06".equals(map.get("compHour"))){//早6-7
					  	
					   bucket31.setBucket2(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("07".equals(map.get("compHour"))){//早7-8
					  	
					   bucket31.setBucket3(Integer.parseInt(map.get("compNum").toString()));
					}
				   if("08".equals(map.get("compHour"))||"09".equals(map.get("compHour"))||"10".equals(map.get("compHour"))||"11".equals(map.get("compHour"))){//早8-12
					  	
					    count_8_12_31 +=Integer.parseInt(map.get("compNum").toString());
					    
					}
				   if("12".equals(map.get("compHour"))||"13".equals(map.get("compHour"))){//早12-14
					  	
					   count_12_14_31 += Integer.parseInt(map.get("compNum").toString());
					   
					}
				   if("14".equals(map.get("compHour"))){//早14-15
					  	
					   bucket31.setBucket6(Integer.parseInt(map.get("compNum").toString()));
					}
					
					if("15".equals(map.get("compHour"))||"16".equals(map.get("compHour"))){//早15-17
					  	
						count_15_17_31 += Integer.parseInt(map.get("compNum").toString());
						
					}
					if("17".equals(map.get("compHour"))||"18".equals(map.get("compHour"))||"19".equals(map.get("compHour"))||"20".equals(map.get("compHour"))){//早17-21
					  	
						count_17_21_31 += Integer.parseInt(map.get("compNum").toString());
					}
					if("21".equals(map.get("compHour"))||"22".equals(map.get("compHour"))){//早21-23
					  	
						count_21_23_31 += Integer.parseInt(map.get("compNum").toString());
					}	
					if("23".equals(map.get("compHour"))||"00".equals(map.get("compHour"))||"01".equals(map.get("compHour"))||"02".equals(map.get("compHour"))||"03".equals(map.get("compHour"))||"04".equals(map.get("compHour"))||"05".equals(map.get("compHour"))){//晚23-早6 
					  	
						count_23_06_31 += Integer.parseInt(map.get("compNum").toString());
					}	
					
					bucket31.setBucket4(count_8_12_31);
					bucket31.setBucket5(count_12_14_31);
					bucket31.setBucket7(count_15_17_31);
					bucket31.setBucket8(count_17_21_31);
					bucket31.setBucket9(count_21_23_31);
					bucket31.setBucket1(count_23_06_31);
					bucket31.setDay(Constans.TIME_DAY31);
			}
			
		}
		list.add(bucket1);
		list.add(bucket2);
		list.add(bucket3);
		list.add(bucket4);
		list.add(bucket5);
		list.add(bucket6);
		list.add(bucket7);
		list.add(bucket8);
		list.add(bucket9);
		list.add(bucket10);
		list.add(bucket11);
		list.add(bucket12);
		list.add(bucket13);
		list.add(bucket14);
		list.add(bucket15);
		list.add(bucket16);
		list.add(bucket17);
		list.add(bucket18);
		list.add(bucket19);
		list.add(bucket20);
		list.add(bucket21);
		list.add(bucket22);
		list.add(bucket23);
		list.add(bucket24);
		list.add(bucket25);
		list.add(bucket26);
		list.add(bucket27);
		list.add(bucket28);
		list.add(bucket29);
		list.add(bucket30);
		list.add(bucket31);
		
		return list;
		
		
		
	}
	
	
	
	
	public String getStart() {
		return start;
	}



	public void setStart(String start) {
		this.start = start;
	}



	public String getEnd() {
		return end;
	}



	public void setEnd(String end) {
		this.end = end;
	}




	public String getPersonname() {
		return personname;
	}



	public void setPersonname(String personname) {
		this.personname = personname;
	}



	public Integer getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getPerNum() {
		return perNum;
	}



	public void setPerNum(String perNum) {
		this.perNum = perNum;
	}



	public String getTitNum() {
		return titNum;
	}



	public void setTitNum(String titNum) {
		this.titNum = titNum;
	}






	
	
	

}
