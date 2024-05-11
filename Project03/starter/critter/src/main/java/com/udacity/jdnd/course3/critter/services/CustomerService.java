package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.DTO.CustomerDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {
    public CustomerDTO saveCustomer(CustomerDTO customerDTO);
    public CustomerDTO getCustomer(long customerId);
    public List<CustomerDTO> getAllCustomers();
    public CustomerDTO getOwnerByPet(long petId);
}
