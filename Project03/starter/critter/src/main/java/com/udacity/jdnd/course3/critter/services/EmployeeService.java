package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.DTO.EmployeeDTO;
import com.udacity.jdnd.course3.critter.DTO.EmployeeRequestDTO;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Service
public interface EmployeeService {
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO);
    public EmployeeDTO getEmployee(long employeeId);
    public List<EmployeeDTO> getAllEmployees();
    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId);
    public List<EmployeeDTO> findEmployeesForService(EmployeeRequestDTO employeeRequestDTO);
}
