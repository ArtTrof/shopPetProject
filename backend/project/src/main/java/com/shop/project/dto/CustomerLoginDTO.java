package com.shop.project.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerLoginDTO {
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;
    @Size(min = 0,max=10,message = "invalid phone number")
    private String phone;
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
    @Email(message = "invalid email")
    private String email;
}
