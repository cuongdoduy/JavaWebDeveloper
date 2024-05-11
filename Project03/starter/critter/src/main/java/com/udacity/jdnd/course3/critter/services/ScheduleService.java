package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.DTO.ScheduleDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ScheduleService {

    ScheduleDTO saveSchedule(ScheduleDTO scheduleDTO);

    List<ScheduleDTO> getSchedulesForPet(long petId);

    List<ScheduleDTO> getSchedulesForEmployee(long employeeId);

    List<ScheduleDTO> getSchedulesForCustomer(long customerId);

    List<ScheduleDTO> getAllSchedules();
}
