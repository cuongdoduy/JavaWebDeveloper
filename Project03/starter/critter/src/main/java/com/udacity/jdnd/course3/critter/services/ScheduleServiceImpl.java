package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.DTO.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.mappers.ScheduleMapper;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
import com.udacity.jdnd.course3.critter.repositories.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repositories.PetRepository;
import com.udacity.jdnd.course3.critter.repositories.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final ScheduleMapper scheduleMapper;

    private final CustomerRepository customerRepository;

    private final EmployeeRepository employeeRepository;

    private final PetRepository petRepository;



    public ScheduleServiceImpl(ScheduleRepository scheduleRepository , ScheduleMapper scheduleMapper, CustomerRepository customerRepository, EmployeeRepository employeeRepository, PetRepository petRepository) {
        this.scheduleRepository = scheduleRepository;
        this.scheduleMapper = scheduleMapper;
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.petRepository = petRepository;
    }

    @Override
    public ScheduleDTO saveSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        schedule.setDate( scheduleDTO.getDate() );
        schedule.setActivities( scheduleDTO.getActivities() );
        List<Employee> employees = new ArrayList<>();
        for (Long employeeId : scheduleDTO.getEmployeeIds()) {
            employees.add( employeeRepository.getOne( employeeId ) );
        }
        schedule.setEmployees( employees );
        List<Pet> pets = new ArrayList<>();
        for (Long petId : scheduleDTO.getPetIds()) {
            pets.add( petRepository.getOne( petId ) );
        }
        schedule.setPets( pets );
        schedule = scheduleRepository.save( schedule );
        return scheduleMapper.scheduleToScheduleDTO( schedule );
    }

    @Override
    public List<ScheduleDTO> getSchedulesForPet(long petId) {
       List<Schedule> schedules = scheduleRepository.getScheduleForPets( petId );
       return scheduleMapper.schedulesToScheduleDTOs( schedules );
    }

    @Override
    public List<ScheduleDTO> getSchedulesForEmployee(long employeeId) {
        List<Schedule> schedules = scheduleRepository.getScheduleForEmployee( employeeId );
        return scheduleMapper.schedulesToScheduleDTOs( schedules );
    }

    @Override
    public List<ScheduleDTO> getSchedulesForCustomer(long customerId) {
        List<Pet> pets = customerRepository.getOne( customerId ).getPets();
        return scheduleRepository.getAllByPetsIn( pets ).stream().map( scheduleMapper::scheduleToScheduleDTO ).collect( Collectors.toList() );
    }

    @Override
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleRepository.findAll();
        return scheduleMapper.schedulesToScheduleDTOs( schedules );
    }
}
