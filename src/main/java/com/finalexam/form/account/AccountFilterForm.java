package com.finalexam.form.account;

import com.finalexam.entity.Account;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountFilterForm {

	private Account.Role role;

	private String departmentName;

}

