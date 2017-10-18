/**
 * 
 */
package com.tuniu.gt.complaint.task;

import com.tuniu.gt.complaint.service.IRobComplaintService;

public class AssignTask implements Runnable{
    
    int userId;
    int amount;
    IRobComplaintService robComplaintService = null; 
    
    
    public AssignTask(int userId, int amount,IRobComplaintService robComplaintService) {
        super();
        this.userId = userId;
        this.amount = amount;
        this.robComplaintService = robComplaintService;
    }



    @Override
    public void run() {
        robComplaintService.robComplaint(userId, amount);
    }
    
}
