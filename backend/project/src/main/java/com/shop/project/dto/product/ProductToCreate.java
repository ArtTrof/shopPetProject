package com.shop.project.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductToCreate {
    @Length(min = 1, max = 200, message = "Product name should be between 1 and 200 characters")
    @NotBlank(message = "Product name can't ne empty")
    private String name;

    @Length(min = 1, max = 1000, message = "Short description should be between 1 and 100 characters")
    @NotBlank(message = "Product short description can't ne empty")
    private String shortDescription;

    @Length(min = 1, max = 2000, message = "Long description should be between 1 and 2000 characters")
    @NotBlank(message = "Product long description can't ne empty")
    private String longDescription;

    @Min(value = 0, message = "Price should be above 0")
    @NotNull(message = "Product price can't be null")
    private Double price;

    @NotNull(message = "Product availability can't be null ")
    private Boolean isAvailable;

    @Min(value = 0, message = "Quantity should be above or equal 0")
    @NotNull(message = "Product quantity can't be null ")
    private Integer quantity;

    @Length(min = 1, max = 200, message = "Category description should be between 1 and 200 characters")
    @NotBlank(message = "Product long description can't ne empty")
    private String category;

    @Length(min = 1, max = 200, message = "Category description should be between 1 and 200 characters")
    @NotBlank(message = "Product long description can't ne empty")
    private String producer;
}
