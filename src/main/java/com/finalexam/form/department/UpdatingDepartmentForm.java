package com.finalexam.form.department;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class UpdatingDepartmentForm {

    private int id;

    @NotBlank(message = "Department must not be blank")
    @Length(max = 20, message = "Department Name must below 20 characters")
    private String name;

}
