package com.shop.project.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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
    @NotBlank(message = "last name can't be blank")
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
    @OneToOne
    private CartItem cartItem;
}
