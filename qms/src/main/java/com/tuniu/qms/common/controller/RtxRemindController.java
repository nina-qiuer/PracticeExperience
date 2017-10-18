package com.tuniu.qms.common.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuniu.qms.common.dto.RtxRemindDto;
import com.tuniu.qms.common.model.RtxRemind;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.RtxRemindService;

@Controller
@RequestMapping("/common/rtxRemind")
public class RtxRemindController {

    @Autowired
    private RtxRemindService service;

    @RequestMapping("/list")
    public String list(RtxRemindDto dto, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("loginUser");
        dto.setAddPerson(user.getRealName());
        List<RtxRemind> list = service.list(dto);
        request.setAttribute("dataList", list);
        return "common/rtxRemindList";
    }

    @RequestMapping("/toAdd")
    public String toAdd(RtxRemindDto dto, HttpServletRequest request) {
        RtxRemind rtxRemind = new RtxRemind();
        request.setAttribute("rtxRemind", rtxRemind);
        return "common/rtxRemindForm";
    }

    @RequestMapping("/add")
    @ResponseBody
    public String add(RtxRemind rtxRemind, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("loginUser");
        rtxRemind.setAddPerson(user.getRealName());
        rtxRemind.setUid(user.getId().toString());
        service.add(rtxRemind);
        return "success";
    }

    @RequestMapping("/{id}/delete")
    @ResponseBody
    public String delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return "success";
    }

    @RequestMapping("/{id}/toUpdate")
    public String toUpdate(@PathVariable("id") Integer id, HttpServletRequest request) {
        RtxRemind rtxRemind = service.get(id);
        request.setAttribute("rtxRemind", rtxRemind);
        return "common/rtxRemindForm";
    }

    @RequestMapping("/update")
    @ResponseBody
    public String update(RtxRemind rtxRemind, HttpServletRequest request) {
        service.update(rtxRemind);
        return "success";
    }

}