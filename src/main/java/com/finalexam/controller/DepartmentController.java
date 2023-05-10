package com.finalexam.controller;

import com.finalexam.dto.DepartmentDTO;
import com.finalexam.entity.Department;
import com.finalexam.form.department.CreatingDepartmentForm;
import com.finalexam.form.department.UpdatingDepartmentForm;
import com.finalexam.service.IDepartmentService;
import com.finalexam.validation.department.DepartmentIDExists;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@CrossOrigin("*")
@RestController
@RequestMapping(value = "api/v1/departments")
@Validated
public class DepartmentController {

    @Autowired
    private IDepartmentService service;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping()
    public Page<DepartmentDTO> getAllDepartments(Pageable pageable, @RequestParam(name = "search", required = false) String search) {

        Page<Department> entityPages = service.getAllDepartments(pageable, search);

        // convert entities --> dtos
        List<DepartmentDTO> dtos = modelMapper.map(
                entityPages.getContent(),
                new TypeToken<List<DepartmentDTO>>() {}.getType());

        Page<DepartmentDTO> dtoPages = new PageImpl<>(dtos, pageable, entityPages.getTotalElements());

        return dtoPages;
    }

    @PostMapping()
    public void createDepartment(@RequestBody @Valid CreatingDepartmentForm form) {
        service.createDepartment(form);
    }

    @PutMapping(value = "/{id}")
    public void updateDepartment(@DepartmentIDExists @PathVariable(name = "id") int id, @RequestBody UpdatingDepartmentForm form) {
        form.setId(id);
        service.updateDepartment(form);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteDepartment(@PathVariable(name = "id") int id) {
        service.deleteDepartment(id);
    }

    @GetMapping("/exception")
    public void testException() throws Exception {
        // ... other logic
        throw new EntityNotFoundException("... Exception Information");
        // ... other code
    }
}
