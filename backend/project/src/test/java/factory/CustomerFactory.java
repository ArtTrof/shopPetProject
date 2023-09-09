package factory;

import com.shop.project.dto.customer.CustomerDTO;
import com.shop.project.models.Customer;
import com.shop.project.models.Role;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.Set;

public class CustomerFactory {
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

    public static CustomerDTO mapCustomerToDTO(Customer customer) {
        return CustomerDTO.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .roles(customer.getRoles().iterator().next().toString())
                .build();
    }
}
