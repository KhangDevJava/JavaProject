package com.finalexam.service;

import com.finalexam.entity.Account;
import com.finalexam.form.account.AccountFilterForm;
import com.finalexam.form.account.CreatingAccountForm;
import com.finalexam.form.account.UpdatingAccountForm;
import com.finalexam.repository.IAccountRepository;
import com.finalexam.specification.account.AccountSpecification;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class AccountService implements IAccountService {

    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Page<Account> getAllAccounts(Pageable pageable, String search, AccountFilterForm filterForm) {

        Specification<Account> where = AccountSpecification.buildWhere(search, filterForm);
        return accountRepository.findAll(where, pageable);

    }

    public void createAccount(CreatingAccountForm form) {

        // omit id field
        TypeMap<CreatingAccountForm, Account> typeMap = modelMapper.getTypeMap(CreatingAccountForm.class, Account.class);
        if (typeMap == null) { // if not already added
            // skip field
            modelMapper.addMappings(new PropertyMap<CreatingAccountForm, Account>() {
                @Override
                protected void configure() {
                    skip(destination.getId());
                }
            });
        }

        // convert form to entity
        Account account = modelMapper.map(form, Account.class);

        accountRepository.save(account);
    }

    public void updateAccount(UpdatingAccountForm form) {

        // omit id field
        TypeMap<UpdatingAccountForm, Account> typeMap = modelMapper.getTypeMap(UpdatingAccountForm.class, Account.class);
        if (typeMap == null) { // if not already added
            // skip field
            modelMapper.addMappings(new PropertyMap<CreatingAccountForm, Account>() {
                @Override
                protected void configure() {
                    skip(destination.getPassword());
                }
            });
        }

        // convert form to entity
        Account account = modelMapper.map(form, Account.class);

        accountRepository.save(account);
    }

    @Override
    public void deleteAccount(int id) {
        accountRepository.deleteById(id);
    }

    @Override
    public void deleteAccounts(List<Integer> ids) {
        accountRepository.deleteByIds(ids);
    }

    public boolean isAccountExistsByUsername(String username) {
        return accountRepository.existsByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Account account = accountRepository.findByUsername(username);

        if (account==null)
        {
            throw new UsernameNotFoundException(username);
        }

        return new User(
                account.getUsername(),
                account.getPassword(),
                Collections.emptyList());

    }

    public Account getAccountByID(int id) {
        return accountRepository.findById(id).get();
    }

    @Override
    public Account getAccountByUsername(String username) {
        return accountRepository.findByUsername(username);
    }
}
