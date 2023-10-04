package com.shop.project.service;

import com.shop.project.dto.customer.CustomerDTO;
import com.shop.project.dto.customer.CustomerUpdateDTO;
import com.shop.project.models.Customer;
import com.shop.project.models.CustomerTmp;
import com.shop.project.models.Role;
import com.shop.project.repository.CartItemRepo;
import com.shop.project.repository.CustomerRepo;
import com.shop.project.repository.CustomerTempPasswordRepo;
import com.shop.project.util.ThrownException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.UUID;

@Service
public class CustomerService implements UserDetailsService {
    private final CustomerTempPasswordRepo customerTmpRepo;
    private final CustomerRepo customerRepo;
    private final PasswordEncoder passwordEncoder;
    private final CartItemRepo cartItemRepo;
    private final MailSender mailSender;
    @Value("${frontend_url}")
    private String FRONT_URL;

    @Autowired
    public CustomerService(CustomerTempPasswordRepo customerTmpRepo, CustomerRepo customerRepo, PasswordEncoder passwordEncoder, CartItemRepo cartItemRepo, MailSender mailSender) {
        this.customerTmpRepo = customerTmpRepo;
        this.customerRepo = customerRepo;
        this.passwordEncoder = passwordEncoder;
        this.cartItemRepo = cartItemRepo;
        this.mailSender = mailSender;
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

    public ResponseEntity<?> checkEmailAndSendLinkToResetPassword(String email) {
        Optional<Customer> customerRepoByEmail = customerRepo.findByEmail(email);
        if (customerRepoByEmail.isPresent()) {
            String uniqueId = UUID.randomUUID().toString();
            Customer customer = customerRepoByEmail.get();
            customerTmpRepo.save(CustomerTmp.builder().id(uniqueId).customer(customer).build());
            String message = String.format("Hello ,%s" +
                    "\nIf you want to reset the password to your account go to this link:" +
                    "\n%s/reset-password/%s" +
                    "\nIf it's not you,just ignore the email!", customer.getFirstName(), FRONT_URL, uniqueId);
            mailSender.send(email, "Password reset", message);
            return ResponseEntity.ok().body("The email with instruction was sent on your email,if you don't see it check spam");
        } else {
            return ResponseEntity.badRequest().body("There is no customer with email:" + email);
        }
    }

    public ResponseEntity<?> updateCustomerPassword(String uniqueId, String newPassword) {
        Optional<CustomerTmp> customerTmpRepoById = customerTmpRepo.findById(uniqueId);
        if (customerTmpRepoById.isPresent()) {
            Customer customer = customerTmpRepoById.get().getCustomer();
            customer.setPassword(passwordEncoder.encode(newPassword));
            customerRepo.save(customer);
            customerTmpRepo.delete(customerTmpRepoById.get());
            return ResponseEntity.ok().body("New password set successfully");
        } else {
            return ResponseEntity.badRequest().body("Something went wrong :(");
        }
    }
}
