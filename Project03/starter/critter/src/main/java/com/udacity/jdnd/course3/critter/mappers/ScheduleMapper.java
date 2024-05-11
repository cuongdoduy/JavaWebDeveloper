package com.udacity.jdnd.course3.critter.mappers;

import com.udacity.jdnd.course3.critter.DTO.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.types.EmployeeSkill;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    @Mapping(target = "employeeIds", source = "employees")
    @Mapping(target = "petIds", source = "pets")
    @Mapping(target = "activities")
    ScheduleDTO scheduleToScheduleDTO(Schedule schedule);

    Schedule scheduleDTOToSchedule(ScheduleDTO scheduleDTO);

    List<ScheduleDTO> schedulesToScheduleDTOs(List<Schedule> schedules);


    default List<Long> employeesToEmployeeIds(List<Employee> employees) {
        return employees.stream()
                .map(Employee::getId)
                .collect(Collectors.toList());
    }

    default List<Long> petsToPetIds(List<Pet> pets) {
        return pets.stream()
                .map(Pet::getId)
                .collect(Collectors.toList());
    }

    default Set<EmployeeSkill> mapActivities(Set<EmployeeSkill> activities) {
        return activities;
    }

    default List<Pet> petIdsToPets(List<Long> petIds) {
        return petIds.stream()
                .map(id -> {
                    Pet pet = new Pet();
                    pet.setId(id);
                    return pet;
                })
                .collect(Collectors.toList());
    }
}