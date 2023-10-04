package com.shop.project.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerPasswordDTO {
    @NotBlank(message = "Password can't be blank")
    @Length(min = 8, message = "Password should contain at least 8 characters")
    private String password;
}
