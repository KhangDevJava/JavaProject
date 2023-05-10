package com.finalexam.entity;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name = "`Account`")
@Data
@NoArgsConstructor
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "AccountID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Username", length = 30, nullable = false, unique = true)
    private String username;

    @Column(name = "Password", length = 60, nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "DepartmentID", nullable = false)
    private Department department;

    @Column(name = "Role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @PrePersist
    public void prePersist() {
        if (password == null) {
            password = new BCryptPasswordEncoder().encode("password123");
        }
    }

    public enum Role {
        ADMIN, MANAGER, EMPLOYEE
    }
}
