package com.zqkj.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zqkj.utils.ueditor.ActionEnter;

@Controller
@RequestMapping("/security/ueditor/")
public class UeditorController {
	
	@Value("${ueditor.uploadPath}")
	private String uploadPath ;
	
	@ResponseBody
	@RequestMapping(value = "/controller.jsp")
	public String controller(HttpServletRequest request){
		String exec = new ActionEnter(request, uploadPath).exec();
		return exec;
	}
}
