package com.example.demo.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.model.Member;
import com.example.demo.repository.MemberMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {

	private final MemberMapper memberMapper;
	
	/**
	 * 会員情報取得
	 * @param mail メールアドレス
	 * @return
	 */
	public Member findByMail(String mail) {
		return memberMapper.findByMail(mail);
	}
	
	/**
	 * 会員登録
	 * @param member 会員情報
	 */
	public void create(Member member) {
		memberMapper.create(member);
	}
}
