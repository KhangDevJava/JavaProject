package com.finalexam.validation.department;

import com.finalexam.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DepartmentIDExistsValidator implements ConstraintValidator<DepartmentIDExists, Integer> {

	@Autowired
	private IDepartmentService departmentRepository;

	@SuppressWarnings("deprecation")
	@Override
	public boolean isValid(Integer id, ConstraintValidatorContext constraintValidatorContext) {

		if (StringUtils.isEmpty(id)) {
			return true;
		}

		return departmentRepository.isDepartmentExistsByID(id);
	}
}

