package com.example.demo.domain.service;

import java.util.List;
import java.util.Optional;

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
	
	/**
	 * サイトタイプ取得(ID指定)
	 * @param siteTypeId
	 * @return
	 */
	public Optional<SiteType> findBySiteTypeId(int siteTypeId) {
		return siteTypeMapper.findBySiteTypeId(siteTypeId);
	}
}
