package com.shop.project.util.validators;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ValidationErrorResponse {
    private String message;
    private List<String> errors;

    public static String getValidationErrors(BindingResult bindingResult) {
        List<String> errorMessages = bindingResult.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return "Validation failed:" + String.join(",", errorMessages);
    }
}
