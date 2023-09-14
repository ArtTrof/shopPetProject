package com.shop.project.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRegistrationDTO {
    @NotBlank(message = "first name can't be blank")
    @Length(min = 1, max = 200, message = "First name should be between 2 and 200 characters")
    private String firstName;
    @NotBlank(message = "last name can't be blank")
    @Length(min = 1, max = 200, message = "Last name should be between 2 and 200 characters")
    private String lastName;
    @NotBlank(message = "email required")
    @Email(message = "Invalid email format")
    private String email;
    @Length(min = 8, max = 15, message = "Phone number should be between 8 and 15 digits")
    private String phone;
    @NotBlank(message = "Password can't be blank")
    @Length(min = 8, message = "Password should contain at least 8 characters")
    private String password;

}
