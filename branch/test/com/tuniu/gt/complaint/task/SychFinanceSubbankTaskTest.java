/**
 * 
 */
package com.tuniu.gt.complaint.task;

import org.junit.Test;

import tuniu.frm.service.Bean;

import com.tuniu.gt.TestCaseExtend;
import com.tuniu.gt.complaint.scheduling.SynchSubbankTask;

public class SychFinanceSubbankTaskTest extends TestCaseExtend{
    
    private SynchSubbankTask subbankTask =  (SynchSubbankTask)Bean.get("synchSubbankTask");
    
    @Test
    public void testTask() throws Exception {
        assertNotNull(subbankTask);
    }
    
    @Test
    public void testSych() throws Exception {
        subbankTask.synchSubbank();
    }
}
