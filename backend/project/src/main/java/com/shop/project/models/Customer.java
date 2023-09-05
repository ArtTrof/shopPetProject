package com.shop.project.models;


import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "customer")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    @NotBlank(message = "first name can't be blank")
    @Length(min = 1, max = 200, message = "First name should be between 2 and 200 characters")
    private String firstName;

    @Column(name = "last_name")
    @Length(min = 1, max = 200, message = "Last name should be between 2 and 200 characters")
    private String lastName;

    @Column(name = "email")
    @NotBlank(message = "email required")
    @Email(message = "Invalid email format")
    private String email;

    @Column(name = "phone")
    @Length(min = 8, max = 15, message = "Phone number should be between 8 and 15 digits")
    private String phone;

    @Column(name = "password")
    @NotBlank(message = "Password can't be blank")
    @Length(min = 10, message = "Password should contain at least 8 characters")
    private String password;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "customer_role", joinColumns = @JoinColumn(name = "customer_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
}
