package com.jgajzler.apartmently.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user_details")
@Getter
@Setter
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", columnDefinition = "VARCHAR(50)", nullable = false)
    private String name;

    @Column(name = "surname", columnDefinition = "VARCHAR(50)", nullable = false)
    private String surname;

    @Column(name = "phone_number", columnDefinition = "VARCHAR(12)", nullable = false)
    private String phoneNumber;

    @Column(name = "image_url", columnDefinition = "VARCHAR(200)")
    private String imageUrl;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
