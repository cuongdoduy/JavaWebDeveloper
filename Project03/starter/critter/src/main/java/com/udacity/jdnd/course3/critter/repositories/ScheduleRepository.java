package com.udacity.jdnd.course3.critter.repositories;

import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
    @Query("select s from Schedule s join s.pets p where p.id = ?1")
    List<Schedule> getScheduleForPets(Long petId);

    @Query("select s from Schedule s join s.employees e where e.id = ?1")
    List<Schedule> getScheduleForEmployee(Long employeeId);

    List<Schedule> getAllByPetsIn(List<Pet> pets);
}