package com.finalexam.dto;

import com.finalexam.entity.Account;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;


@Data
@NoArgsConstructor
public class AccountDTO extends RepresentationModel<AccountDTO> {

    private int id;

    private String username;

    private String departmentName;

    private Account.Role role;

}

