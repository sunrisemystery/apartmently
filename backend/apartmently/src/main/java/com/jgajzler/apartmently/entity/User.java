package com.jgajzler.apartmently.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jgajzler.apartmently.entity.enums.UserRole;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;

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

    @Column(name = "email", columnDefinition = "VARCHAR(100)", nullable = false)
    private String email;

    @Column(name = "password", columnDefinition = "VARCHAR(255)", nullable = false)
    private String password;

    @Column(name = "username", columnDefinition = "VARCHAR(50)", nullable = false)
    private String username;

    @OneToOne(mappedBy = "user")
    private UserDetails userDetails;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "user")
    private Set<Ad> ads;

    @ManyToMany(mappedBy = "usersFav", cascade = CascadeType.REMOVE)
    Set<Ad> favoriteAds;

    @ManyToMany(mappedBy = "permittedUsers", cascade = CascadeType.REMOVE)
    Set<Ad> permittedAds;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", columnDefinition = "user_role_enum default 'COMMON_USER'")
    @Type(type = "pgsql_enum")
    UserRole userRole = UserRole.COMMON_USER;
}
