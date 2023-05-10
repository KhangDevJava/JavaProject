package com.finalexam.service;

import com.finalexam.entity.Department;
import com.finalexam.form.department.CreatingDepartmentForm;
import com.finalexam.form.department.UpdatingDepartmentForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IDepartmentService{

    public Page<Department> getAllDepartments(Pageable pageable, String search);

    public void createDepartment(CreatingDepartmentForm form);

    public void updateDepartment(UpdatingDepartmentForm form);

    public boolean isDepartmentExistsByID(Integer id);

    public void deleteDepartment(int id);
}
