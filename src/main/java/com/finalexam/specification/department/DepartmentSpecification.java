package com.finalexam.specification.department;

import com.finalexam.entity.Department;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.Date;

public class DepartmentSpecification {

	@SuppressWarnings("deprecation")
	public static Specification<Department> buildWhere(String search) {
		
		Specification<Department> where = null;
		
		if (!StringUtils.isEmpty(search)) {
			search = search.trim();
			CustomSpecification username = new CustomSpecification("username", search);
			where = Specification.where(username);
		}

		return where;
	}
}

@SuppressWarnings("serial")
@RequiredArgsConstructor
class CustomSpecification implements Specification<Department> {

	@NonNull
	private String field;
	@NonNull
	private Object value;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Predicate toPredicate(
			Root<Department> root,
			CriteriaQuery<?> query,
			CriteriaBuilder criteriaBuilder) {

		if (field.equalsIgnoreCase("username")) {
			Join join = root.join("accounts", JoinType.LEFT);
			return criteriaBuilder.like(join.get("username"), "%" + value.toString() + "%");
		}

		return null;
	}
}

