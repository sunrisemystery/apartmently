package com.jgajzler.apartmently.repository;

import com.jgajzler.apartmently.entity.Ad;
import com.jgajzler.apartmently.entity.enums.AdType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {

    Optional<Ad> findAdById(Long id);

    Page<Ad> findAllByUserId(Long id, Pageable pageable);

    Page<Ad> findAllByAdType(AdType adType, Pageable pageable);

    Page<Ad> findAll(Pageable pageable);

    @Query("select a from Ad  a where upper(a.adName) like concat('%',upper(?1),'%')" +
            " or upper(a.address.city.name) like upper(?1)" +
            "or upper(a.address.country.name) like upper(?1)")
    Page<Ad> findAllByKeyword(String keyword, Pageable pageable);

    Page<Ad> findAdsByUsersFavId(Long id, Pageable pageable);

}
