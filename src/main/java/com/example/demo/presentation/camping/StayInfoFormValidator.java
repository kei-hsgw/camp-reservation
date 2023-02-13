package com.example.demo.presentation.camping;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.demo.domain.model.StayInfo;
import com.example.demo.domain.service.SiteAvailabilityService;
import com.example.demo.domain.service.SiteTypeService;

import lombok.RequiredArgsConstructor;

/*
 * 宿泊情報フォーム Validator
 */
@RequiredArgsConstructor
@Component
public class StayInfoFormValidator implements Validator {
	
	private final SiteTypeService siteTypeService;
	private final SiteAvailabilityService siteAvailabilityService;
	private final ModelMapper modelMapper;

	/**
	 * チェック対象となるクラスがStayInfoFormクラスかチェックする
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return StayInfoForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		StayInfoForm stayInfoForm = (StayInfoForm) target;
		StayInfo stayInfo = modelMapper.map(stayInfoForm, StayInfo.class);
		
		// サイトの上限人数取得
		int capacity = siteTypeService.findBySiteTypeId(stayInfo.getSiteTypeId())
				.map(st -> st.getCapacity())
				.orElseThrow(() -> new RuntimeException());
		
		// 宿泊人数検証
		validateNumberOfPeople(errors, stayInfo, capacity);
		
		// 予約受付期間判定
		if (!validateOKPeriodOfStay(errors, stayInfo)) {
			return;
		}
		
		// サイトの空き状況検証
		validateSiteAvailability(errors, stayInfo);
	}
	
	/**
	 * 宿泊人数検証
	 * 宿泊人数がサイトの上限を上回る場合エラー
	 * @param errors
	 * @param stayInfo 宿泊情報
	 * @param capacity サイトの上限人数
	 */
	private void validateNumberOfPeople(Errors errors, StayInfo stayInfo, int capacity) {
		
		if (!stayInfo.isValidNumberOfPeople(capacity)) {
			errors.rejectValue("numberOfPeople", "validation.custom.numberOfPeopleIncorrect", new String[] {String.valueOf(capacity)}, "宿泊人数が上限を超えています");
		}
	}
	
	/**
	 * 予約受付期間判定
	 * 予約受付期間外の日程が含まれる場合はエラー情報を設定し、falseを返す。それ以外はtrue
	 * @param errors
	 * @param stayInfo 宿泊情報
	 * @return
	 */
	private boolean validateOKPeriodOfStay(Errors errors, StayInfo stayInfo) {
		
		if (!stayInfo.isValidPeriod()) {
			errors.rejectValue("stayDays", "validation.custom.periodOfStayIncorrect", new String[] {String.valueOf(stayInfo.getDaysOfStay())}, "予約受付期間外の日程が含まれています");
			return false;
		}
		
		return true;
	}
	
	/**
	 * サイトの空き状況検証
	 * チェックイン日からチェックアウト日までの期間で、満室の日程がある場合はエラー
	 * @param errors
	 * @param stayInfo 宿泊情報
	 */
	private void validateSiteAvailability(Errors errors, StayInfo stayInfo) {
		
		if (siteAvailabilityService.isSiteAvailableForPeriod(stayInfo.getSiteTypeId(), stayInfo.getDateFrom(), stayInfo.getDateTo())) {
			errors.rejectValue("stayDays", "validation.custom.siteIsNotAvailable", new String[] {String.valueOf(stayInfo.getDaysOfStay())}, "満室の日程が含まれています");
		}
	}
}
