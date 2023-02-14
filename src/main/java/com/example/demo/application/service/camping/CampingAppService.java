package com.example.demo.application.service.camping;

import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.example.demo.domain.model.SiteType;
import com.example.demo.domain.service.SiteTypeService;
import com.example.demo.exception.SystemException;

import lombok.RequiredArgsConstructor;

/*
 * キャンプ情報表示 Application Service
 */
@RequiredArgsConstructor
@Service
public class CampingAppService {

	private final SiteTypeService siteTypeService;
	private final MessageSource messageSource;
	
	/**
	 * サイトタイプ全件取得
	 * @return
	 */
	public List<SiteType> findAllSiteType() {
		return siteTypeService.findAllSiteType();
	}
	
	/**
	 * サイトタイプ名取得
	 * @param siteTypeId サイトタイプID
	 * @return
	 */
	public String findSiteTypeName(int siteTypeId) {
		
		return siteTypeService.findBySiteTypeId(siteTypeId)
				.map(st -> st.getName())
				.orElseThrow(() -> new SystemException(messageSource.getMessage("exception.dataNotFound", new String[] {String.valueOf(siteTypeId)}, Locale.JAPAN)));
	}
}
