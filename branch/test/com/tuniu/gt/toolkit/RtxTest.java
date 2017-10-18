package com.tuniu.gt.toolkit;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class RtxTest {

	@Test
	public void testSingleRtx() {
		Rtx.sendSingleRtx(3394, "yuliang", "this is a test operation", "uh,you know,good luck!");
		//fail("Not yet implemented");
	}

	@Test
	public void testMultiRtx() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(3394, "yuliang");
		map.put(3393, "liangjie");
		//Rtx.sendMultiRtx(map, "this is a test operation", "uh,you know,good luck!");
		//fail("Not yet implemented");
	}

}
