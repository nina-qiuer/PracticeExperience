/**
 * 
 */
package com.tuniu.gt.toolkit;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author jiangye
 *
 */
public class MailUtil {
    private static ExecutorService exec = Executors.newCachedThreadPool();
    
    public static void sendMail(MailerThread mailerThread){
     //   exec.execute(mailerThread);
    }
}
