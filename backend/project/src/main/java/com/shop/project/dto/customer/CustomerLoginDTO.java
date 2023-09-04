package com.shop.project.dto.customer;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerLoginDTO {
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
