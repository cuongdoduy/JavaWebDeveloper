package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.DTO.CustomerDTO;
import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.mappers.CustomerMapper;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
import com.udacity.jdnd.course3.critter.repositories.PetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    private final PetRepository petRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper, PetRepository petRepository) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.petRepository = petRepository;
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
       Customer customer = customerMapper.customerDTOToCustomer( customerDTO) ;
       customer = customerRepository.save(customer);
       return customerMapper.customerToCustomerDTO(customer);
    }

    @Override
    public CustomerDTO getCustomer(long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        return customerMapper.customerToCustomerDTO(customer.get());
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customerMapper.customerToCustomerDTO(customers);
    }

    @Override
    public CustomerDTO getOwnerByPet(long petId) {
        Customer customer = petRepository.getOne(petId).getCustomer();
        return customerMapper.customerToCustomerDTO(customer);
    }
}
