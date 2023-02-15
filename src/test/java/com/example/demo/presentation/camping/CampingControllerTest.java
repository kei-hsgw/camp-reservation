package com.example.demo.presentation.camping;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.demo.application.service.camping.CampingAppService;
import com.example.demo.domain.model.SiteType;

/*
 * キャンプ情報表示・予約 Controller Test
 */
@AutoConfigureMockMvc
@SpringBootTest
class CampingControllerTest {
	
	List<SiteType> siteTypeList = new ArrayList<>();
	SiteType siteType1 = new SiteType();
	SiteType siteType2 = new SiteType();
	SiteType siteType3 = new SiteType();
	StayInfoForm stayInfoForm = new StayInfoForm();
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CampingAppService campingAppService;
	
	@BeforeEach
	void setUp() throws Exception {
		
		siteType1.setId(1);
		siteType1.setName("区画サイト（AC電源なし）");
		siteType1.setCapacity(6);
		siteTypeList.add(siteType1);
		
		siteType2.setId(2);
		siteType2.setName("区画サイト（AC電源あり）");
		siteType2.setCapacity(6);
		siteTypeList.add(siteType2);
		
		siteType3.setId(3);
		siteType3.setName("コテージ");
		siteType3.setCapacity(4);
		siteTypeList.add(siteType3);
		
		stayInfoForm.setSiteTypeId(1);
		stayInfoForm.setDateFrom(LocalDate.of(2023, 01, 01));
		stayInfoForm.setSiteTypeName("区画サイト（AC電源なし）");
	}

	@Test
	@DisplayName("正常系：サイトタイプ一覧表示画面に遷移した時、サイトタイプを全件取得できている")
	void testSiteTypeList() throws Exception {
		
		when(campingAppService.findAllSiteType()).thenReturn(siteTypeList);
		
		// 検証 & 実行
		mockMvc.perform(get("/camping/siteTypes"))
				.andExpect(status().isOk())
				.andExpect(view().name("camping/siteTypes"))
				.andExpect(model().attribute("siteTypeList", siteTypeList));
	}
	
	@Test
	@DisplayName("正常系：サイトタイプIDが1のスケジュール表示画面に遷移した時、サイトタイプIDとサイトタイプ名が取得できている")
	void testScheduleBySiteTypeId1() throws Exception {
		
		when(campingAppService.findSiteTypeName(1)).thenReturn(siteType1.getName());
		
		// 検証 & 実行
		mockMvc.perform(get("/camping/schedule")
				.param("siteTypeId", "1"))
		.andExpect(status().isOk())
		.andExpect(view().name("camping/schedule"))
		.andExpect(model().attribute("siteTypeId", 1))
		.andExpect(model().attribute("siteTypeName", "区画サイト（AC電源なし）"));
	}
	
	@Test
	@DisplayName("正常系：サイトタイプIDが1の宿泊情報入力画面に遷移した時、サイトタイプ名が取得できている")
	void testStayInfoCatchSiteTypeName() throws Exception {
		
		when(campingAppService.findSiteTypeName(stayInfoForm.getSiteTypeId())).thenReturn(stayInfoForm.getSiteTypeName());
		
		// 検証 & 実行
		MvcResult result = mockMvc.perform(get("/camping/stayInfo")
				.flashAttr("stayInfoForm", stayInfoForm))
				.andExpect(status().isOk())
				.andExpect(view().name("camping/stayInfo"))
				.andReturn();
		
		StayInfoForm resultForm = (StayInfoForm) result.getModelAndView().getModel().get("stayInfoForm");
		
		assertEquals("区画サイト（AC電源なし）", resultForm.getSiteTypeName());
	}
	
}
