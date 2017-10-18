/**
 * 
 */
package com.tuniu.gt.complaint.restfulserver;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONBuilder;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tuniu.gt.complaint.dao.sqlmap.imap.ClaimsAuditMap;
import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.restfulserver.dao.sqlmap.imap.MockTestMapper;
import com.tuniu.operation.platform.annotation.MethodRestful;
import com.tuniu.operation.platform.annotation.ParamRestful;
import com.tuniu.operation.platform.annotation.ReturnRestful;
import com.tuniu.operation.platform.annotation.TypeRestful;
import com.tuniu.trest.TRestException;
import com.tuniu.trest.TRestServer;

/**
 * @author jiangye
 */
@Controller
@RequestMapping("/mock")
@TypeRestful(desc = "Restful mock接口类", author = "姜野", version = "1.0")
public class RestMockServer {
    private static Logger logger = Logger.getLogger(RestMockServer.class);

    @Autowired
    @Qualifier("mock_test")
    private MockTestMapper mapper;
    
    @RequestMapping(value = "interface{id}")
    public void mockResponse(@PathVariable("id") Integer id,HttpServletResponse response) throws IOException {
        String responseStr = generateResultString(id);
        write(response, responseStr);
    }

    /**
     * 生成接口输出字符串
     */
    private String generateResultString(Integer id) {
        String resStr = "";
        try {
            resStr = Base64.encodeBase64String(mapper.getJsonById(id).getBytes("utf-8"));
        } catch(UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        }
        return resStr;
    }

    /**
     * 接口返回值写入输出流
     */
    private void write(HttpServletResponse response, String responseStr) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Methods", "*");
        try {
            response.getWriter().write(responseStr);
        } catch(IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

}
