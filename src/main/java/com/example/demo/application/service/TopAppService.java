package com.example.demo.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.model.SiteRatePivot;
import com.example.demo.domain.service.SiteRateService;

import lombok.RequiredArgsConstructor;

/*
 * トップページ Application Service
 */
@RequiredArgsConstructor
@Service
public class TopAppService {

	private final SiteRateService siteRateService;
	
	/**
	 * サイトタイプ別料金情報取得
	 * @return
	 */
	public List<SiteRatePivot> findAllSiteRatePivot() {
		return siteRateService.findAllSiteRatePivot();
	}
}
