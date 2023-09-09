package com.shop.project.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id", "email"})
public class CustomerDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String roles;
}
