package com.finalexam.form.account;

import com.finalexam.entity.Account;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class UpdatingAccountForm {

    private int id;

    private String username;

    private int departmentId;

    @NotBlank(message = "Role must not be blank")
    private Account.Role role;

}
