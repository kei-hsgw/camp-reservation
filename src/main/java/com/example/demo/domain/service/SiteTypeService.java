package com.example.demo.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.model.SiteType;
import com.example.demo.repository.SiteTypeMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SiteTypeService {

	private final SiteTypeMapper siteTypeMapper;
	
	/**
	 * サイトタイプ全件取得
	 * @return
	 */
	public List<SiteType> findAllSiteType() {
		return siteTypeMapper.findAllSiteType();
	}
}
