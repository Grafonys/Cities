package com.example.cities.dao.services;

import com.example.cities.dao.DAO;
import com.example.cities.models.security.Role;
import com.example.cities.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RoleService implements DAO<Role> {

    private final RoleRepository repository;

    @Override
    public List<Role> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Role> findById(Integer id) {
        return (id != null) && (id > 0)
                ? repository.findById(id)
                : Optional.empty();
    }

    public Optional<Role> findByName(String name) {
        return name != null
                ? repository.findByName(name)
                : Optional.empty();
    }

    @Override
    public Role save(Role entity) {
        return repository.save(entity);
    }

    @Override
    public Role update(Role entity) {
        return save(entity);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
