package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.DTO.PetDTO;
import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.mappers.PetMapper;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
import com.udacity.jdnd.course3.critter.repositories.PetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final PetMapper petMapper;

    private final CustomerRepository customerRepository;

    public PetServiceImpl(PetRepository petRepository, PetMapper petMapper, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.petMapper = petMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public PetDTO savePet(PetDTO petDTO) {
        Pet pet = petMapper.petDTOToPet( petDTO );
        Customer customer = customerRepository.getOne(petDTO.getOwnerId());
        pet.setCustomer(customer);
        pet = petRepository.save(pet);
        customer.addPet(pet);
        customerRepository.save(customer);
        return petMapper.petToPetDTO( pet );
    }

    @Override
    public PetDTO getPet(long petId) {
        Optional<Pet> pet = petRepository.findById( petId );
        return petMapper.petToPetDTO( pet.get() );
    }

    @Override
    public List<PetDTO> getPetsByOwner(long ownerId) {
       List<Pet> pets = petRepository.getAllByOwnerId(ownerId);
       return petMapper.petsToPetDTOs( pets );
    }

    @Override
    public List<PetDTO> getAllPets() {
        List<Pet> pets = petRepository.findAll();
        return petMapper.petsToPetDTOs( pets );
    }
}
