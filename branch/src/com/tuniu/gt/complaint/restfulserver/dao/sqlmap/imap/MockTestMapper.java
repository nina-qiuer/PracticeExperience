/**
 * 
 */
package com.tuniu.gt.complaint.restfulserver.dao.sqlmap.imap;

import org.springframework.stereotype.Repository;

/**
 * @author jiangye
 *
 */
@Repository("mock_test")
public interface MockTestMapper {
        String getJsonById(Integer id);
}
