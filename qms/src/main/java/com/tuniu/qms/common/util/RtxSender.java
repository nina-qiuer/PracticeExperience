package com.tuniu.qms.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.tuniu.qms.access.client.RestClient;
import com.tuniu.qms.common.init.Config;

public class RtxSender {

	private static String URL = Config.get("rtx.server");

	private final static Logger logger = LoggerFactory.getLogger(RtxSender.class);

	/**
	 * 发送一条rtx提醒
	 * @param rtx
	 * @return
	 */
	public static synchronized Boolean sendSingleRtx(Rtx rtx) {
		RestClient rc = new RestClient(URL, "POST", JSON.toJSON(rtx));
		RtxResult result = JSON.parseObject(rc.execute(), RtxResult.class);
		return dealWithResult(result);
	}

	private static Boolean dealWithResult(RtxResult result) {
		Boolean sucFlag = true;
		String code = result.getCode();
		if (RtxResult.FAIL.equals(code)) {
			sucFlag = false;
			logger.warn("===发送RTX提醒失败,失败描述："+result.getDescription()+"===");
		}
		return sucFlag;
	}
	
	public static void main(String[] args) {
		sendSingleRtx(new Rtx());
	}

	/**
	 * 发送多人rtx提醒
	 * @param rtx rtx实体
	 * @param uids 发送目标人id列表
	 * @return
	 */
	/*public static synchronized Boolean sendMultiRtx(Rtx rtx, Integer[] uids) {
		RestClient rc = null;
		RtxResult result = null;
		try {
			for (Integer uid : uids) {
				Rtx rtxClone;
				rtxClone = (Rtx) rtx.clone();
				rtxClone.setUid(uid);
				rc = new RestClient(URL, METHOD, JSON.toJSON(rtxClone));
				result = JSON.toJavaObject((JSONObject) rc.execute(),
						RtxResult.class);
				dealWithResult(result);
			}
		} catch (CloneNotSupportedException e) {
			logger.error(e.getMessage(),e);
		}
		return true;
	}*/

}
