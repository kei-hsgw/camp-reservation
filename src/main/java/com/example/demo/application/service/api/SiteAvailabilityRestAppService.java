package com.example.demo.application.service.api;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.domain.model.SiteAvailability;
import com.example.demo.domain.service.SiteAvailabilityService;
import com.example.demo.presentation.api.ResultSiteAvailability;

import lombok.RequiredArgsConstructor;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

/*
 * サイト空き状況（スケジュール用）の検索 Application Service
 */
@RequiredArgsConstructor
@Service
public class SiteAvailabilityRestAppService {
	
	private final SiteAvailabilityService siteAvailabilityService;

	/**
	 * サイト空き状況(スケジュール用)の検索
	 * @param siteTypeId
	 * @param startDate 取得開始日
	 * @param endDate 取得終了日
	 * @return
	 */
	public List<ResultSiteAvailability> fetchSiteAvailabilityForSchedule(int siteTypeId, LocalDate startDate, LocalDate endDate) {
		
		// サイトの空き状況検索
		List<SiteAvailability> result = siteAvailabilityService.findSiteAvailability(siteTypeId, startDate, endDate);
		
		if (result.isEmpty()) {
			throw new RuntimeException();
		}
		
		return result.stream().map(element -> new ResultSiteAvailability(
				generateTitle(element.getAvailabilityCount()),
				element.getCalendarDate(),
				generateUrl(element.getAvailabilityCount()
						, element.getSiiteTypeId()
						, element.getCalendarDate())))
				.collect(Collectors.toList());
	}
	
	/**
	 * スケジュール(FullCalendar)のイベントタイトル
	 * @param availabilityCount
	 * @return
	 */
	private String generateTitle(int availabilityCount) {
		
		if (availabilityCount == 0) {
			return "×";
		} else if (availabilityCount < 3) {
			return "△";
		} else {
			return "◎";
		}
	}
	
	private String generateUrl(int availabilityCount, int siteTypeId, LocalDate dateFrom) {
		
		String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/camping/stayInfo")
				.queryParam("form")
				.queryParam("sitetypeId", siteTypeId)
				.queryParam("dateFrom", dateFrom.format(ISO_LOCAL_DATE))
				.toUriString();
		
		return availabilityCount == 0 ? "" : url;
	}
}
