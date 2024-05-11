package com.udacity.jdnd.course3.critter.mappers;

import com.udacity.jdnd.course3.critter.DTO.PetDTO;
import com.udacity.jdnd.course3.critter.entities.Pet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PetMapper {

    Pet petDTOToPet(PetDTO petDTO);

    @Mapping(target = "ownerId", source = "customer.id")
    PetDTO petToPetDTO(Pet pet);

    List<PetDTO> petsToPetDTOs(List<Pet> pets);


}
