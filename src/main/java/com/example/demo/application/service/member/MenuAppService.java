package com.example.demo.application.service.member;

import org.springframework.stereotype.Service;

import com.example.demo.domain.model.Member;
import com.example.demo.domain.service.MemberService;

import lombok.RequiredArgsConstructor;

/*
 * 会員メニュー Application Service
 */
@RequiredArgsConstructor
@Service
public class MenuAppService {

	private final MemberService memberService;
	
	/**
	 * 会員情報取得
	 * @param memberId 会員ID
	 * @return
	 */
	public Member findMember(int memberId) {
		
		return memberService.findById(memberId)
				.orElseThrow(() -> new RuntimeException());
	}
}
