package com.example.cities.dao.services;

import com.example.cities.dao.DAO;
import com.example.cities.models.City;
import com.example.cities.repositories.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CityService implements DAO<City> {

    private final CityRepository repository;

    @Override
    public List<City> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<City> findById(Integer id) {
        return (id != null) && (id > 0)
                ? repository.findById(id)
                : Optional.empty();
    }

    public Optional<City> findByName(String name) {
        return name != null
                ? repository.findByName(name)
                : Optional.empty();
    }

    public List<City> findByStroke(String stroke) {
        return repository.findByStroke(String.format("%%%s%%", stroke));
    }

    public List<City> findByStrokeAndMinPopulation(String stroke,
                                                   Integer min) {
        return repository.findByStrokeAndMinPopulation(
                String.format("%%%s%%", stroke),
                min);
    }

    public List<City> findByStrokeAndMaxPopulation(String stroke,
                                                   Integer max) {
        return repository.findByStrokeAndMaxPopulation(
                String.format("%%%s%%", stroke),
                max);
    }

    public List<City> findByStrokeAndPopulationRange(String stroke,
                                                     Integer min,
                                                     Integer max) {
        return repository.findByStrokeAndPopulationRange(
                String.format("%%%s%%", stroke),
                min,
                max);
    }

    public List<City> findByNameOrCoordinates(String name,
                                              String coordinates) {
        return repository.findByNameOrCoordinates(name, coordinates);
    }

    public List<City> findByMinPopulation(Integer min) {
        return repository.findByMinPopulation(min);
    }

    public List<City> findByMaxPopulation(Integer max) {
        return repository.findByMaxPopulation(max);
    }

    public List<City> findByPopulationRange(Integer min, Integer max) {
        return repository.findByPopulationRange(min, max);
    }

    @Override
    public City save(City entity) {
        return repository.save(entity);
    }

    @Override
    public City update(City entity) {
        return save(entity);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
