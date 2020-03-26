package com.example.ms.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "resources")
@NoArgsConstructor
@Getter @Setter
public class ResourcesEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private int metal;

    @Column(nullable = false)
    private int crystal;

    @Column(nullable = false)
    private int deuterium;

    @Column(nullable = false)
    private int energy;

    @Column(nullable = false)
    private int darkMatter;

}