package com.jgajzler.apartmently.repository;

import com.jgajzler.apartmently.entity.Ad;
import com.jgajzler.apartmently.entity.AdType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {

    Page<Ad> findByAdType(@RequestParam("type") AdType adType, Pageable pageable);

    Optional<Ad> findAdById(@RequestParam("id") Long id);


}
