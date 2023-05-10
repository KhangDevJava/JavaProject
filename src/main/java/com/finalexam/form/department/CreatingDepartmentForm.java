package com.finalexam.form.department;

import com.finalexam.validation.account.AccountUsernameNotExists;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
public class CreatingDepartmentForm {

    private int id;

    @NotBlank(message = "{Department.createDepartment.form.name.NotBlank}")
    @Length(max = 20, message = "{Department.createDepartment.form.name.Length}")
    private String name;

    @NotEmpty(message = "Accounts mustn't be empty")
    private List<@Valid Account> accounts;

    @Data
    @NoArgsConstructor
    public static class Account {

        @NotBlank(message = "The name mustn't be null value")
        @Length(max = 50, message = "The name's length is max 50 characters")
        @AccountUsernameNotExists
        private String username;
    }
}
