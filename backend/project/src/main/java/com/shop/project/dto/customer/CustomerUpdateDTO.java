package com.shop.project.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import java.util.Optional;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerUpdateDTO {

    private Optional<@Length(min = 1, max = 200, message = "First name should be between 2 and 200 characters")
            String> firstName;

    private Optional<@Email(message = "Invalid email format") String>
            email;

    private Optional<@Length(min = 1, max = 200, message = "Last name should be between 2 and 200 characters")
            String> lastName;

    private Optional<@Length(min = 8, max = 12, message = "Phone number should be between 8 and 12")
            String> phone;

    private Optional<@Length(min = 8, message = "Password should contain at least 8 characters")
            String> password;

}
