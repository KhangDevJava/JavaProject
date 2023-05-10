package com.finalexam.service;

import com.finalexam.entity.Account;
import com.finalexam.form.account.AccountFilterForm;
import com.finalexam.form.account.CreatingAccountForm;
import com.finalexam.form.account.UpdatingAccountForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IAccountService extends UserDetailsService {

    public Page<Account> getAllAccounts(Pageable pageable, String search, AccountFilterForm filterForm);

    public void createAccount(CreatingAccountForm form);

    public Account getAccountByUsername(String username);

    public void updateAccount(UpdatingAccountForm form);

    public Account getAccountByID(int id);

    public boolean isAccountExistsByUsername(String username);

    public void deleteAccount(int id);
    public void deleteAccounts(List<Integer> ids);


}
