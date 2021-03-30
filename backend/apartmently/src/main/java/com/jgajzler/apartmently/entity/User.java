package com.jgajzler.apartmently.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "email_unique", columnNames = "email"),
        @UniqueConstraint(name = "username_unique", columnNames = "username"),

})
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "uuid", columnDefinition = "uuid default uuid_generate_v4()")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    @Column(name = "email", columnDefinition = "VARCHAR(100)", nullable = false)
    private String email;

    @Column(name = "password", columnDefinition = "VARCHAR(255)", nullable = false)
    private String password;

    @Column(name="username", columnDefinition = "VARCHAR(50)", nullable = false)
    private String username;

    @OneToOne(mappedBy = "user")
    private UserDetails userDetails;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Ad> ads;

    @ManyToMany(mappedBy = "usersFav")
    Set<Ad> favoriteAds;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", columnDefinition = "user_role_enum default 'COMMON_USER'")
    UserRole userRole;
}
