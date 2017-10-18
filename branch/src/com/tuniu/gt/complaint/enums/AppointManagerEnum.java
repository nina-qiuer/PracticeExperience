package com.tuniu.gt.complaint.enums;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.tuniu.gt.complaint.Constans;

public enum AppointManagerEnum {
	// 售后组
	IN_TRAVEL(2, Constans.IN_TRAVEL),
	// 资深组
	AFTER_TRAVEL(3, Constans.AFTER_TRAVEL),
	// 出游前客户服务
	SPECIAL_BEFORE_TRAVEL(5, Constans.SPECIAL_BEFORE_TRAVEL),
	// 机票组
	AIR_TICKIT(6, Constans.AIR_TICKIT),
	// 酒店组
	HOTEL(7, Constans.HOTEL),
	// 交通组
	TRAFFIC(15, Constans.TRAFFIC),
	// 分销组
	DISTRIBUTE(8, Constans.DISTRIBUTE);

	private Integer type;

	private String dealDepart;

	private AppointManagerEnum(Integer type, String dealDepart) {
		this.type = type;
		this.dealDepart = dealDepart;
	}

	private Integer getType() {
		return type;
	}

	private String getDealDepart() {
		return dealDepart;
	}

	//根据负责人类型获取对应的处理岗
	public static String getValue(Integer type) {
		for (AppointManagerEnum appointManager : AppointManagerEnum.values()) {
			if (appointManager.getType().equals(type)) {
				return appointManager.getDealDepart();
			}
		}
		return null;
	}
	
	public static List<String> getValueList(List<Integer> typeList) {
		Set<String> dealDepartSet = new LinkedHashSet<String>();
		for (Integer type : typeList) {
			for (AppointManagerEnum appointManager : AppointManagerEnum.values()) {
				if (appointManager.getType().equals(type)) {
					dealDepartSet.add(appointManager.getDealDepart());
				}
			}
		}
		if(dealDepartSet.size()==0){
			return null;
		}
		List<String> result = new ArrayList<String>();
		result.addAll(dealDepartSet);
		return result;
	}
}
