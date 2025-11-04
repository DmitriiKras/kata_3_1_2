package ru.kata.spring.boot_security.demo.configs.dao;

import ru.kata.spring.boot_security.demo.configs.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleDao {

    public List<Role> getAllRoles();
    public Optional<Role> findByName(String name);
    public void save(Role role);
    public Role getRoleById(Long id);
}