package com.tuniu.gt.toolkit;

public class GetSql {

	public static void main(String[] args) {
		
		int[] tourTimeNodeArr = new int[]{1, 2, 3};
		int[] complaintLevelArr = new int[]{1, 2, 3};
		String[] productLineArr = new String[]{"跟团游", "自助游", "公司旅游", "牛人专线", "途牛自组"};
		String[] destCateArr = new String[]{"周边", "国内长线", "出境短线", "出境长线"};
		
		for (Integer i : tourTimeNodeArr) {
			for (Integer j : complaintLevelArr) {
				for (String pl : productLineArr) {
					for (String dc : destCateArr) {
						StringBuffer sql = new StringBuffer();
						sql.append("insert into ct_assign_category (tour_time_node, complaint_level, product_line, dest_cate) values (")
						.append(i).append(", ").append(j).append(", ").append("\"").append(pl).append("\", \"")
						.append(dc).append("\");");
						System.out.println(sql.toString());
					}
				}
			}
		}

	}

}
