package com.tuniu.gt;  


/** 
 * Userinfo entity. @author MyEclipse Persistence Tools 
 */  

public class Userinfo implements java.io.Serializable {  

    // Fields  

    private Integer id;  
    private String name;  
    private String pass;  
    private String lastname;  
    private String addres;  
    private String remark;  

    // Constructors  

    /** default constructor */  
    public Userinfo() {  
    }  

    /** full constructor */  
    public Userinfo(String name, String pass, String lastname, String addres,  
            String remark) {  
        this.name = name;  
        this.pass = pass;  
        this.lastname = lastname;  
        this.addres = addres;  
        this.remark = remark;  
    }  

    // Property accessors  

    public Integer getId() {  
        return this.id;  
    }  

    public void setId(Integer id) {  
        this.id = id;  
    }  

    public String getName() {  
        return this.name;  
    }  

    public void setName(String name) {  
        this.name = name;  
    }  

    public String getPass() {  
        return this.pass;  
    }  

    public void setPass(String pass) {  
        this.pass = pass;  
    }  

    public String getLastname() {  
        return this.lastname;  
    }  

    public void setLastname(String lastname) {  
        this.lastname = lastname;  
    }  

    public String getAddres() {  
        return this.addres;  
    }  

    public void setAddres(String addres) {  
        this.addres = addres;  
    }  

    public String getRemark() {  
        return this.remark;  
    }  

    public void setRemark(String remark) {  
        this.remark = remark;  
    }  

}  