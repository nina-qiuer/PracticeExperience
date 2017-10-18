/**
 * 
 */
package com.tuniu.gt.complaint.enums;

import com.tuniu.gt.complaint.util.ComplaintUtil;

/**
 * @author jiangye
 *
 */
public enum AssignType {
    
    AUTO(0,"自动分单","complaint_",1),
    ROB(1,"抢单","complaint_rob_",3);
    
    private int type;
    private String description;
    private String memPre;
    private int persistentType; //持久化类型  对应record表里的type
    
     AssignType(int type, String description,String memPre,int persistentType) {
        this.type = type;
        this.description = description;
        this.memPre = memPre;
        this.persistentType = persistentType;
    }
     
     public String getDescription(){
         return description;
     }
     
     public Integer getType(){
         return type;
     }
     
     public String getMemPre(){
         return memPre;
     }
     
     public int getPersistentType(){
         return persistentType;
     }
     
     public  void  recordNums(int userId){
          ComplaintUtil.recordUserNums(userId,memPre);
     }
     
     public void recordUserOrders(int userId, String orderId){
         ComplaintUtil.recordUserOrders(userId, orderId, memPre);
     }
    
}
