package com.jgajzler.apartmently.repository;

import com.jgajzler.apartmently.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    boolean existsByName(String name);

    @Query("select c.id from City c where c.name=?1")
    Long getIdByName(String name);


}
