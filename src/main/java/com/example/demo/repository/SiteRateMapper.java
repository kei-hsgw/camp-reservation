package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.model.SiteRate;
import com.example.demo.domain.model.SiteRatePivot;

@Mapper
public interface SiteRateMapper {

	/**
	 * サイトタイプ別料金情報取得
	 * @return
	 */
	public List<SiteRatePivot> findAllSiteRatePivot();
	
	/**
	 * サイト料金取得
	 * @param siteTypeId
	 * @param date
	 * @return
	 */
	public SiteRate findSiteRate(int siteTypeId, LocalDate date);
}
