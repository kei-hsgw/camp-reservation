package com.example.demo.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 予約詳細
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDetail {

	/** 予約ID */
	private Integer reservationId;
	
	/** 予約日 */
	private LocalDate reservationDate;
	
	/** サイト料金 */
	private BigDecimal siteRate;
	
	/** 税率 */
	private BigDecimal taxRate;
	
	/** 料金形態名 */
	private String rateTypeName;
	
	public ReservationDetail(LocalDate reservationDate, SiteRate siteRate) {
		
		if (reservationDate == null || siteRate == null) {
			throw new IllegalArgumentException("必須項目が設定されていません。");
		}
		
		this.reservationDate = reservationDate;
		this.siteRate = siteRate.getRate();
		this.taxRate = siteRate.getTaxRate();
		this.rateTypeName = siteRate.getRateType().getName();
	}
}
