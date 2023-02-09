package com.example.demo.presentation.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/*
 * メールアドレス変更フォーム
 */
@Data
public class UpdateMailForm {

	/** メールアドレス */
	@NotBlank(message = "必須項目です")
	@Email(message = "メールアドレスの形式で入力してください")
	private String mail;
}
