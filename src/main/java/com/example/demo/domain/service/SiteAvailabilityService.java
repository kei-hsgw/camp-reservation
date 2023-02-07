package com.example.demo.domain.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.model.SiteAvailability;
import com.example.demo.repository.SiteAvailabilityMapper;

import lombok.RequiredArgsConstructor;

/*
 * サイト空き状況 Domain Service
 */
@RequiredArgsConstructor
@Service
public class SiteAvailabilityService {

	private final SiteAvailabilityMapper siteAvailabilityMapper;
	
	/**
	 * サイト空き状況検索
	 * @param sitetypeId
	 * @param from チェックイン日
	 * @param to チェックアウト日
	 * @return
	 */
	public List<SiteAvailability> findSiteAvailability(int sitetypeId, LocalDate from, LocalDate to) {
		return siteAvailabilityMapper.findSiteAvailability(sitetypeId, from, to);
	}
	
	/**
	 * サイト空き状況判定
	 * 指定した宿泊期間のサイト空き状況が満室の場合はfalse、それ以外はtrueを返す。
	 * @param siteTypeId
	 * @param from チェックイン日
	 * @param to チェックアウト日
	 * @return
	 */
	public boolean isSiteAvailableForPeriod(int siteTypeId, LocalDate from, LocalDate to) {
		return siteAvailabilityMapper.countDaysNotAvailable(siteTypeId, from, to) != 0;
	}
	
	/**
	 * サイト空き状況減算処理
	 * 指定した宿泊期間のサイト空き状況に対し、在庫を1減らす。
	 * @param siteTypeId
	 * @param from チェックイン日
	 * @param to チェックアウト日
	 * @return 減算した数
	 */
	public int reduceAvailabilityCount(int siteTypeId, LocalDate from, LocalDate to) {
		return siteAvailabilityMapper.reduceAvailabilityCount(siteTypeId, from, to);
	}
}
