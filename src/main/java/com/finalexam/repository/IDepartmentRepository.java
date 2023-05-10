package com.finalexam.repository;

import com.finalexam.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDepartmentRepository extends JpaRepository<Department, Integer> {

    Page<Department> findAll(Specification<Department> where, Pageable pageable);

    public Department findDepartmentByName(String name);
}
