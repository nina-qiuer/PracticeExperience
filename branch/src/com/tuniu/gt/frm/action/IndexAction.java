package com.tuniu.gt.frm.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.tuniu.gt.frm.entity.MenuEntity;
import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.frm.service.privilege.IMenuService;





import tuniu.frm.core.FrmBase;


@Service("frm-index") 
@Scope("prototype")
public class IndexAction extends FrmBase {
	
	
	@Autowired
	@Qualifier("frm_service_privilege_impl-menu")
    private IMenuService menuService;

	private List<MenuEntity> menuList;
	
	
	
	


	/**
	 * 当前登录的用户
	 */
	protected UserEntity user;
	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	} 
	
	public IndexAction() {
		
//		ApplicationContext ctx = new ClassPathXmlApplicationContext("../applicationContext.xml");   
//		menuDao = (FrmMenuDao)ctx.getBean("dao_frmMenuDao"); 
//		tmpMenu = (FrmMenu)ctx.getBean("hbm_frmMenu"); 
		
//		menuList = menuDao.loadAll();
//		
//		for(int i = 0;i<menuList.size();i++) {
//			tmpMenu = menuList.get(i);
//			System.out.println(tmpMenu.getTree_father_id());
//		}
//		
		
//		ApplicationContext ctx = new ClassPathXmlApplicationContext("../applicationContext.xml");   
//		FrmPinyinDao pinyin_dao= (FrmPinyinDao)ctx.getBean("FrmPinyinDao");
//		FrmPinyin pinyin = (FrmPinyin)ctx.getBean("FrmPinyin");
//		
//		FrmPinyin.setName(this.strTemp); 
		
		
//		GenerateCode gCode = new GenerateCode();
//		gCode.makeAll(); 
	}
	
	
	
	@SuppressWarnings("unchecked")
	public String execute(){
		
			menuList = menuService.getMenuList();   
			HttpServletRequest request = ServletActionContext.getRequest();
			request.setAttribute("menuList", menuList); 
	
			return "index"; 
		
	}
}
