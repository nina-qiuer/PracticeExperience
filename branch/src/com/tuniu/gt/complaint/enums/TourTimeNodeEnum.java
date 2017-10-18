package com.tuniu.gt.complaint.enums;

import com.tuniu.gt.complaint.Constans;

public enum TourTimeNodeEnum {
	SPECIAL_BEFORE_TRAVEL(Constans.SPECIAL_BEFORE_TRAVEL, 1), IN_TRAVEL(Constans.IN_TRAVEL, 2), 
	AFTER_TRAVEL(Constans.AFTER_TRAVEL,3), AIR_TICKIT(Constans.AIR_TICKIT, 4), HOTEL(Constans.HOTEL, 5), TRAFFIC(Constans.TRAFFIC, 6), VIP_DEPART(Constans.VIP_DEPART, 8);

	private Integer tourTimeNode;
	
	private String dealDepart;

	private TourTimeNodeEnum(String dealDepart, Integer tourTimeNode) {
		this.dealDepart = dealDepart;
        this.tourTimeNode = tourTimeNode;
	}

	public static Integer getValue(String dealDepart) {
		for (TourTimeNodeEnum  tourTimeNode: TourTimeNodeEnum.values()) {
            if (tourTimeNode.getDealDepart().equals(dealDepart)) {
                return tourTimeNode.getTourTimeNode();
            }
        }
		return null;
	}
	
    public String getDealDepart() {
        return dealDepart;
    }

    public void setDealDepart(String dealDepart) {
        this.dealDepart = dealDepart;
    }

    public Integer getTourTimeNode() {
        return tourTimeNode;
    }

    public void setTourTimeNode(Integer tourTimeNode) {
        this.tourTimeNode = tourTimeNode;
    }
}
