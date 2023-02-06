package com.example.demo.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.application.service.TopAppService;

import lombok.RequiredArgsConstructor;

/*
 * トップページ Controller
 */
@RequiredArgsConstructor
@Controller
public class TopController {

	private final TopAppService topAppService;
	
	/**
	 * トップページ表示
	 * @param model
	 * @return
	 */
	@GetMapping("/")
	public String top(Model model) {
		
		// サイトタイプ別料金情報取得
		model.addAttribute("siteRateList", topAppService.findAllSiteRatePivot());
		
		return "index";
	}
}
