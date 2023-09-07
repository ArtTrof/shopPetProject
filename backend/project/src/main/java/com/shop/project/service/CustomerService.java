package com.shop.project.service;

import com.shop.project.dto.customer.CustomerDTO;
import com.shop.project.dto.customer.CustomerUpdateDTO;
import com.shop.project.models.Customer;
import com.shop.project.models.Role;
import com.shop.project.repository.CustomerRepo;
import com.shop.project.util.ThrownException;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.*;

@Service
public class CustomerService implements UserDetailsService {
    private final CustomerRepo customerRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomerService(CustomerRepo customerRepo, PasswordEncoder passwordEncoder) {
        this.customerRepo = customerRepo;
        this.passwordEncoder = passwordEncoder;
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
    public CustomerDTO saveNewCustomer(Customer customer) {
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
        return null;
    }

    @Transactional
    public void updateCustomer(Long id, CustomerUpdateDTO dto) {
        Customer customer = customerRepo.findCustomerById(id).get();
        if (dto.getEmail() != null && !customer.getEmail().equals(dto.getEmail()) && isEmailUnique(dto.getEmail()))
            customer.setEmail(dto.getEmail());
        if (dto.getPassword() != null)
            customer.setPassword(passwordEncoder.encode(dto.getPassword()));
        if (dto.getFirstName() != null)
            customer.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null)
            customer.setLastName(dto.getLastName());
        if (dto.getPhone() != null)
            customer.setPhone(dto.getPhone());
        customerRepo.save(customer);
    }

    public boolean isEmailUnique(String email) {
        return customerRepo.findByEmail(email).isPresent();
    }

    @Transactional
    public void updateCustomerRole(Long id, String role) {
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
    }
}
