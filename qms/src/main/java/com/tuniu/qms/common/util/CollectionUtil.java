/**
 * 
 */
package com.tuniu.qms.common.util;

import java.util.Collection;

import org.springframework.util.CollectionUtils;

/**
 * @author jiangye
 *
 */
public class CollectionUtil extends CollectionUtils {
    public static boolean isNotEmpty(Collection c) {
        return c!=null&&c.size()>0;
    }
}
