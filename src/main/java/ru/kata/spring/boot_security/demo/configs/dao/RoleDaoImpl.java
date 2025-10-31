package ru.kata.spring.boot_security.demo.configs.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.configs.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
@Repository
public class RoleDaoImpl implements RoleDao{

    @PersistenceContext
    private EntityManager em;
    @Override
    public List<Role> getAllRoles() {
        return em.createQuery("FROM Role", Role.class).getResultList();
    }

    @Override
    public Optional<Role> findByName(String roleName) {
        List<Role> result = em.createQuery(
                        "SELECT r FROM Role r WHERE r.role = :role", Role.class)
                .setParameter("role", roleName)
                .getResultList();

        if (result.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(result.get(0));
        }
    }
    @Transactional
    @Override
    public void save(Role role) {
        if (role.getId() == null) {
            em.persist(role);
        } else {
            em.merge(role);
        }
    }

    @Override
    public Role getRoleById(Long id) {
        return em.find(Role.class, id);
    }
}
