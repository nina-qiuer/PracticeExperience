package com.tuniu.gt.frm.action.system;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;

import com.tuniu.gt.frm.entity.FestivalEntity;
import com.tuniu.gt.frm.service.system.IFestivalService;
import com.tuniu.gt.frm.vo.FestivalVo;


@Service("frm_action_system-festival")
@Scope("prototype")
public class FestivalAction extends FrmBaseAction<IFestivalService, FestivalEntity> { 
	
	private static final long serialVersionUID = 1L;
	
	private List<FestivalVo> fesVoList = new ArrayList<FestivalVo>();
	
	private List<FestivalEntity> fesEntList = new ArrayList<FestivalEntity>();
	
	private FestivalVo vo = new FestivalVo();

	public List<FestivalVo> getFesVoList() {
		return fesVoList;
	}

	public void setFesVoList(List<FestivalVo> fesVoList) {
		this.fesVoList = fesVoList;
	}

	public List<FestivalEntity> getFesEntList() {
		return fesEntList;
	}

	public void setFesEntList(List<FestivalEntity> fesEntList) {
		this.fesEntList = fesEntList;
	}

	public FestivalAction() {
		setManageUrl("festival");
	}
	
	public FestivalVo getVo() {
		return vo;
	}

	public void setVo(FestivalVo vo) {
		this.vo = vo;
	}

	@Autowired
	@Qualifier("frm_service_system_impl-festival")
	public void setService(IFestivalService service) {
		this.service = service;
	}
	
	public String execute() {
		this.setPageTitle("法定假日及补工日期");
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		String fYear = request.getParameter("fYear");
		
		List<String> fYearList = service.getFYearList();
		if (null != fYearList && fYearList.size() > 0) {
			if (!fYearList.contains(fYear)) {
				fYear = fYearList.get(0);
			}
		}
		
		paramMap.put("fYear", fYear);
		List<FestivalEntity> dataList = (List<FestivalEntity>) service.fetchList(paramMap);
		
		if (null != dataList && dataList.size() > 0) {
			makeFesVoList(dataList);
		}
		
		request.setAttribute("fYearList", fYearList);
		request.setAttribute("fYear", fYear);
		
		return "list";
	}
	
	private void makeFesVoList(List<FestivalEntity> dataList) {
		FestivalVo vo = new FestivalVo();
		StringBuffer fesDateStr = new StringBuffer();
		StringBuffer offsetDateStr = new StringBuffer();
		for (int i=0; i<dataList.size(); i++) {
			FestivalEntity ent = dataList.get(i);
			
			if (0 == i) {
				vo.setFesId(ent.getFesId());
				vo.setFestivalName(ent.getFestivalName());
				if (1 == ent.getDateType()) {
					fesDateStr.append(ent.getFestivalDate());
				} else {
					offsetDateStr.append(ent.getFestivalDate());
				}
			} else {
				if (vo.getFestivalName().equals(ent.getFestivalName())) {
					if (1 == ent.getDateType()) {
						if (fesDateStr.length() > 0) {
							fesDateStr.append(", ");
						}
						fesDateStr.append(ent.getFestivalDate());
					} else {
						if (offsetDateStr.length() > 0) {
							offsetDateStr.append(", ");
						}
						offsetDateStr.append(ent.getFestivalDate());
					}
				} else {
					vo.setFesDateStr(fesDateStr.toString());
					vo.setOffsetDateStr(offsetDateStr.toString());
					fesVoList.add(vo);
					
					vo = new FestivalVo();
					fesDateStr = new StringBuffer();
					offsetDateStr = new StringBuffer();
					vo.setFesId(ent.getFesId());
					vo.setFestivalName(ent.getFestivalName());
					if (1 == ent.getDateType()) {
						fesDateStr.append(ent.getFestivalDate());
					} else {
						offsetDateStr.append(ent.getFestivalDate());
					}
				}
			}
	    }
		vo.setFesDateStr(fesDateStr.toString());
		vo.setOffsetDateStr(offsetDateStr.toString());
		fesVoList.add(vo);
	}
	
	public String toAddFestival() {
		fesEntList = service.getFesNameList();
		return "form";
	}
	
	public String doAddFestival() {
		List<FestivalEntity> entList = new ArrayList<FestivalEntity>();
		
		if (-1 != vo.getFesDateStr().indexOf(",")) {
			String[] fesDateArr = vo.getFesDateStr().split(",");
			for (String date : fesDateArr) {
				FestivalEntity ent = new FestivalEntity();
				ent.setFesId(vo.getFesId());
				ent.setFestivalDate(Date.valueOf(date.trim()));
				ent.setDateType(1);
				entList.add(ent);
			}
		} else {
			FestivalEntity ent = new FestivalEntity();
			ent.setFesId(vo.getFesId());
			ent.setFestivalDate(Date.valueOf(vo.getFesDateStr().trim()));
			ent.setDateType(1);
			entList.add(ent);
		}
		
		if (null != vo.getOffsetDateStr() && !"".equals(vo.getOffsetDateStr())) {
			if (-1 != vo.getOffsetDateStr().indexOf(",")) {
				String[] offsetDateArr = vo.getOffsetDateStr().split(",");
				for (String date : offsetDateArr) {
					FestivalEntity ent = new FestivalEntity();
					ent.setFesId(vo.getFesId());
					ent.setFestivalDate(Date.valueOf(date.trim()));
					ent.setDateType(2);
					entList.add(ent);
				}
			} else {
				FestivalEntity ent = new FestivalEntity();
				ent.setFesId(vo.getFesId());
				ent.setFestivalDate(Date.valueOf(vo.getOffsetDateStr().trim()));
				ent.setDateType(2);
				entList.add(ent);
			}
		}
		
		service.batchInsert(entList);
		
		request.setAttribute("succFlag", "Success");
		
		return "form";
	}
	
	public String delFes() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fesId", Integer.valueOf(request.getParameter("fesId")));
		params.put("fYear", request.getParameter("fYear"));
		service.delFes(params);
		
		request.setAttribute("delFlag", "Success");
		
		return "list";
	}
	
}
