package com.example.cities.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "cities_t")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class City extends BaseEntity {

    @Column
    private String name;

    @Column
    private Integer population;

    @Column(name = "brief_history", length = 1000)
    private String briefHistory;

    @Column
    private String coordinates;
}
