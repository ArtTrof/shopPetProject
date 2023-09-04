package com.shop.project.dto.customer;

import com.shop.project.models.Role;
import lombok.*;

import java.util.Set;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String password;
    private String email;
    private Set<String> roles;
}
