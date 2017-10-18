package com.tuniu.gt.complaint.service.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import tuniu.frm.service.Bean;

import com.tuniu.gt.TestCaseExtend;
import com.tuniu.gt.complaint.scheduling.AssignCompalintList;
import com.tuniu.gt.complaint.service.IRobComplaintService;
import com.tuniu.gt.complaint.task.AssignTask;



public class AutoAssignServiceTest  extends TestCaseExtend 
{
		
		AssignCompalintList assignCmpTask = (AssignCompalintList) Bean.get("assignCompalintList"); 
		IRobComplaintService robComplaintService = (IRobComplaintService) Bean.get("complaint_service_complaint_impl-robComplaint"); 
		
		@Test
		public void testAssignForAir() throws Exception{
			assertNotNull(assignCmpTask);
//			assignCmpTask.assginInTravel();
		}
		
		@Test
        public void testRobComplaint() throws Exception {
		    /*AssignTask task1 = new AssignTask(7579, 3,robComplaintService);
		    AssignTask task2 = new AssignTask(8727, 2,robComplaintService);
		    Executor execute = Executors.newCachedThreadPool();
		    execute.execute(task1);
		    execute.execute(task2);*/
		    robComplaintService.robComplaint(7579, 3);
		    robComplaintService.robComplaint(8727, 3);
		    
        }
		
		@Test
        public void testRobTask() throws Exception {
		    AssignTask task1 = new AssignTask(7579, 3,robComplaintService);
            AssignTask task2 = new AssignTask(8727, 2,robComplaintService);
            ExecutorService service = Executors.newCachedThreadPool();
            service.execute(task1);
            service.execute(task2);
            service.shutdown();
            
        }
}

