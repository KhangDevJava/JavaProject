package com.finalexam.controller;

import com.finalexam.dto.AccountDTO;
import com.finalexam.entity.Account;
import com.finalexam.form.account.AccountFilterForm;
import com.finalexam.form.account.CreatingAccountForm;
import com.finalexam.form.account.UpdatingAccountForm;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.finalexam.service.IAccountService;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "api/v1/accounts")
@Validated
public class AccountController {

    @Autowired
    private IAccountService service;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping()
    public Page<AccountDTO> getAllAccounts(Pageable pageable, @RequestParam(value = "search", required = false) String search, AccountFilterForm filterForm) {

        Page<Account> entityPages = service.getAllAccounts(pageable, search, filterForm);

        // convert entities --> dtos
        List<AccountDTO> dtos = modelMapper.map(entityPages.getContent(), new TypeToken<List<AccountDTO>>() {}.getType());

        Page<AccountDTO> dtoPages = new PageImpl<>(dtos, pageable, entityPages.getTotalElements());

        for(AccountDTO dto : dtos) {
            dto.add(linkTo(methodOn(AccountController.class).getAccountByID(dto.getId())).withSelfRel());
        }

        return dtoPages;

    }

    @PostMapping()
    public void createAccount(@RequestBody @Valid CreatingAccountForm form) {
        service.createAccount(form);
    }

    @PutMapping(value = "/{id}")
    public void updateAccount(@PathVariable(name = "id") int id, @RequestBody UpdatingAccountForm form) {
        form.setId(id);
        service.updateAccount(form);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteAccount(@PathVariable(name = "id") int id) {
        service.deleteAccount(id);
    }

    @DeleteMapping
    public void deleteAccounts(@RequestParam(name = "ids") List<Integer> ids) {
        service.deleteAccounts(ids);
    }

    @GetMapping(value = "/username/{username}/exists")
    public boolean existsByName(@PathVariable(name = "username") String username) {
        return service.isAccountExistsByUsername(username);
    }

    @GetMapping(value = "/{id}")
    public AccountDTO getAccountByID(@PathVariable(name = "id") int id) {
        Account entity = service.getAccountByID(id);

        // convert entity to dto
        AccountDTO dto = modelMapper.map(entity, AccountDTO.class);

        dto.add(linkTo(methodOn(AccountController.class).getAccountByID(id)).withSelfRel());

        return dto;
    }

    @GetMapping("/exception")
    public void testException() throws Exception {
        // ... other logic
        throw new EntityNotFoundException("... Exception Information");
        // ... other code
    }
}
