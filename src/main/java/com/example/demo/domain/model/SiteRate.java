package com.example.demo.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

/*
 * サイト料金
 */
@Data
public class SiteRate {

	/** サイトタイプID */
	private int siteTypeId;
	
	/** 料金形態ID */
	private int rateTypeId;
	
	/** チェックイン日 */
	private LocalDate dateFrom;
	
	/** SiteTypeの情報 */
	private SiteType siteType;
	
	/** RateTypeの情報 */
	private RateType rateType;
	
	/** 料金 */
	private BigDecimal rate;
	
	/** 税率 */
	private BigDecimal taxRate;
}
