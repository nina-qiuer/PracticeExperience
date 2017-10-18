package com.tuniu.gt.toolkit;

import tuniu.frm.service.Constant;

public class RTXSenderThread extends Thread {
	
	private int userId;
	
	private String userName;
	
	private String title;
	
	private String content;
	
	public RTXSenderThread(int userId, String userName, String title, String content) {
		this.userId = userId;
		this.userName = userName;
		this.title = title;
		this.content = content;
	}
	
	@Override
	public void run() {
		if (Constant.CONFIG.getProperty("sendFlag").equals("1")) {
			Rtx.sendSingleRtx(userId, userName, title, content);
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append("userId: ").append(userId).append("\r")
			  .append("userName: ").append(userName).append("\r")
			  .append("content: ").append("\r").append(content);
			 Rtx.sendSingleRtx(17193, "fangyouming", title, sb.toString());
			 Rtx.sendSingleRtx(18965, "liubing", title, sb.toString());
		}
	}

}
