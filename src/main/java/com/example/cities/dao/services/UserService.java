package com.example.cities.dao.services;

import com.example.cities.dao.DAO;
import com.example.cities.models.security.User;
import com.example.cities.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService implements DAO<User>, UserDetailsService {

    private final UserRepository repository;

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<User> findById(Integer id) {
        return (id != null) && (id > 0)
                ? repository.findById(id)
                : Optional.empty();
    }

    public Optional<User> findByUsername(String username) {
        return username != null
                ? repository.findByUsername(username)
                : Optional.empty();
    }

    @Override
    public User save(User entity) {
        return repository.save(entity);
    }

    @Override
    public User update(User entity) {
        return save(entity);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = findByUsername(username);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException("Cannot find user with username=" + username);
        }
    }
}
