package com.example.ms.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "building")
public class BuildingEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
