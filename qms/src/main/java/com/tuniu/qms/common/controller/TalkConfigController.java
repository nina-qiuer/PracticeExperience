package com.tuniu.qms.common.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuniu.qms.common.dto.TalkConfigDto;
import com.tuniu.qms.common.model.TalkConfig;
import com.tuniu.qms.common.service.TalkConfigService;

@Controller
@RequestMapping("/common/talkConfig")
public class TalkConfigController {

    @Autowired
    private TalkConfigService service;

    @RequestMapping("/list")
    public String list(HttpServletRequest request) {
        List<TalkConfig> talkConfigList  = service.list(null);
        request.setAttribute("dataList", talkConfigList);
        return "common/talkConfigList";
    }

    @RequestMapping("/toAdd")
    public String toAdd(HttpServletRequest request) {
        TalkConfig talkConfig = new TalkConfig();
        request.setAttribute("talkConfig", talkConfig);
        return "common/talkConfigForm";
    }

    @RequestMapping("/add")
    @ResponseBody
    public String add(TalkConfig model, HttpServletRequest request) {
        service.add(model);
        return "success";
    }

    @RequestMapping("/{id}/toUpdate")
    public String toUpdate(@PathVariable("id") Integer id, HttpServletRequest request) {
        TalkConfig talkConfig = service.get(id);
        request.setAttribute("talkConfig", talkConfig);
        return "common/talkConfigForm";
    }

    @RequestMapping("/update")
    @ResponseBody
    public String update(TalkConfig model, HttpServletRequest request) {
        service.update(model);
        return "success";
    }
    
    @RequestMapping("/toChoose")
	public String toChoose(HttpServletRequest request) {
		
        List<TalkConfig> talkConfigList  = service.list(new TalkConfigDto());
        request.setAttribute("dataList", talkConfigList);
      
        return "common/chooseTalkConfig";
	}
}