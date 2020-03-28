package com.example.ms.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "buildings")
@NoArgsConstructor
@Getter
@Setter
public class BuildingEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String userId;

    private String name;

    private int level;

    private Date howLongToBuild;

    private String buildState;
}
