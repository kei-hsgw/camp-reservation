package com.example.demo.application.service.camping;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.model.SiteType;
import com.example.demo.domain.service.SiteTypeService;

import lombok.RequiredArgsConstructor;

/*
 * キャンプ情報表示 Application Service
 */
@RequiredArgsConstructor
@Service
public class CampingAppService {

	private final SiteTypeService siteTypeService;
	
	/**
	 * サイトタイプ全件取得
	 * @return
	 */
	public List<SiteType> findAllSiteType() {
		return siteTypeService.findAllSiteType();
	}
	
	/**
	 * サイトタイプ名取得
	 * @param siteTypeId
	 * @return
	 */
	public String findSiteTypeName(int siteTypeId) {
		
		return siteTypeService.findBySiteTypeId(siteTypeId)
				.map(st -> st.getName())
				.orElseThrow(() -> new RuntimeException());
	}
}
