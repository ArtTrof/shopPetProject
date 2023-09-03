package com.shop.project.util.validators;

import com.shop.project.models.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class CustomerValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Customer.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Customer customer = (Customer) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "field.required", "Password is required");
        if (customer.getPassword() != null && customer.getPassword().length() < 8) {
            errors.rejectValue("password", "field.minlength", "Password must be at least 8 characters");
        }

    }

}
