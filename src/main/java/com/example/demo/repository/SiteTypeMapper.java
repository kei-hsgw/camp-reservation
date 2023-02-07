package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.model.SiteType;

@Mapper
public interface SiteTypeMapper {

	/**
	 * サイトタイプ全件取得
	 * @return
	 */
	public List<SiteType> findAllSiteType();
}
