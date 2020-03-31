package com.example.ms.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Data
@Entity
@Table(name = "buildings")
public class BuildingEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String name;

    @Min(value = 0)
    @Column(nullable = false)
    private int level;

    @Column(nullable = false)
    private String buildState;

    private String howLongToBuild;
}
