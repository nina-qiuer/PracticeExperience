package com.tuniu.qms.qc.util;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.tuniu.qms.common.model.Department;


/**
 * 获取子节点
 * 
 */
public class NodeUtil {
	
	private static List<Integer> returnList = new ArrayList<Integer>();	
	


	public static List<Integer> getReturnList() {
		return returnList;
	}


	public static void setReturnList(List<Integer> returnList) {
		NodeUtil.returnList = returnList;
	}


	/**
	 * 根据父节点的ID获取所有子节点
	 * @param list 分类表
	 * @param typeId 传入的父节点ID
	 * @return List<Long>
	 */
	public static List<Integer> getChildNodes(List<Department> list, Integer typeId) {
		returnList = new  ArrayList<Integer>();	
		if(list == null && typeId == null)	return null;
		for (Iterator<Department> iterator = list.iterator(); iterator.hasNext();) {
				Department node = (Department) iterator.next();
				// 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
				if (typeId.intValue()==node.getId().intValue()) {
					recursionFn(list, node);
				}
			}
		return returnList;
}


private static void recursionFn(List<Department> list, Department node) {
	List<Department> childList = getChildList(list, node);// 得到子节点列表
	if (hasChild(list, node)) {// 判断是否有子节点
		returnList.add(node.getId());
		Iterator<Department> it = childList.iterator();
		while (it.hasNext()) {
			Department n = (Department) it.next();
			recursionFn(list, n);
		}
	} else {
		
		returnList.add(node.getId());
	}
}


/**
 * 根据父节点获取他的下一级子节点（注意是只得到下一级的，不是全部的）
 * @param list 分类表
 * @param Node 传入的父节点对象
 * @return List<Node>
 */
public static List<Department> getChildList(List<Department> list, Department node) {
	List<Department> nodeList = new ArrayList<Department>();
	Iterator<Department> it = list.iterator();
	while (it.hasNext()) {
		Department n = (Department) it.next();
		if (n.getPid().intValue() == node.getId().intValue()) {
			nodeList.add(n);
		}
	}
	return nodeList;
}

/**
 * 根据父节点判断是否有子节点
 * @param list 分类表
 * @param Node 传入的父节点对象
 * @return boolean
 */
public static boolean hasChild(List<Department> list, Department node) {
	return getChildList(list, node).size() > 0 ? true : false;
}
	
}

