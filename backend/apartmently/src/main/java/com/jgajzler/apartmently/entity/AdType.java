package com.jgajzler.apartmently.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "ad_types",
        uniqueConstraints = {
                @UniqueConstraint(name = "type_name_unique", columnNames = "name")

        })
@Getter
@Setter
public class AdType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", columnDefinition = "VARCHAR(100)", nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "adType")
    private Set<Ad> ads;

}
