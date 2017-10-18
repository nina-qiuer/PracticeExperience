/*
 * File Name:tspCommonUtils.java
 * Author:xuxuezhi
 * Date:2017年8月23日
 * Copyright (C) 2006-2017 Tuniu All rights reserved
 */
 
package com.tuniu.gt.complaint.tsp;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.tuniu.trest.TRestException;
import com.tuniu.trest.TRestServer;

public class tspCommonUtils
{
    private static Logger logger = Logger.getLogger(tspCommonUtils.class);
    
    public static JSONObject  getRestParams(HttpServletRequest request, HttpServletResponse response)
            throws TRestException {
        String data = new TRestServer(request, response).getRestData();

        return JSONObject.fromObject(data);
    }
    
    public static void write(HttpServletResponse response, Object object) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Methods", "*");
        try{
            response.getWriter().print(Base64.encodeBase64String(JSONObject.fromObject(object).toString().getBytes("UTF-8")));
        }
        catch(IOException ex){
            logger.error(ex.getMessage());
        }
    }
}
