package com.example.demo.presentation.camping;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	/**
	 * スケジュール表示
	 * @param siteTypeId
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/schedule", params = "siteTypeId")
	public String schedule(@RequestParam("siteTypeId") int siteTypeId, Model model) {
		
		model.addAttribute("siteTypeId", siteTypeId);
		model.addAttribute("siteTypeName", campingAppService.findSiteTypeName(siteTypeId));
		
		return "camping/schedule";
	}
}
