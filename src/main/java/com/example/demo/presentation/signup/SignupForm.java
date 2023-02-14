package com.example.demo.presentation.signup;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SignupForm {

	/** メールアドレス */
	@NotBlank
	@Email
	private String mail;
	
	/** パスワード */
	@NotBlank
	@Length(min = 8, message = "8文字以上で入力してください")
	@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "半角英数字で入力してください")
	private String password;
	
	/** 名前 */
	@NotBlank
	@Length(max = 100, message = "100文字以内で入力してください")
	private String name;
	
	/** 電話番号 */
	@NotBlank
	@Length(max = 15, message = "15桁以内で入力してください")
	@Pattern(regexp = "^[0-9]+$", message = "半角数字で入力してください")
	private String phoneNumber;
}
