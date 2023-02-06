package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.model.SiteRatePivot;

@Mapper
public interface SiteRateMapper {

	/**
	 * サイトタイプ別料金情報取得
	 * @return
	 */
	List<SiteRatePivot> findAllSiteRatePivot();
}
