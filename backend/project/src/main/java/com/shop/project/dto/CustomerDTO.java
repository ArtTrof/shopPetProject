package com.shop.project.dto;

import lombok.*;


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
    private String role;
}
