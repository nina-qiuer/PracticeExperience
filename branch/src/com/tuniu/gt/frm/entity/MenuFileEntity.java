package com.tuniu.gt.frm.entity; 
import java.io.Serializable;
import java.util.Date;


public class MenuFileEntity extends EntityBase  implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 2238931180003909812L;

	private String methods="";

	private String all_methods="";

	private String file_name="";



	private Date update_time=new Date();

	private Date add_time=new Date();



	public String getMethods() {
		return methods;
	}
	public void setMethods(String methods) {
		this.methods = methods;
	}

	public String getAll_methods() {
		return all_methods;
	}
	public void setAll_methods(String all_methods) {
		this.all_methods = all_methods;
	}

	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}


	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public Date getAdd_time() {
		return add_time;
	}
	public void setAdd_time(Date add_time) {
		this.add_time = add_time;
	}
	
}
