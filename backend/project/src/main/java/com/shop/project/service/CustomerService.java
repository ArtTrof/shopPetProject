package com.shop.project.service;

import com.shop.project.dto.CustomerDTO;
import com.shop.project.models.Customer;
import com.shop.project.repository.CustomerRepo;
import com.shop.project.repository.RoleRepo;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements UserDetailsService {
    private final CustomerRepo customerRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new User(customer.getEmail(), customer.getPassword(), getGrantedAuthority(customer));
    }

    private Collection<GrantedAuthority> getGrantedAuthority(Customer customer) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if (customer.getRole().getRoleName().equalsIgnoreCase("admin")) {
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        }
        authorities.add(new SimpleGrantedAuthority("USER"));
        return authorities;
    }

    @Autowired
    public CustomerService(CustomerRepo customerRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.customerRepo = customerRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Customer> getCustomers() {
        return customerRepo.findAll();
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
                .role(roleRepo.findRoleById(Long.valueOf(1)).get())
                .password(passwordEncoder.encode(customer.getPassword()))
                .build();
        customerRepo.save(customerToSave);
        return null;
    }

    @Transactional
    public void updateCustomer(Long id, String email, String phone, String password) {
        var customer = customerRepo.findCustomerById(id);
        if (customer.isPresent()) {

        }
    }

    public boolean isEmailUnique(String email) {
        return customerRepo.findByEmail(email).isPresent();
    }
}
