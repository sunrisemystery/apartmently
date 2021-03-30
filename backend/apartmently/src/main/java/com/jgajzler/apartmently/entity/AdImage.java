package com.jgajzler.apartmently.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "ad_images")
@Getter
@Setter
public class AdImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "image_url", columnDefinition = "VARCHAR(200)", nullable = false)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "ad_id",nullable = false)
    private Ad ad;
}
