package com.tuniu.qms.qs.util;

import java.util.Arrays;
import java.util.List;

public enum PreSaleReturnTypeEnum {
	FIRSTLEVEL(1, 5), SECONDLEVEL(2, 4), THIRDLEVEL(3, 3), FOURlEVEL(4, 1), FIVElEVEL(5, 0);
	
	private int key;
	private int score;
	
	private PreSaleReturnTypeEnum(int key, int score){
		this.key = key;
		this.score = score;
	}
	
	public static int getScoreBykey(int key){
		PreSaleReturnTypeEnum[] list = PreSaleReturnTypeEnum.values();
		
		for(PreSaleReturnTypeEnum e : list){
			if(e.key == key){
				return e.score;
			}
		}
		
		return -1;
	}
	
	public static List<PreSaleReturnTypeEnum> getSourceList(){
		return Arrays.asList(PreSaleReturnTypeEnum.values());
	}

	public int getKey() {
		return key;
	}

	public int getScore() {
		return score;
	}
	
}
