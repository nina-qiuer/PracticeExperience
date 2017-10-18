package com.tuniu.gt.frm.service.system.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;
import tuniu.frm.service.Common;

import com.opensymphony.xwork2.ognl.OgnlValueStack;
import com.tuniu.gt.complaint.entity.AppointManagerEntity;
import com.tuniu.gt.complaint.service.IAppointManagerService;
import com.tuniu.gt.frm.dao.impl.SessionDao;
import com.tuniu.gt.frm.dao.impl.UserDao;
import com.tuniu.gt.frm.entity.SessionEntity;
import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.frm.service.common.ExEhcache;
import com.tuniu.gt.frm.service.privilege.IMenuService;
import com.tuniu.gt.frm.service.privilege.IRolePrivilegeService;
import com.tuniu.gt.frm.service.privilege.IRoleService;
import com.tuniu.gt.frm.service.privilege.IRoleUserAttrService;
import com.tuniu.gt.frm.service.system.IUserService;
import com.tuniu.gt.toolkit.MemcachesUtil;
import com.tuniu.gt.uc.entity.DepartmentEntity;
import com.tuniu.gt.uc.entity.DepuserMapEntity;
import com.tuniu.gt.uc.entity.JobEntity;
import com.tuniu.gt.uc.service.user.IDepartmentService;
import com.tuniu.gt.uc.service.user.IDepuserMapService;
import com.tuniu.gt.uc.service.user.IJobService;
import com.tuniu.gt.uc.vo.UserDepartmentVo;
@Service("frm_service_system_impl-user")
public class UserServiceImpl extends ServiceBaseImpl<UserDao> implements IUserService {
	private IDepartmentService departmentService;

	private Logger logger = Logger.getLogger(getClass());
	@Autowired
	@Qualifier("frm_dao_impl-session")
	private SessionDao sessionDao;
	
	@Autowired
	@Qualifier("frm_dao_impl-user")
	public void setDao(UserDao dao) {
		this.dao = dao;
	};
	
	@Override
	public UserEntity getUserByID(int id){
		return dao.get(id);
	}
	
	
	public IDepartmentService getDepartmentService() {
		return departmentService;
	}

	@Autowired
	@Qualifier("uc_service_user_impl-department")
	public void setDepartmentService(IDepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	
	@Autowired
	@Qualifier("uc_service_user_impl-job")
	private IJobService jobService;
	
	@Autowired
	@Qualifier("frm_service_privilege_impl-role_user_attr")
	private IRoleUserAttrService roleUserAttrService;
	
	@Autowired
	@Qualifier("frm_service_privilege_impl-role")
	private IRoleService roleService;
	
	@Autowired
	@Qualifier("frm_service_privilege_impl-role_privilege")
	private  IRolePrivilegeService rolePrivilegeService;
	
	@Autowired
	@Qualifier("frm_service_privilege_impl-menu")
	private IMenuService menuService;

	@Autowired
	@Qualifier("uc_service_user_impl-depuser_map")
	private IDepuserMapService  depMapService;
	
	public IDepuserMapService getDepMapService() {
		return depMapService;
	}

	public void setDepMapService(IDepuserMapService depMapService) {
		this.depMapService = depMapService;
	}

	@Autowired
	@Qualifier("complaint_service_complaint_impl-appoint_manager")
	private IAppointManagerService appointManagerService;
	
	
	public UserEntity getUserBySessionid(String sid,Integer uid,String pwd) {
		if(uid== null || uid.equals("")) {
			return null;
		}
		SessionEntity sessionEntity = (SessionEntity)sessionDao.get(sid);
		UserEntity userEntity;
		int needUpdate = 1;
		if(null == sessionEntity) { //没有查询到
			userEntity =  getFromUser(sid,uid,pwd);
			
		} else if(!sessionEntity.getUid().equals(uid)) { 
			sessionDao.delete(sessionEntity.getUid());
			userEntity =  getFromUser(sid,uid,pwd);
			
		}else {
			
			userEntity =  (UserEntity)Common.unserialize(sessionEntity.getUserInfo());
			needUpdate =  sessionEntity.getNeedUpdate();
		}
		
		if(userEntity != null) {
			userEntity.setNeedUpdate(needUpdate);
		}
		return userEntity;
	
	}
	
	private UserEntity getFromUser(String sid,Integer uid,String pwd) {
		if(uid != null ) {
			sessionDao.delete(uid); 
			if(pwd != null) {
				Map<String, Object> sqlParamMap = new HashMap<String, Object>();
				sqlParamMap.put("id", uid);
				sqlParamMap.put("password", pwd);
				sqlParamMap.put("del_flag", 0);
				UserEntity userEntity =  (UserEntity)dao.fetchOne(sqlParamMap);
				if(userEntity != null) {
					//将该用户进session表 
					SessionEntity sessionEntity = new SessionEntity();
					sessionEntity.setUid(uid);
					sessionEntity.setId(sid);
					sessionEntity.setUserInfo(Common.serialize(userEntity));
					sessionDao.insert(sessionEntity);
				} 
				
				return userEntity;
			}
		}
		return null;
	}
	
	
	
	
	/**
	 * xml,数据库
	 * @param uid
	 */
	public Map<String,List<Map<String, String>>> loadUserPrivileges(UserEntity userEntity) {
		Map<String,List<Map<String, String>>> ret = new HashMap<String, List<Map<String,String>>>();
		//获取用户角色
		if(userEntity.getNeedUpdate() > 0 ) {
			
			
			//更新needupdate值为0
			Map<String,Object> sessionMap = new HashMap<String, Object>();
			sessionMap.put("needUpdate", 0);
			sessionMap.put("uid", userEntity.getId()); 
			sessionDao.update(sessionMap);
			
		} else {
			ret = (Map<String,List<Map<String, String>>>) ExEhcache.get("user_privileges",userEntity.getId()); 
		}
		
		return ret;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> loadAllUser() {
		return (List<Map<String, Object>>)dao.fetchListMap();
	}


	@Override
	public List<UserEntity> getUsersByJob(int jobId) {
		Map<String, Object> sqlParams = new HashMap<String, Object>();
		sqlParams.put("job_id", jobId);
		List<UserEntity> users = (List<UserEntity>) dao.fetchList(sqlParams);
		return users;
	}
	
	@Override
	public List<UserEntity> getUsersByJob(String jobIds) {
		Map<String, Object> sqlParams = new HashMap<String, Object>();
		sqlParams.put("job_ids", jobIds);
		List<UserEntity> users = (List<UserEntity>) dao.fetchList(sqlParams);
		return users;
	}
	
	
	@Override
	public List<UserEntity> getUsers(String queryColumns,String positionIds) {
		Map<String, Object> sqlParams = new HashMap<String, Object>();
		sqlParams.put(queryColumns, positionIds);
		sqlParams.put("delFlag", 0);
		List<UserEntity> users = (List<UserEntity>) dao.fetchList(sqlParams);
		return users;
	}


	@Override
	public List<UserEntity> getUsersByDepartmentId(int departmentId) {
		// Get Department by id
		List<UserEntity> users=new ArrayList<UserEntity>();
		Map<String, Object> sqlParams = new HashMap<String, Object>();
		sqlParams.put("id", departmentId);
		DepartmentEntity department = (DepartmentEntity) departmentService.get(sqlParams);
		if(department!=null){
		  String treeFatherId = department.getTreeFatherId();
		  String treeId= department.getTreeId();
		
		  users = dao.getUsersByDepartmentTreeFatherId(treeFatherId+treeId);
		}
		return users;
	}


	@Override
	public List<UserEntity> getSameGroupUsers(UserEntity user) {
		int departmentId = user.getDepId();
		
		return getUsersByDepartmentId(departmentId);
	}


	@Override
	public List<UserEntity> getAllSameGroupUsers(UserEntity user) {
		List<DepuserMapEntity> depUserList = depMapService.getDepIdByUser(user.getId());
		List<UserEntity> userList = new ArrayList<UserEntity>();
		if (depUserList != null && depUserList.size() > 0) {
			int depCount = depUserList.size();
			for (int i=0; i<depCount; i++) {
				List<UserEntity> userListTemp = getUsersByDepartmentId(depUserList.get(i).getDepId());
				if (userListTemp != null && userListTemp.size() > 0) {
					userList.addAll(userListTemp);
				}
			}
		} else {
			userList = this.getSameGroupUsers(user);
		}
		return userList;
	}

	@Override
	public UserEntity getUserByCrmCookieUid(int uid, String pwd) {
		Map<String, Object> sqlParamMap = new HashMap<String, Object>();
		sqlParamMap.put("id", uid);
		sqlParamMap.put("del_flag", 0);
		UserEntity userEntity =  (UserEntity)dao.fetchOne(sqlParamMap);
		return userEntity;
	}

    /**
     * 判断添加的员工承担责任人是否存在，若存在，返回1，不存在返回0。
     */
    public int checkEmployInfo(String name) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("realName", name);
        List<UserEntity> users = (List<UserEntity>)dao.fetchList(paramMap);
        if (users != null && users.size() > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     *由realname得到用户
     */
    @SuppressWarnings("unchecked")
	public UserEntity getUserByName(String name) {
    	UserEntity user = null;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("realName", name);
        List<UserEntity> users = (List<UserEntity>)dao.fetchList(paramMap);
        if (users != null && users.size() > 0) {
        	user = users.get(0);
        }
        return user;
    }
  
    /**
     * 赔款专用  由realname得到用户
     */
	public UserEntity getUserByRealName(String name) {
		
		UserEntity user = null;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("realName", name);
        paramMap.put("delFlag", 0);
        List<UserEntity> users = (List<UserEntity>)dao.fetchList(paramMap);
        if (users != null && users.size() > 0) {
        	user = users.get(0);
        }
        return user;
    }
    
    /**
	 * 获取部门信息配置的部门所有人员列表
	 */
	public List<UserEntity> getComplaintDepUsers() {
		List<UserEntity> returnList = new ArrayList<UserEntity>();
		String depIdStr = "";
		List<AppointManagerEntity> complaintDepList = appointManagerService.getListByType(AppointManagerEntity.COMPLAINT_DEPARTMENT);
		for (AppointManagerEntity appointManagerEntity : complaintDepList) {
			depIdStr += appointManagerEntity.getDepartmentId() + ",";
		}
		depIdStr = depIdStr.substring(0, depIdStr.length()-1);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("depIdStr", depIdStr);
		returnList = (List<UserEntity>) dao.fetchList(param);
		
		return returnList;
	}

	@Override
	public List<UserEntity> getUsersOnlyByDepartmentId(int departmentId) {
		List<UserEntity> userList = dao.getUsersByDepartmentId(departmentId);
		return userList;
	}


	@Override
	public List<UserEntity> selectTransferUsers(int type) {
		List<UserEntity> returnList = new ArrayList<UserEntity>();
		List<AppointManagerEntity> complaintDepList = appointManagerService.getListByType(type);
		String userIds = "";
		for (AppointManagerEntity appointManagerEntity : complaintDepList) {
			userIds += appointManagerEntity.getUserId() + ",";
		}
		if(!"".equals(userIds)){
			userIds = userIds.substring(0, userIds.length()-1);
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("ids", userIds);
			returnList = (List<UserEntity>) dao.fetchList(param);
		}

		return returnList;
	}
	
	@Override
	public List<UserEntity> getManageByDepartmentId(int departmentId) {
		List<UserEntity> userList = dao.getManageByDepartmentId(departmentId);
		return userList;
		
	}

	@Override
	public List<UserEntity> getCustomerDirectorByCustomerName(String name) {
		List<UserEntity> list = new ArrayList<UserEntity>();
		
		UserEntity user = getUserByName(name);
		if (user!=null) {
			
			DepartmentEntity department = departmentService.getDepartmentById(user.getDepId());
			DepartmentEntity superDepartment = departmentService.getDepartmentById(department.getFatherId());
			while(superDepartment.getFatherId()>0){
				
				List<UserEntity> userList = this.getManageByDepartmentId(superDepartment.getId());
				for (UserEntity userEntity : userList) {
					if (userEntity.getJobId() == 143 || userEntity.getJobId() == 374 || userEntity.getJobId() == 503) {
						list.add(userEntity);
					}
				}
				
				superDepartment = departmentService.getDepartmentById(superDepartment.getFatherId());
				
			}
		}
		
		return list;
	}

	@Override
	public List<UserEntity> getDeputyManagerByProducterName(String name) {
		List<UserEntity> list = new ArrayList<UserEntity>();
		UserEntity user = getUserByName(name);
		if (user != null) {
			DepartmentEntity dept = getBusinessDepartment(user, 1);
			List<UserEntity> managerList = getManageByDepartmentId(dept.getId());
			if (null != managerList) {
				for (UserEntity manager : managerList) {
					if (isInJobs(manager.getJobId(), "副总经理")) {
						list.add(manager);
					}
				}
			}
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	private boolean isInJobs(int jobId, String jobName) {
    	boolean flag = false;
    	Map<String, Object> params = new HashMap<String, Object>();
		params.put("jobName", jobName);
		List<JobEntity> jobList = (List<JobEntity>) jobService.fetchList(params);
		List<Integer> jobIdList = new ArrayList<Integer>();
		for (JobEntity job : jobList) {
			jobIdList.add(job.getId());
		}
		
		params.clear();
		params.put("jobName", "副" + jobName);
		List<JobEntity> jobList2 = (List<JobEntity>) jobService.fetchList(params);
		List<Integer> jobIdList2 = new ArrayList<Integer>();
		for (JobEntity job2 : jobList2) {
			jobIdList2.add(job2.getId());
		}
		
		if (jobIdList.contains(jobId) && !jobIdList2.contains(jobId)) {
			flag = true;
		}
		
		return flag;
    }

	@Override
	public List<UserEntity> getManageByDepartmentName(String departmentName) {
	
		List<UserEntity> userList = dao.getManageByDepartmentName(departmentName);
		return userList;
	
	}

	@Override
	public DepartmentEntity getBusinessDepartment(UserEntity userEntity, int depth) {
		DepartmentEntity dep = null ;
		if(userEntity != null && userEntity.getDepId() > 0){
			int depId = userEntity.getDepId();
			do {
				dep = departmentService.getDepartmentById(depId);
				if(null!=dep){
					
					depId = dep.getFatherId();
					
				}else{
					
					break;
				}
			
			} while (dep.getDepth() > depth);
		}
		return dep;
	}

	@Override
	public boolean login(Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response) {
		boolean success = false;
		UserEntity user = (UserEntity) dao.fetchOne(paramMap);
		if (user != null) {
			Common.setCookie("uid", user.getId().toString(), 86400, response);
			Common.setCookie("pwd", user.getPassword(), 86400, response);
			
			String userStr = JSONObject.fromObject(user).toString();
			logger.info("登录用户信息："+userStr);
			MemcachesUtil.setInToday("CMP_USERAUTH_USER_" + user.getId(), userStr);
			
			OgnlValueStack stack=(OgnlValueStack)request.getAttribute("struts.valueStack");
			stack.setValue("user", user);
			request.getSession().setAttribute("user", user);
			
			success = true;
			
			// TODO 授权并存入缓存
			
		}
		return success;
	}

	@Override
	public UserEntity getDeptGeneralManager(int deptId) {
		UserEntity depManager = new UserEntity();
		List<UserEntity> managerList = getManageByDepartmentId(deptId);
		for (UserEntity manager : managerList) {
			if (isInJobs(manager.getJobId(), "总经理")) {
				depManager = manager;
				break;
			}
		}
		return depManager;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserEntity> getCmpUsers() {
		List<UserEntity> users = (List<UserEntity>) MemcachesUtil.getObj("CMP_users");
		if (null == users || 0 == users.size()) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("delFlag", 0);
			users = (List<UserEntity>) fetchList(params);
			MemcachesUtil.setInToday("CMP_users", users);
		}
		return users;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserEntity> getCmpUnUsers() {
		List<UserEntity> users = (List<UserEntity>) MemcachesUtil.getObj("CMP_UnUsers");
		if (null == users || 0 == users.size()) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("delFlag", 1);
			users = (List<UserEntity>) fetchList(params);
			MemcachesUtil.setInToday("CMP_UnUsers", users);
		}
		return users;
	}
	
	@Override
	public List<UserEntity> getCmpAllUsers() {
		
		 List<UserEntity> userList = getCmpUsers();
		 List<UserEntity> userUnList = getCmpUnUsers();
		 userList.addAll(userUnList);
		 return userList;
	}
	
	@Override
	public List<UserEntity> getSameDeptUsers(Integer deptId) {
		Set<DepartmentEntity> deptSet = departmentService.getAllDepartmentByFatherId(deptId);
		List<UserEntity> managerList = new ArrayList<UserEntity>();
		for (DepartmentEntity dept : deptSet) {
			managerList.addAll(getUsersByDepartmentId(dept.getId()));
		}
		return managerList;
	}

    @Override
    public UserDepartmentVo getUserDepartmentVoByRealName(String realName) {
        UserDepartmentVo vo = new UserDepartmentVo();
        UserEntity user = getUserByRealName(realName);
        if(user.getId()!=null){
            vo.setUserId(user.getId());
            vo.setUserName(user.getUserName());
            vo.setRealName(realName);
            vo.setWorkNum(user.getWorknum());
            vo.setDepId(user.getDepId());
            
            Integer depId = user.getDepId();
            if(depId!=0){
                departmentService.fillUserDepartmentVo(vo);
            }
            
        }
        
        return vo;
    }
	
    @Override
	public List<UserEntity> getManageByFatherDepId(int departmentId) {
    	return dao.getManageByFatherDepId(departmentId);
		
		
	}
    
    @Override
    public UserDepartmentVo getUserDepartmentVoByUserId(Integer userId){
		return dao.getUserDepartmentVoByUserId(userId);
    }

	@Override
	public List<Map<String, Object>> getUserNamesInJSON() {
		List<Map<String, Object>> userList = (List<Map<String, Object>>)MemcachesUtil.getObj("allUser");
		
		if(null == userList || userList.isEmpty()){
			userList = dao.getAllUsersWithoutDel();
			MemcachesUtil.setInToday("allUser", userList);
			
		}
		return userList;
	}
    
	/**
     * 根据人员id获取同组除去自己的人员
     * @return
     */
	@Override
	public List<UserEntity> getSameDepUsersWithoutSelfByUserId(Integer userId) {
		return dao.getSameDepUsersWithoutSelf(userId);
	}
	
	@Override
	public String getExtensionByUserId(int id){
		UserEntity user = getUserByID(id);
		if(user==null){
			return "99999";
		}
		return user.getExtension();
	}
}
