package com.finalexam.service;

import com.finalexam.entity.Account;
import com.finalexam.entity.Department;
import com.finalexam.form.account.CreatingAccountForm;
import com.finalexam.form.department.CreatingDepartmentForm;
import com.finalexam.form.department.UpdatingDepartmentForm;
import com.finalexam.repository.IAccountRepository;
import com.finalexam.repository.IDepartmentRepository;
import com.finalexam.specification.department.DepartmentSpecification;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DepartmentService implements IDepartmentService{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IDepartmentRepository departmentRepository;

    @Autowired
    private IAccountRepository accountRepository;

    public Page<Department> getAllDepartments(Pageable pageable, String search) {

        Specification<Department> where = DepartmentSpecification.buildWhere(search);
        return departmentRepository.findAll(where, pageable);
    }

    public void createDepartment(CreatingDepartmentForm form) {

        // omit id field
        TypeMap<CreatingDepartmentForm, Department> typeMap = modelMapper.getTypeMap(CreatingDepartmentForm.class, Department.class);
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
        Department departmentEntity = modelMapper.map(form, Department.class);

        // create department
        Department department = departmentRepository.save(departmentEntity);

        // create accounts
        List<Account> accountEntities = departmentEntity.getAccounts();
        for (Account account : accountEntities) {
            account.setDepartment(department);
        }
        accountRepository.saveAll(accountEntities);
    }

    public void updateDepartment(UpdatingDepartmentForm form) {

        // convert form to entity
        Department department = modelMapper.map(form, Department.class);

        departmentRepository.save(department);
    }

    public void deleteDepartment(int id) {
        departmentRepository.deleteById(id);
    }

    public boolean isDepartmentExistsByID(Integer id) {
        return departmentRepository.existsById(id);
    }

}
