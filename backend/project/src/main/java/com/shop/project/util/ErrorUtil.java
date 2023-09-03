package com.shop.project.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class ErrorUtil {
    public static void returnErrorToClient(BindingResult bindingResult) {
        StringBuilder errorMsg = new StringBuilder();
        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError err : errors) {
            errorMsg.append(err.getField())
                    .append("-").append(err.getDefaultMessage() == null ? err.getCode() : err.getDefaultMessage())
                    .append(";");
        }
        throw new ThrownException(errorMsg.toString());
    }
}
