package com.finalexam.specification.account;

import com.finalexam.entity.Account;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.finalexam.form.account.AccountFilterForm;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class AccountSpecification {

    @SuppressWarnings("deprecation")
    public static Specification<Account> buildWhere(String search, AccountFilterForm filterForm) {

        Specification<Account> where = null;

        if (!StringUtils.isEmpty(search)) {
            search = search.trim();
            CustomSpecification username = new CustomSpecification("username", search);
            CustomSpecification departmentName = new CustomSpecification("departmentName", search);
            where = Specification.where(username).or(departmentName);
        }


        // if there is filter by Role
        if (filterForm != null && filterForm.getRole() != null) {
            CustomSpecification role = new CustomSpecification("role", filterForm.getRole());
            if (where == null) {
                where = role;
            } else {
                where = where.and(role);
            }
        }

        // if there is filter by department name
        if (filterForm != null && filterForm.getDepartmentName() != null) {
            CustomSpecification departmentName = new CustomSpecification("departmentName",
                    filterForm.getDepartmentName());
            if (where == null) {
                where = departmentName;
            } else {
                where = where.and(departmentName);
            }
        }

        return where;
    }
}

@SuppressWarnings("serial")
@RequiredArgsConstructor
class CustomSpecification implements Specification<Account> {

    @NonNull
    private String field;
    @NonNull
    private Object value;

    @Override
    public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (field.equalsIgnoreCase("username")) {
            return criteriaBuilder.like(root.get("username"), "%" + value.toString() + "%");
        }

        if (field.equalsIgnoreCase("role")) {
            return criteriaBuilder.equal(root.get("role"), value);
        }

        if (field.equalsIgnoreCase("departmentName")) {
            return criteriaBuilder.equal(root.get("department").get("name"), value.toString());
        }
        return null;
    }
}