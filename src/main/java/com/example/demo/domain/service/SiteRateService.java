package com.example.demo.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.model.SiteRatePivot;
import com.example.demo.repository.SiteRateMapper;

import lombok.RequiredArgsConstructor;

/*
 * サイト料金 Domain Service
 */
@RequiredArgsConstructor
@Transactional
@Service
public class SiteRateService {

	private final SiteRateMapper siteRateMapper;
	
	/**
	 * サイトタイプ別料金情報取得
	 * @return
	 */
	public List<SiteRatePivot> findAllSiteRatePivot() {
		return siteRateMapper.findAllSiteRatePivot();
	}
}
