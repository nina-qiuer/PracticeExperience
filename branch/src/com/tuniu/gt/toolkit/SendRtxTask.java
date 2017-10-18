package com.tuniu.gt.toolkit;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import tuniu.frm.service.Constant;
import tuniu.frm.tools.Db;

public class SendRtxTask extends TimerTask {
	
	/**
	 * 定时发送下次跟进时间rtx提醒
	 */
	public void run() {
		if(Constant.CONFIG.getProperty("sendFlag").equals("1")){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Db db = new Db();
			java.sql.Connection conn = db.connect();
			Date now = new Date();
			String sql = "SELECT id, complaint_id, order_id, user_id, user_name, note FROM ct_follow_time WHERE del_flag=1 AND time>='"
					+ format.format(now) + "' AND time<='" + format.format(new Date(now.getTime() + 60000)) + "'";
			//System.out.println(sql);
			List<Map> followTimeMap = db.fetchList(sql);
			if (followTimeMap != null) {
				for (Map map : followTimeMap) {
					int id = Integer.valueOf((String) map.get("id"));
					String title = "投诉单号：" + map.get("complaint_id") + "跟进提醒";
					int userId = Integer.valueOf((String) map.get("user_id"));
					String userName = (String) map.get("user_name");
					String note = (String) map.get("note");
					Rtx.sendSingleRtx(userId, userName, title, note);
					String updateSql = "UPDATE ct_follow_time SET del_flag=0 WHERE id=" + id;
					db.query(updateSql);
				}
			}
			db.disConnect(conn);
		}
	}

}
