package com.udacity.pricing.domain.price;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Represents the price of a given vehicle, including currency.
 */
@Entity
@NoArgsConstructor()
@AllArgsConstructor()
@Data


@Table(name = "price")
public class Price {

    private String currency;
    private BigDecimal price;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long vehicleId;


}
