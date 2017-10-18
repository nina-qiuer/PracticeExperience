package com.tuniu.gt.complaint.entity;

import java.io.Serializable;

public class TimeBucketEntity implements Serializable {

	/**
	 * 排班人为分段数量
	 */
	private static final long serialVersionUID = 1L;

	private String day;//日期
	
	private int  bucket1 ;//晚23-早6 
	
	private int  bucket2 ;//早6-7
	
	private int  bucket3 ;//早7-8
	
	private int  bucket4 ;//早8-12
	
	private int  bucket5 ;//早12-14
	
	private int  bucket6 ;//早14-15
	
	private int  bucket7 ;//早15-17
	
	private int  bucket8 ;//早17-21
	
	private int  bucket9 ;//早21-23

	
	
	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public int getBucket1() {
		return bucket1;
	}

	public void setBucket1(int bucket1) {
		this.bucket1 = bucket1;
	}

	public int getBucket2() {
		return bucket2;
	}

	public void setBucket2(int bucket2) {
		this.bucket2 = bucket2;
	}

	public int getBucket3() {
		return bucket3;
	}

	public void setBucket3(int bucket3) {
		this.bucket3 = bucket3;
	}

	public int getBucket4() {
		return bucket4;
	}

	public void setBucket4(int bucket4) {
		this.bucket4 = bucket4;
	}

	public int getBucket5() {
		return bucket5;
	}

	public void setBucket5(int bucket5) {
		this.bucket5 = bucket5;
	}

	public int getBucket6() {
		return bucket6;
	}

	public void setBucket6(int bucket6) {
		this.bucket6 = bucket6;
	}

	public int getBucket7() {
		return bucket7;
	}

	public void setBucket7(int bucket7) {
		this.bucket7 = bucket7;
	}

	public int getBucket8() {
		return bucket8;
	}

	public void setBucket8(int bucket8) {
		this.bucket8 = bucket8;
	}

	public int getBucket9() {
		return bucket9;
	}

	public void setBucket9(int bucket9) {
		this.bucket9 = bucket9;
	}

	


}
