package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.DTO.PetDTO;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface PetService {
    public PetDTO savePet(PetDTO petDTO);
    public PetDTO getPet(long petId);
    public List<PetDTO> getPetsByOwner(long ownerId);
    public List<PetDTO> getAllPets();
}
