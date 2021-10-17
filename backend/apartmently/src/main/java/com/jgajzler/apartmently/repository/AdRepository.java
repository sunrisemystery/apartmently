package com.jgajzler.apartmently.repository;

import com.jgajzler.apartmently.entity.Ad;
import com.jgajzler.apartmently.entity.enums.AdType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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


    @Query(value = "select ad_id from users_favorites where user_id = ?", nativeQuery = true)
    List<Long> findAdsIdByUsersFavId(Long id);


    @Transactional
    @Modifying
    @Query("update Ad ad set ad.isActive = ?2 where ad.id = ?1")
    void setActiveById(Long id, boolean isActive);


}
