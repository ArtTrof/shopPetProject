package com.shop.project.dto.customer;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerUpdateDTO {
    @Length(min = 1, max = 200, message = "First name should be between 2 and 200 characters")
    private String firstName;
    @Email(message = "Invalid email format")
    private String email;
    @Length(min = 1, max = 200, message = "Last name should be between 2 and 200 characters")
    private String lastName;
    @Length(min = 8, max = 15, message = "Phone number should be between 8 and 15 digits")
    private String phone;
    @Length(min = 8, message = "Password should contain at least 8 characters")
    private String password;

}
