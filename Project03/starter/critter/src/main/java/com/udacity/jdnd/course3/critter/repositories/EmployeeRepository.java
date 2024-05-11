package com.udacity.jdnd.course3.critter.repositories;


import com.udacity.jdnd.course3.critter.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    @Query("select e from Employee e where :dayOfWeek member of e.daysAvailable")
    List<Employee> findAllByDaysAvailableContaining(DayOfWeek dayOfWeek);
}