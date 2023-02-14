package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.model.SiteType;

@Mapper
public interface SiteTypeMapper {

	/**
	 * サイトタイプ全件取得
	 * @return
	 */
	public List<SiteType> findAllSiteType();
	
	/**
	 * サイトタイプ取得(ID指定)
	 * @param siteTypeId サイトタイプID
	 * @return
	 */
	public Optional<SiteType> findBySiteTypeId(int siteTypeId);
}
