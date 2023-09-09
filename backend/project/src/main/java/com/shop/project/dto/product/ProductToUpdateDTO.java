package com.shop.project.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import java.util.Optional;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductToUpdateDTO {

    private Optional<@Length(min = 1, max = 200, message = "Product name should be between 1 and 200 characters") String> name;


    private Optional<@Length(min = 1, max = 1000, message = "Short description should be between 1 and 100 characters")
            String> shortDescription;


    private Optional<@Length(min = 1, max = 2000, message = "Long description should be between 1 and 2000 characters")
            String> longDescription;

    private Optional<@Min(value = 0, message = "Price should be above 0")
            Double> price;

    private Optional<Boolean> isAvailable;

    private Optional<@Min(value = 0, message = "Quantity should be above or equal 0")
            Integer> quantity;


    private Optional<@Length(min = 1, max = 200, message = "Category description should be between 1 and 200 characters")
            String> category;


    private Optional<@Length(min = 1, max = 200, message = "Category description should be between 1 and 200 characters")
            String> producer;
}
