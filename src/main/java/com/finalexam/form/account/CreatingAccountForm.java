package com.finalexam.form.account;

import com.finalexam.entity.Account;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;


@Data
@NoArgsConstructor
public class CreatingAccountForm {

	@NotBlank(message = "{Account.createAccount.form.name.NotBlank}")
	@Length(max = 30, message = "{Account.createAccount.form.name.Length}")
	private String username;

	@NotNull(message = "{Account.createAccount.form.name.NotNull}")
	@PositiveOrZero(message = "{Account.createAccount.form.name.PositiveOrZero}")
	private int departmentId;

	private Account.Role role;

}

