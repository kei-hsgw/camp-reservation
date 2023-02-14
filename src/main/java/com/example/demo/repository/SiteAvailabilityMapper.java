package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.model.SiteAvailability;

@Mapper
public interface SiteAvailabilityMapper {

	/**
	 * サイト空き状況検索
	 * @param siteTypeId サイトタイプID
	 * @param from 取得開始日
	 * @param to 取得終了日
	 * @return
	 */
	public List<SiteAvailability> findSiteAvailability(int siteTypeId, LocalDate from, LocalDate to);
	
	/**
	 * サイト空き状況残数
	 * @param siteTypeId サイトタイプID
	 * @param from 取得開始日
	 * @param to 取得終了日
	 * @return
	 */
	public int countDaysNotAvailable(int siteTypeId, LocalDate from, LocalDate to);
	
	/**
	 * サイト空き状況減算処理
	 * @param siteTypeId サイトタイプID
	 * @param from 取得開始日
	 * @param to 取得終了日
	 * @return
	 */
	public int reduceAvailabilityCount(int siteTypeId, LocalDate from, LocalDate to);
}
