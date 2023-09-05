package com.shop.project.dto.customer;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerLoginDTO {
    @Email
    @NotBlank(message = "email required")
    @Email(message = "Invalid email format")
    private String email;
    @NotBlank(message = "Password can't be blank")
    @Length(min = 8, message = "Password should contain at least 8 characters")
    private String password;
}
