package com.tuniu.gt.complaint.enums;

public enum SupportConfirmStateEnum {
	//nb未对接
	NOT_NB(-1),
	//未确认
	NOT_CONFIRM(0),
	//已确认
	CONFIRMED(1),
	//到期默认
	AUTO_CONFIRMED(2),
	//已申诉
	APEALED(3),
	//申诉审批中
	APPROVALING(4),
	//申诉审批通过
	APPROVAL_SUCC(8),
	//申诉审批未通过
	APPROVAL_FAIL(6),
	
	//未对接nb申诉审批中
    NOT_NB_APPROVALING(5),
    //未对接nb申诉审批通过
    NOT_NB_APPROVAL_SUCC(9),
    //未对接nb申诉审批不通过
    NOT_NB_APPROVAL_FAIL(7);
	
	private Integer value;
	
	SupportConfirmStateEnum(Integer value){
        this.value = value;
    }
    
    public Integer getValue(){
        return this.value;
    }
}
