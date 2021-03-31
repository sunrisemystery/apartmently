package com.jgajzler.apartmently.repository;

import com.jgajzler.apartmently.entity.Ad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {

    Optional<Ad> findAdById(Long id);

    Page<Ad> findAllByUserId(Long id, Pageable pageable);


}
