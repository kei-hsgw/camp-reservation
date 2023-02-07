package com.example.demo.presentation.camping;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.application.service.camping.CampingAppService;

import lombok.RequiredArgsConstructor;

/*
 * キャンプ情報表示・予約
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/camping")
public class CampingController {
	
	private final CampingAppService campingAppService;
	
	/**
	 * サイトタイプ一覧表示
	 * @param model
	 * @return
	 */
	@GetMapping("/siteTypes")
	public String siteTypeList(Model model) {
		
		model.addAttribute("siteTypeList", campingAppService.findAllSiteType());
		
		return "camping/siteTypes";
	}
}
