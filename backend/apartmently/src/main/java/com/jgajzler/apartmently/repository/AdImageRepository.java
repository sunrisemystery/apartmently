package com.jgajzler.apartmently.repository;

import com.jgajzler.apartmently.entity.AdImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface AdImageRepository extends JpaRepository<AdImage, Long> {

    List<AdImage> findAllByAdId(@RequestParam("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "insert into ad_images (ad_id, image_url) values (?1,?2)", nativeQuery = true)
    void saveImageByAdId(Long id, String url);

}
