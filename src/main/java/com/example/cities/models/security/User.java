package com.example.cities.models.security;

import com.example.cities.models.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "users_t")
@Getter
@Setter
public class User extends BaseEntity implements UserDetails {

    @Column
    private String username;

    @Column
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role_t",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    @Column(name = "expired")
    private Boolean isAccountExpired;

    @Column(name = "locked")
    private Boolean isAccountLocked;

    @Column(name = "credentials_expired")
    private Boolean isCredentialsExpired;

    @Column(name = "enabled")
    private Boolean isEnabled;


    public User() {
        roles = new HashSet<>();
        isAccountExpired = false;
        isAccountLocked = false;
        isCredentialsExpired = false;
        isEnabled = true;
    }

    public User(String username, String password) {
        this();
        this.username = username;
        this.password = password;
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !isAccountExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isAccountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !isCredentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
