package com.example.cities.repositories;

import com.example.cities.models.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

    @Query(value = "SELECT * FROM cities_t WHERE name=?1",
            nativeQuery = true)
    Optional<City> findByName(String name);

    @Query(value = "SELECT * FROM cities_t WHERE name=?1 OR coordinates=?2",
            nativeQuery = true)
    List<City> findByNameOrCoordinates(String name, String coordinates);

    @Query(value = "SELECT * FROM cities_t " +
            "WHERE name LIKE ?1 OR brief_history LIKE ?1",
            nativeQuery = true)
    List<City> findByStroke(String stroke);

    @Query(value = "SELECT * FROM cities_t WHERE population >= ?1",
            nativeQuery = true)
    List<City> findByMinPopulation(Integer min);

    @Query(value = "SELECT * FROM cities_t WHERE population <= ?1",
            nativeQuery = true)
    List<City> findByMaxPopulation(Integer max);

    @Query(value = "SELECT * FROM cities_t " +
            "WHERE population >= ?1 AND population <= ?2",
            nativeQuery = true)
    List<City> findByPopulationRange(Integer min, Integer max);

    @Query(value = "SELECT * FROM cities_t " +
            "WHERE (name LIKE ?1 OR brief_history LIKE ?1) AND population >= ?2",
            nativeQuery = true)
    List<City> findByStrokeAndMinPopulation(String stroke, Integer min);

    @Query(value = "SELECT * FROM cities_t " +
            "WHERE (name LIKE ?1 OR brief_history LIKE ?1) AND population <= ?2",
            nativeQuery = true)
    List<City> findByStrokeAndMaxPopulation(String stroke, Integer max);

    @Query(value = "SELECT * FROM cities_t " +
            "WHERE (name LIKE ?1 OR brief_history LIKE ?1) AND (population >= ?2 AND population <= ?3)",
            nativeQuery = true)
    List<City> findByStrokeAndPopulationRange(String stroke, Integer min, Integer max);
}
