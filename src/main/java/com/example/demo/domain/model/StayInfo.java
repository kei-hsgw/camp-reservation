package com.example.demo.domain.model;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;

/*
 * 宿泊情報
 */
@Data
public class StayInfo {

	/** サイトタイプID */
	private int siteTypeId;
	
	/** チェックイン日 */
	private LocalDate dateFrom;
	
	/** 滞在日数 */
	private int stayDays;
	
	/** 人数 */
	private int numberOfPeople;
	
	/** サイトタイプ名 */
	private String siteTypeName;
	
	/**
	 * 予約受付期間判定
	 * 予約受付期間は翌日〜90日間
	 * 予約受付期間外の日程が含まれている場合はfalse, それ以外はtrue
	 * @return
	 */
	public boolean isValidPeriod() {
		
		// 今日
		LocalDate today = LocalDate.now();
		// 翌日
		LocalDate tomorrow = today.plusDays(1);
		// 90日後
		LocalDate day90Later = today.plusDays(90);
		//チェックアウト日
		LocalDate dateTo = this.getDateTo();
		
		return !dateFrom.isBefore(tomorrow) && !dateTo.isAfter(day90Later);
	}
	
	/**
	 * 人数上限判定
	 * 引数で渡されるサイトの上限人数を上回る場合はfalse, それ以外はtrue
	 * @param upperLimit サイトの上限人数
	 * @return 判定値
	 */
	public boolean isValidNumberOfPeople(int upperLimit) {
		return numberOfPeople <= upperLimit;
	}
	
	/**
	 * 宿泊日リスト取得
	 * @return
	 */
	public List<LocalDate> getDaysOfStay() {
		return dateFrom.datesUntil(dateFrom.plusDays(stayDays)).collect(Collectors.toList());
	}
	
	/**
	 * チェックアウト日取得
	 * @return
	 */
	public LocalDate getDateTo() {
		return dateFrom.plusDays(stayDays - 1);
	}
}
