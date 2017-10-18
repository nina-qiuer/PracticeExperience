package com.tuniu.gt.complaint.enums;

public enum DealDepartIdEnum {
    SPECIAL_BEFORE_TRAVEL(1296),//出游前客户服务
    
    IN_TRAVEL(748),//总部售后
    
    AFTER_TRAVEL(74),//总部资深
    AFTER_TRAVEL_EAST(3374),//华东资深
    AFTER_TRAVEL_NORTH(5942),//华北资深
    AFTER_TRAVEL_SOUTH(3711),//华南资深
    
    AIR_TICKIT(3176),//机票
    
    COLLIGATE(5931);//综合
	
    private Integer value;
    DealDepartIdEnum(Integer value){
        this.value = value;
    }
    
    public Integer getValue(){
        return this.value;
    }
}
