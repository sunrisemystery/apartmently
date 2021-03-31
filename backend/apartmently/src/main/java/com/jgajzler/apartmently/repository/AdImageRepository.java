package com.jgajzler.apartmently.repository;

import com.jgajzler.apartmently.entity.AdImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface AdImageRepository extends JpaRepository<AdImage, Long> {

    List<AdImage> findAllByAdId(@RequestParam("id") Long id);

}
