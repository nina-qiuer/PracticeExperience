package com.tuniu.qms.qs.util;

public enum ProductRemarkSatisfyEnum {
	ALLWAY("一路之上", 95), FRIENDSTOUR("朋派定制游", 92), CATTLELINE("牛人专线", 90), HAPPYPARENTSTOUR("乐开花爸妈游", 90),
	LETSGO("出发吧我们", 90), PARENTCHILDTOUR("瓜果亲子游", 90), AFFORDABLETOUR("实惠游", 85);
	
	private String key;
	
	private int score;
	
	private ProductRemarkSatisfyEnum(String key, int score){
		this.key = key;
		this.score = score;
	}
	
	public static int getScore(String key){
		ProductRemarkSatisfyEnum[] list = ProductRemarkSatisfyEnum.values();
		
		for(ProductRemarkSatisfyEnum type : list){
			if(type.key.equals(key)){
				return type.score;
			}
		}
		
		return 75;
	}

	public String getKey() {
		return key;
	}

	public int getScore() {
		return score;
	}
	
}
