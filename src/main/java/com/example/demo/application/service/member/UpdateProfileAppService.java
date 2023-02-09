package com.example.demo.application.service.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.model.Member;
import com.example.demo.domain.service.MemberService;

import lombok.RequiredArgsConstructor;

/*
 * 会員基本情報更新 Application Service
 */
@RequiredArgsConstructor
@Transactional
@Service
public class UpdateProfileAppService {

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
	
	public void update(int memberId, Member newMember) {
		
		Member member = findMember(memberId);
		member.setName(newMember.getName());
		member.setPhoneNumber(newMember.getPhoneNumber());
		
		// 会員基本情報更新
		memberService.updateProfile(member);
	}
}
