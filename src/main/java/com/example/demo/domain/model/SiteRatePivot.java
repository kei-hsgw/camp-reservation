package com.example.demo.domain.model;

import java.math.BigDecimal;

import lombok.Data;

/*
 * サイト料金表
 */
@Data
public class SiteRatePivot {

	/** サイトタイプID */
	private int siteTypeId;
	
	/** サイトタイプ名 */
	private String siteTypeName;
	
	/** 通常料金 */
	private BigDecimal rateBasic;
	
	/** ハイシーズン料金 */
	private BigDecimal rateHighSeason;
}
