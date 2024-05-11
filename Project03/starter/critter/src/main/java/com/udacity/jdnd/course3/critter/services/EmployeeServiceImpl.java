package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.DTO.EmployeeDTO;
import com.udacity.jdnd.course3.critter.DTO.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.mappers.EmployeeMapper;
import com.udacity.jdnd.course3.critter.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }


    @Override
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.employeeDTOToEmployee(employeeDTO);
        employee = employeeRepository.save(employee);
        return employeeMapper.employeeToEmployeeDTO( employee );
    }

    @Override
    public EmployeeDTO getEmployee(long employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isPresent()) {
            return employeeMapper.employeeToEmployeeDTO(employee.get());
        }
        return null;
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employeeMapper.employeesToEmployeeDTOs( employeeRepository.findAll() );
    }

    @Override
    public void setAvailability(Set<DayOfWeek> daysAvailable , long employeeId) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if (!employeeOptional.isPresent()) {
            return;
        }
        Employee employee = employeeOptional.get();
        employee.setDaysAvailable(daysAvailable);
        employeeRepository.save(employee);
    }

    @Override
    public List<EmployeeDTO> findEmployeesForService(EmployeeRequestDTO request) {
        DayOfWeek day = request.getDate().getDayOfWeek();
        List<Employee> employees = employeeRepository.findAllByDaysAvailableContaining(day).stream().filter(
            employee -> employee.getSkills().containsAll(request.getSkills())
        ).collect(Collectors.toList());
        return employeeMapper.employeesToEmployeeDTOs( employees );
    }
}
