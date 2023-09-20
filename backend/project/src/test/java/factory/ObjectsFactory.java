package factory;

import com.shop.project.dto.customer.CustomerDTO;
import com.shop.project.models.CartItem;
import com.shop.project.models.Category;
import com.shop.project.models.Customer;
import com.shop.project.models.Producer;
import com.shop.project.models.Product;
import com.shop.project.models.Role;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.Set;

public class ObjectsFactory {
    public static Customer generateCustomer() {
        return Customer.builder()
                .id(new Random().nextLong())
                .email(String.format("testemail%s@test.com", new Random().nextInt(0, 10)))
                .phone("+48555555555")
                .roles(Set.of(Role.USER))
                .createdAt(LocalDateTime.now())
                .firstName("John")
                .lastName("Snow")
                .password("somePassword")
                .build();
    }

    public static Product generateProduct() {
        return Product.builder()
                .id(new Random().nextLong())
                .contentType(null)
                .image(null)
                .price(new Random().nextInt(10, 1000))
                .isAvailable(true)
                .name("TestProduct" + new Random().nextInt(0, 100))
                .quantity(1000)
                .longDescription("text")
                .shortDescription("text")
                .category(Category.builder().id(0l).name("category").build())
                .producer(Producer.builder().id(0l).name("producer").build())
                .build();
    }

    public static CustomerDTO mapCustomerToDTO(Customer customer) {
        return CustomerDTO.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .roles(customer.getRoles().iterator().next().toString())
                .build();
    }

    public static CartItem generateCartItem() {
        return CartItem.builder()
                .customer(generateCustomer())
                .quantity(10)
                .product(generateProduct())
                .build();
    }
}
