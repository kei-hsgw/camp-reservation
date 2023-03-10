package com.example.demo.presentation;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.math.BigDecimal;
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

import com.example.demo.application.service.TopAppService;
import com.example.demo.domain.model.SiteRatePivot;

/*
 * トップページ Controller Test
 */
@AutoConfigureMockMvc
@SpringBootTest
class TopControllerTest {
	
	List<SiteRatePivot> siteRatePivotList = new ArrayList<>();
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private TopAppService topAppService;
	
	@BeforeEach
	void setUp() throws Exception {
		
		SiteRatePivot siteRatePivot1 = new SiteRatePivot();
		siteRatePivot1.setSiteTypeId(1);
		siteRatePivot1.setSiteTypeName("区画サイト（AC電源なし）");
		siteRatePivot1.setRateBasic(BigDecimal.valueOf(2500));
		siteRatePivot1.setRateHighSeason(BigDecimal.valueOf(3500));
		siteRatePivotList.add(siteRatePivot1);
		
		SiteRatePivot siteRatePivot2 = new SiteRatePivot();
		siteRatePivot2.setSiteTypeId(2);
		siteRatePivot2.setSiteTypeName("区画サイト（AC電源あり）");
		siteRatePivot2.setRateBasic(BigDecimal.valueOf(4500));
		siteRatePivot2.setRateHighSeason(BigDecimal.valueOf(5500));
		siteRatePivotList.add(siteRatePivot2);
		
		SiteRatePivot siteRatePivot3 = new SiteRatePivot();
		siteRatePivot3.setSiteTypeId(3);
		siteRatePivot3.setSiteTypeName("コテージ");
		siteRatePivot3.setRateBasic(BigDecimal.valueOf(5000));
		siteRatePivot3.setRateHighSeason(BigDecimal.valueOf(7000));
		siteRatePivotList.add(siteRatePivot3);
	}
	
	@Test
	@DisplayName("正常系：トップページに遷移した時、サイトタイプ別料金表を取得できている")
	void testTop() throws Exception {
		
		when(topAppService.findAllSiteRatePivot()).thenReturn(siteRatePivotList);
		
		// 検証 & 実行
		mockMvc.perform(get("/"))
		.andExpect(status().isOk())
		.andExpect(view().name("index"))
		.andExpect(model().attribute("siteRateList", siteRatePivotList))
		.andReturn();
	}

}
