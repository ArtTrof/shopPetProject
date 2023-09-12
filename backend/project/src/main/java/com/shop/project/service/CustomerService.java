package com.shop.project.service;

import com.shop.project.dto.customer.CustomerDTO;
import com.shop.project.dto.customer.CustomerUpdateDTO;
import com.shop.project.models.Customer;
import com.shop.project.models.Role;
import com.shop.project.repository.CartItemRepo;
import com.shop.project.repository.CustomerRepo;
import com.shop.project.util.ThrownException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CustomerService implements UserDetailsService {
    private final CustomerRepo customerRepo;
    private final PasswordEncoder passwordEncoder;
    private final CartItemRepo cartItemRepo;

    @Autowired
    public CustomerService(CustomerRepo customerRepo, PasswordEncoder passwordEncoder, CartItemRepo cartItemRepo) {
        this.customerRepo = customerRepo;
        this.passwordEncoder = passwordEncoder;
        this.cartItemRepo = cartItemRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return new User(customer.getEmail(), customer.getPassword(), getGrantedAuthority(customer));
    }

    private Collection<GrantedAuthority> getGrantedAuthority(Customer customer) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        Set<Role> roles = customer.getRoles();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.name()));
        }
        return authorities;
    }


    public List<CustomerDTO> getCustomers() {
        var customers = customerRepo.findAll();
        List<CustomerDTO> dtos = new ArrayList<>();
        for (Customer customer : customers) {
            dtos.add(new CustomerDTO(customer.getId(),
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getPhone(),
                    customer.getEmail(),
                    customer.getRoles().iterator().next().toString()
            ));
        }
        return dtos;
    }

    public Customer getCustomerById(Long id) {
        Optional<Customer> customer = customerRepo.findCustomerById(id);
        return customer.orElse(null);
    }

    @Transactional
    public void saveNewCustomer(Customer customer) {
        String email = customer.getEmail();
        if (isEmailUnique(email)) {
            throw new ThrownException("User with such email already exists");
        }
        Customer customerToSave = Customer.builder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .createdAt(LocalDateTime.now())
                .roles(Set.of(Role.USER))
                .password(passwordEncoder.encode(customer.getPassword()))
                .build();
        customerRepo.save(customerToSave);

    }

    @Transactional
    public ResponseEntity<String> updateCustomer(Long id, CustomerUpdateDTO dto) {
        if (customerRepo.findCustomerById(id).isPresent()) {
            Customer customer = customerRepo.findCustomerById(id).get();
            if (dto.getEmail().isPresent() && !customer.getEmail().equals(dto.getEmail().get()) && isEmailUnique(dto.getEmail().get()))
                customer.setEmail(dto.getEmail().get());
            if (dto.getPassword().isPresent())
                customer.setPassword(passwordEncoder.encode(dto.getPassword().get()));
            if (dto.getFirstName().isPresent())
                customer.setFirstName(dto.getFirstName().get());
            if (dto.getLastName().isPresent())
                customer.setLastName(dto.getLastName().get());
            if (dto.getPhone().isPresent())
                customer.setPhone(dto.getPhone().get());
            customerRepo.save(customer);
            return ResponseEntity.ok("Customer was updated");
        } else {
            return ResponseEntity.badRequest().body(String.format("No customer with id %s found", id));
        }
    }

    public boolean isEmailUnique(String email) {
        return customerRepo.findByEmail(email).isPresent();
    }

    @Transactional
    public void updateCustomerRole(Long id, String role) {
        if (customerRepo.existsById(id)) {
            Customer customer = customerRepo.findCustomerById(id).get();
            customer.getRoles().clear();
            if (role.equalsIgnoreCase("ADMIN")) {
                customer.setRoles(Set.of(Role.ADMIN));
            } else if (role.equalsIgnoreCase("USER")) {
                customer.setRoles(Set.of(Role.USER));
            } else {
                customer.setRoles(Set.of(Role.USER));
            }
            customerRepo.save(customer);
        } else {
        }
        throw new ThrownException("No customer with such id");
    }
}
