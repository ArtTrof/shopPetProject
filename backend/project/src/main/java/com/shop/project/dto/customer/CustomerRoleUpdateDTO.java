package com.shop.project.dto.customer;

import com.shop.project.models.Role;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRoleUpdateDTO {
    @NotBlank
    private Set<Role> roles;
}
