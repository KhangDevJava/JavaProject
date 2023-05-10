package com.finalexam.entity;


import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Department")
@Data
@NoArgsConstructor
public class Department implements Serializable{

    private static final long serialVersionUID = 1L;

    @Column(name = "DepartmentID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "DepartmentName", length = 20, nullable = false)
    private String name;

    @OneToMany(mappedBy = "department")
    private List<Account> accounts;
}
