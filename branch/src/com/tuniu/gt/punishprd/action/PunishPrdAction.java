package com.tuniu.gt.punishprd.action;

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

import com.tuniu.gt.punishprd.entity.PunishPrdEntity;
import com.tuniu.gt.punishprd.service.IPunishPrdService;
import com.tuniu.gt.punishprd.vo.LowQualityDetailVo;
import com.tuniu.gt.punishprd.vo.LowSatisfyDetailVo;
import com.tuniu.gt.punishprd.vo.PunishPrdVo;
import com.tuniu.gt.toolkit.CellDataMapperHandler;
import com.tuniu.gt.toolkit.CommonUtil;
import com.tuniu.gt.toolkit.DateUtil;
import com.tuniu.gt.toolkit.POIUtil;
import com.tuniu.gt.toolkit.lang.CollectionUtil;

import tuniu.frm.core.FrmBaseAction;

@Service("punishprd_action-punishprd")
@Scope("prototype")
public class PunishPrdAction extends FrmBaseAction<IPunishPrdService, PunishPrdEntity> {
	
	private Logger logger = Logger.getLogger(getClass());

	private PunishPrdVo vo;
	private Map<String,Object> info; 
	
	public PunishPrdAction() {
		setManageUrl("punishprd");
		info = new HashMap<String, Object>();
	}

	@Autowired
	@Qualifier("pp_service_impl-punishprd")
	public void setService(IPunishPrdService service) {
		this.service = service;
	};

	public String execute() {
		this.setPageTitle("处罚产品管理列表");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (null == vo) {
			vo = new PunishPrdVo();
		}
		paramMap.putAll(vo.toMap());
		super.execute(paramMap);
		
		request.setAttribute("vo", vo);

		return "punishprd_list";
	}
	
	
	public String chgPrdStatus() {
		vo.setUpdatePersonId(user.getId());
		vo.setUpdatePerson(user.getRealName());
		boolean isSucc = service.chgPrdStatus(vo);
		CommonUtil.writeResponse(isSucc);
		
		return "punishprd_list";
	}
	
	public String lowSatisfyDetail(){
		LowSatisfyDetailVo vo = service.getLowSatisfactionDetail(id);
		request.setAttribute("vo", vo);
		return "punishprd_lowsatisfy_detail";
	}
	
	public String lowQualityDetail(){
		LowQualityDetailVo vo = service.getLowQualityDetail(id);
		request.setAttribute("vo", vo);
		return "punishprd_lowquality_detail";
	}
	
	
	public String checkExportsCount(){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (null == vo) {
			vo = new PunishPrdVo();
		}
		paramMap.putAll(vo.toMap());
		Integer count  = service.getListCount(paramMap);
		
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
	
	public String exports(){
		
		//获取列表数据
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (null == vo) {
			vo = new PunishPrdVo();
		}
		paramMap.putAll(vo.toMap());
		paramMap.put("exports", "1");
		super.execute(paramMap);
		
		request.setAttribute("vo", vo);
		List<PunishPrdEntity>   prdList = (List<PunishPrdEntity>) request.getAttribute("dataList");
		
		if(CollectionUtil.isNotEmpty(prdList)) {
			try {
				
				String[] headers = new String[]{"触发时间","订单号","线路编号","线路名称","事业部","产品经理","产品专员","供应商","下线类型","下线次数","下线时间","上线时间","状态"};
				String[] exportsFields = "triggerTime,orderId,routeId,routeName,BU,prdManager,prdOfficer,supplier,offlineType,offlineCount,offlineTime,onlineTime,status".split(",");
				new POIUtil<PunishPrdEntity>().exportExcel(Arrays.asList(headers),
						Arrays.asList(exportsFields), prdList, 
						new CellDataMapperHandler() {

							@Override
							public String mapToCell(String fieldName,
									Object value) {
								String textValue = "";
								 if(value instanceof Date) {
	                                    Date date = (Date) value;
	                                    textValue = DateUtil.formatDate(date);
	                                    return textValue;
	                                }
								if ("offlineType".equals(fieldName)) {
									switch ((Integer) value) {
									case 1:
										textValue = "触红";
										break;
									case 2:
										textValue = "低满意度";
										break;
									case 3:
										textValue = "低质量";
										break;
									default:
										break;
									}
								} else if ("status".equals(fieldName)) {
									switch ((Integer) value) {
									case 1:
										textValue = "待整改";
										break;
									case 2:
										textValue = "整改中";
										break;
									case 3:
										textValue = "已整改";
										break;
									case 4:
										textValue = "永久下线";
										break;

									default:
										break;
									}
								}else{
									textValue=value==null?"":value+"";
								}
								return textValue;
							}
						});

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			} 

		}

		return null;
	}
	public PunishPrdVo getVo() {
		return vo;
	}

	public void setVo(PunishPrdVo vo) {
		this.vo = vo;
	}

	public Map<String, Object> getInfo() {
		return info;
	}

	public void setInfo(Map<String, Object> info) {
		this.info = info;
	}
}
