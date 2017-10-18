package com.tuniu.gt.uc.service.user.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;
import tuniu.frm.service.Common;
import tuniu.frm.service.Constant;

import com.tuniu.gt.frm.dao.impl.UserDao;
import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.toolkit.MemcachesUtil;
import com.tuniu.gt.uc.dao.impl.DepartmentDao;
import com.tuniu.gt.uc.datastructure.DepartUsersTree;
import com.tuniu.gt.uc.datastructure.TreeNode;
import com.tuniu.gt.uc.entity.DepartmentEntity;
import com.tuniu.gt.uc.entity.UcDepartmentEntity;
import com.tuniu.gt.uc.service.user.IDepartmentService;
import com.tuniu.gt.uc.vo.UserDepartmentVo;
@Service("uc_service_user_impl-department")
public class DepartmentServiceImpl extends ServiceBaseImpl<DepartmentDao> implements IDepartmentService {
	@Autowired
	@Qualifier("uc_dao_impl-department")
	public void setDao(DepartmentDao dao) {
		this.dao = dao;
	}
	
	@Autowired
    @Qualifier("frm_dao_impl-user")
	private UserDao userDao;
	
	@Override
	public Map<String, String> getNextTreeIdByFatherId(int fatherId) {
		Map<String, String> ret = new HashMap<String, String>();
		int maxTreeId = 1;
		String depth = "0";
		String treeFatherId="";
		
		
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("fields", "max(tree_id) as maxTreeId,tree_father_id,tree_id,depth,id"); //查找下级
		paramMap.put("fatherId", fatherId);
		Map<String,Object> childMap = new HashMap<String, Object>();
		
		childMap = (Map<String,Object>)dao.fetchOne(paramMap,false);
		if(childMap != null){
			maxTreeId =  Integer.parseInt(childMap.get("maxTreeId").toString()) + 1;
			treeFatherId = childMap.get("treeFatherId").toString();
			depth = childMap.get("depth").toString();
		} else {
			if(fatherId > 0) {
				paramMap.remove("fatherId"); 
				paramMap.put("id", fatherId);
				childMap = (Map<String,Object>)dao.fetchOne(paramMap,false); //查找自身
				treeFatherId = childMap.get("treeFatherId").toString() + childMap.get("treeId").toString();
				depth = (Integer.parseInt(childMap.get("depth").toString())+1) + "";
			}
		}
		
	
		ret.put("treeId", Common.strPad(maxTreeId, "0", 10)); 
		ret.put("treeFatherId", treeFatherId);
		ret.put("depth", depth);
		
		return ret;
	}

	@Override
	public DepartmentEntity getByFatherId(int fatherId) {
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("father_id", fatherId);
		return  (DepartmentEntity)dao.fetchOne(paramMap);
	}

	@Override
	public void updateChildsTreeFatherId(String oldTreeFatherId,String newTreeFatherId) {
		int len = oldTreeFatherId.length();
		int len1 = newTreeFatherId.length();
		int treeIdLen = Integer.parseInt(Constant.CONFIG.get("g_tree_id_len").toString());
		
		int diff = Math.abs(len - len1) / treeIdLen;  
		
		Map<String,Object> paramMap = new HashMap<String, Object>();
		String addSet = ""; 
		if(len > len1) {
			 
			addSet = ",depth = depth - "+ diff;	 
		} else if(len < len1) {
			addSet = ",depth = depth + "+diff;	 
		}
		
		paramMap.put("len",len);
		paramMap.put("addSet",addSet);
		paramMap.put("newFatherId",newTreeFatherId);
		paramMap.put("oldFatherId",oldTreeFatherId);
		 
		dao.updateChildsTreeFatherId(paramMap);
		
	};
	
	
	public void doRestore(int id) {
		dao.doRestore(id);
	}
	/**
	 * 根据ID获取部门信息
	 * @param id
	 * @return
	 */
	public DepartmentEntity getDepartmentById(int id) {
		return dao.fetchOne(id);
	}
	/**
	 * 根据ID获取部门及下级部门信息
	 */
	public Set<DepartmentEntity> getAllDepartmentByFatherId(int id) {
		Set<DepartmentEntity> returnSet = new HashSet<DepartmentEntity>();
		
		DepartmentEntity departmentEntity = dao.fetchOne(id);
		if (departmentEntity != null) {
			returnSet.add(departmentEntity);
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("fatherId", departmentEntity.getId());
			List<DepartmentEntity> tempList = (List<DepartmentEntity>) dao.fetchList(param);
			if (tempList != null && tempList.size() > 0) {
				for (DepartmentEntity temp : tempList) {
					returnSet.addAll(getAllDepartmentByFatherId(temp.getId()));
				}
			}
		}
		
		return returnSet;
	}

	@Override
	public int getSecondDepartmentId(int depId) {
		int secondId = 0;
		
		DepartmentEntity department = getDepartmentById(depId);
		if (1 == department.getFatherId() || -1 == department.getFatherId()) {
			return secondId;
		}
		
		DepartmentEntity fatherDepartment = getDepartmentById(department.getFatherId());
		int fatherId = fatherDepartment.getFatherId();
		while (1 != fatherId) {
			secondId = fatherDepartment.getId();
			fatherDepartment = getDepartmentById(fatherId);
			fatherId = fatherDepartment.getFatherId();
			
		}
		return secondId;
	}

	@Override
	public String getSecDeptFullName(int depId) {
		return dao.getSecDeptFullName(depId);
	}

	@Override
	public int getSecondaryDepId(Map<String, Object> map) {
		return dao.getSecondaryDepId(map);
	}
	/**
	 * 获取部门全部有效信息
	 * @return
	 */
	public List<UcDepartmentEntity> doSearchConstruts(){
		return dao.doSearchConstruts();
	}
	public List<DepartmentEntity> doBackConstruts(Map<String ,Object> map){
		return dao.doBackConstruts(map);
	}
	
	public void batchUpdateUseDeptForQc(Map<String,Object> param){
		List<Integer> ids = dao.getAllDepIdsForQc();
		param.put("depIds", ids);
		dao.batchUpdateUseDeptForQc(param);
	}

    @Override
    public DepartUsersTree buildDepartUsersTree(Integer rootDepartmentId, Integer depth) {
        DepartUsersTree  departUsersTree = (DepartUsersTree) MemcachesUtil.getObj("DEPUSERSTREE_"+rootDepartmentId);
        if(departUsersTree == null) {
            departUsersTree = new DepartUsersTree();
            DepartmentEntity tree = dao.fetchOne(rootDepartmentId);
            TreeNode<String>  root = new TreeNode<String>(tree.getDepName()); 
            departUsersTree.setRoot(root);
            appendChild(root,tree, depth);
            MemcachesUtil.setInToday("DEPUSERSTREE_"+rootDepartmentId, departUsersTree);
        }
        return departUsersTree;
    }
    
    private void appendChild(TreeNode<String> fatherNode,DepartmentEntity department, Integer depth) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("fatherId", department.getId());
        if(department.getDepth() == depth) {
            List<UserEntity> users = userDao.getUsersByDepartmentId(department.getId());
            List<TreeNode<String>>  userNodes = new ArrayList<TreeNode<String>>();
            for(UserEntity user : users) {
                if(StringUtils.isNotEmpty(user.getRealName()) && user.getRealName().length()<5){
                    TreeNode<String>  childNode = new TreeNode<String>(user.getRealName());
                    userNodes.add(childNode);
                }
            }
            fatherNode.setChilds(userNodes);
        }else {
            List<DepartmentEntity> childDep = (List<DepartmentEntity>) dao.fetchList(param);
            department.setChildDept(childDep);
            List<TreeNode<String>>  childNodes = new ArrayList<TreeNode<String>>();
            for(DepartmentEntity departmentEntity : childDep) {
                TreeNode<String>  node = new TreeNode<String>(departmentEntity.getDepName());
                childNodes.add(node);
            }
            
            fatherNode.setChilds(childNodes);
            for(DepartmentEntity departmentEntity : childDep) {
                if(departmentEntity.getDepth() <= depth) {
                    for(TreeNode<String> treeNode : childNodes) {
                        if(treeNode.getData()==departmentEntity.getDepName()) {
                            appendChild(treeNode,departmentEntity, depth);
                        }
                    }
                }
            }
        }
        
    }

	@Override
	public void fillUserDepartmentVo(UserDepartmentVo vo) {
		Integer depId = vo.getDepId();
		if (depId > 0) {
			DepartmentEntity userDepEntity = (DepartmentEntity) get(depId);
			if (userDepEntity != null) {
				int depth = userDepEntity.getDepth();
				if (depth == 3) {
					vo.setGroupId(depId);
					vo.setGroupName(userDepEntity.getDepName());
					int fatherId = userDepEntity.getFatherId();
					if (fatherId > 0) {
						DepartmentEntity fatherDepartment = (DepartmentEntity) get(fatherId);
						vo.setDepartmentId(fatherDepartment.getId());
						vo.setDepartmentName(fatherDepartment.getDepName());
						int grandpaId = fatherDepartment.getFatherId();
						if (grandpaId > 0) {
							DepartmentEntity grandpaDepartment = (DepartmentEntity) get(grandpaId);
							vo.setBusinessUnitId(grandpaDepartment.getId());
							vo.setBusinessUnitName(grandpaDepartment
									.getDepName());
						}
					}

				} else if (depth == 2) {
					int fatherId = userDepEntity.getFatherId();
					if (fatherId > 0) {
						DepartmentEntity fatherDepartment = (DepartmentEntity) get(fatherId);
						vo.setDepartmentId(depId);
						vo.setDepartmentName(userDepEntity.getDepName());
						vo.setBusinessUnitId(fatherDepartment.getId());
						vo.setBusinessUnitName(fatherDepartment.getDepName());
					}
				} else if (depth == 1) {
					vo.setBusinessUnitId(depId);
					vo.setBusinessUnitName(userDepEntity.getDepName());
				}
			}
		}

	}

	/**
	 * 根据部门id构建部门树(理论赔付)
	 * 
	 * @param paramMap
	 * @return
	 */
	@Override
	public DepartUsersTree buildPayoutReportTree(Map<String, Object> paramMap) {
		List<DepartmentEntity> deptList = dao.getUserAndDeptList(paramMap);
		Integer[] idlist_second = (Integer[]) paramMap.get("idlist_second");
		Boolean needCount = (Boolean) paramMap.get("needCount");
		needCount=needCount==null?false:needCount;
		DepartUsersTree deptUsersTree = (DepartUsersTree) MemcachesUtil
				.getObj("DeptTree_PayoutReport");
		if (deptUsersTree == null) {
			deptUsersTree = builPayoutTreeByidList(deptList,
					idlist_second,needCount);
			//设置缓存时间为1200000ms，即20分钟更新一次
			MemcachesUtil.set("DeptTree_PayoutReport",
					deptUsersTree,1200000);
		}
		return deptUsersTree;
	};

	/**
	 * 根据人员单位列表构建树(理论赔付)
	 * @param deptList
	 * @param idlist
	 * @return
	 */
	public DepartUsersTree builPayoutTreeByidList(
			List<DepartmentEntity> deptList, Integer[] idlist,Boolean needCount) {
		TreeNode<String> root=new TreeNode<String>(null);
		List<TreeNode<String>> treeFirstRootList=new ArrayList<TreeNode<String>>();
		//取除了渠道事业部其他的二级部门
		for (int i = 0; i <= idlist.length-1; i++) {
			for (int j = 0; j < deptList.size(); j++) {
				DepartmentEntity tempDep=deptList.get(j);
				if(tempDep.getId().equals(idlist[i])){
					String deptName="";
					if(tempDep.getId().equals(3306)){//华东大区
						deptName="华东售后部";
					}else if(tempDep.getId().equals(3439)){
						deptName="华北售后部";
					}else if(tempDep.getId().equals(3658)){
						deptName="华南售后部";
					}else{
						deptName=tempDep.getDepName();
					}
					TreeNode<String> secondRoot=new TreeNode<String>(deptName);
					secondRoot.setChilds(setChildRecursion(deptList,tempDep.getId(),needCount));
					treeFirstRootList.add(secondRoot);
				}
			}
		}
		for (DepartmentEntity tempDep : deptList) {
			//三级部门3960--售后投诉组
			if(tempDep.getId().equals(3960)){
				TreeNode<String> specialRoot=new TreeNode<String>("分销客服部");
				TreeNode<String> secondRoot=new TreeNode<String>("分销客服部");
				secondRoot.setChilds(setChildRecursion(deptList,tempDep.getId(),needCount));
				List<TreeNode<String>> secondRootList=new ArrayList<TreeNode<String>>();
				secondRootList.add(secondRoot);
				specialRoot.setChilds(secondRootList);
				treeFirstRootList.add(specialRoot);
			}
		}
		root.setChilds(treeFirstRootList);
		DepartUsersTree departUsersTree=new DepartUsersTree();
		departUsersTree.setRoot(root);
		return departUsersTree;
	}
	
	/**
	 * 根据部门id构建部门树
	 * 
	 * @param paramMap
	 * @return
	 */
	@Override
	public DepartUsersTree buildDepartUsersTree(Map<String, Object> paramMap) {
		List<DepartmentEntity> deptList = dao.getUserAndDeptList(paramMap);
		Integer[] idlist_second = (Integer[]) paramMap.get("idlist_second");
		Boolean needCount = (Boolean) paramMap.get("needCount");
		needCount=needCount==null?false:needCount;
		DepartUsersTree deptUsersTree = (DepartUsersTree) MemcachesUtil
				.getObj("DeptTree_Common");
		if (deptUsersTree == null) {
			deptUsersTree = buildDepartUsersTreeByidList(deptList,
					idlist_second,needCount);
			//设置缓存时间为1200000ms，即20分钟更新一次
			MemcachesUtil.set("DeptTree_Common",
					deptUsersTree,1200000);
		}
		return deptUsersTree;
	};

	/**
	 * 根据人员单位列表构建树
	 * @param deptList
	 * @param idlist
	 * @return
	 */
	public DepartUsersTree buildDepartUsersTreeByidList(
			List<DepartmentEntity> deptList, Integer[] idlist,Boolean needCount) {
		TreeNode<String> root=new TreeNode<String>(null);
		List<TreeNode<String>> treeFirstRootList=new ArrayList<TreeNode<String>>();
		for (int i = 0; i < idlist.length; i++) {
			for (int j = 0; j < deptList.size(); j++) {
				DepartmentEntity tempDep=deptList.get(j);
				if(tempDep.getId().equals(idlist[i])){
					String deptName="";
					if(tempDep.getId().equals(3306)){//华东大区
						deptName="华东售后部";
					}else if(tempDep.getId().equals(3439)){
						deptName="华北售后部";
					}else if(tempDep.getId().equals(3658)){
						deptName="华南售后部";
					}else{
						deptName=tempDep.getDepName();
					}
					TreeNode<String> secondRoot=new TreeNode<String>(deptName);
					secondRoot.setChilds(setChildRecursion(deptList,tempDep.getId(),needCount));
					treeFirstRootList.add(secondRoot);
				}
			}
		}
		root.setChilds(treeFirstRootList);
		DepartUsersTree departUsersTree=new DepartUsersTree();
		departUsersTree.setRoot(root);
		return departUsersTree;
	}
	
	/**
	 * 人员单位列表构建递归
	 * @param deptList
	 * @param fatherid
	 * @return
	 */
	public List<TreeNode<String>> setChildRecursion(List<DepartmentEntity> deptList,Integer fatherid,Boolean needCount){
		List<TreeNode<String>> tempNodeList=new ArrayList<TreeNode<String>>();
		for (int i = 0; i < deptList.size(); i++) {
			DepartmentEntity tempDep=deptList.get(i);
			if(tempDep.getFatherId().equals(fatherid)){//有子集则递归，否则出
				TreeNode<String> tempNode=new TreeNode<String>(tempDep.getDepName());
				tempNode.setChilds(setChildRecursion(deptList,tempDep.getId(),needCount));
				tempNodeList.add(tempNode);
			}
		}
		if(needCount){
			TreeNode<String> conutNode=new TreeNode<String>("合计");
			tempNodeList.add(conutNode);
		}
		return tempNodeList;
	}
	
	//通过部门id构建
	private List<TreeNode<DepartmentEntity>> buildDepartmentsTree(Integer departmentId){
		List<TreeNode<DepartmentEntity>> result = new ArrayList<TreeNode<DepartmentEntity>>();
		List<DepartmentEntity> departmentsList = dao.getDepartmentsByTreeFatherId(null);
		for (DepartmentEntity department:departmentsList) {
			//父级是售后服务中心
			if(department.getFatherId().equals(departmentId)){
				TreeNode<DepartmentEntity> departmentNode = new TreeNode<DepartmentEntity>(department);
				List<TreeNode<DepartmentEntity>> secondDepartLists = new ArrayList<TreeNode<DepartmentEntity>>();
				for (DepartmentEntity secdepartment:departmentsList) {
					if(secdepartment.getFatherId().equals(department.getId())){
						TreeNode<DepartmentEntity> secondDepartmentNode = new TreeNode<DepartmentEntity>(secdepartment);
						secondDepartLists.add(secondDepartmentNode);
					}
				}
				departmentNode.setChilds(secondDepartLists);
				result.add(departmentNode);
			}
		}
		return result;
	}
	
	@Override
    public List<TreeNode<DepartmentEntity>> getDepartTreeByDeptId(Integer departmentId) {
		List<TreeNode<DepartmentEntity>>  departUsersTree = (List<TreeNode<DepartmentEntity>>) MemcachesUtil.getObj("DEPARTMENTTREE_"+departmentId);
        if(departUsersTree == null) {
        	departUsersTree = buildDepartmentsTree(departmentId);
            MemcachesUtil.setInToday("DEPARTMENTTREE_"+departmentId, departUsersTree);
        }
        return departUsersTree;
    }
	
	@Override
	public Integer getBussinessIdByUserId(Integer userId){
		return dao.getBussinessIdByUserId(userId);
	}
}
