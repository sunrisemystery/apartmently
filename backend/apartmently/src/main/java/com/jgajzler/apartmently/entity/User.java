package com.jgajzler.apartmently.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "email_unique", columnNames = "email")

})
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", columnDefinition = "VARCHAR(100)", nullable = false)
    private String email;

    @Column(name = "password", columnDefinition = "VARCHAR(255)", nullable = false)
    private String password;

    @OneToOne(mappedBy = "user")
    private UserDetails userDetails;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Ad> ads;

    @ManyToMany(mappedBy = "usersFav")
    Set<Ad> favoriteAds;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", columnDefinition = "user_role_enum default 'COMMON_USER'")
    UserRole userRole;

    public enum UserRole {
        ADMIN,
        COMMON_USER
    }

    ;


}
