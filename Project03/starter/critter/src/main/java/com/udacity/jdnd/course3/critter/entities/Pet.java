package com.udacity.jdnd.course3.critter.entities;

import com.udacity.jdnd.course3.critter.types.PetType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;


    private PetType type;

    private String name;

    @ManyToOne(targetEntity = Customer.class)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private LocalDate birthDate;

    private String notes;

}
