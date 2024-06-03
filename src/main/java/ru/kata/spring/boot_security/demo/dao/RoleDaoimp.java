package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RoleDaoimp implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Role> getAllRoles() {
        return entityManager.createQuery("from Role", Role.class).getResultList();
    }

    @Override
    public Set<Role> rolesSet() {
        return new HashSet<>(entityManager.createQuery("SELECT r FROM Role r").getResultList());
    }

    @Override
    public Role showRole(long id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public void addRole(Role role) {
        entityManager.persist(role);
    }

    @Override
    public Role showRoleName(String roleName) {
        String hql = "FROM Role r WHERE r.name = :name";
        Query query = entityManager.createQuery(hql);
        query.setParameter("name", roleName);
        return (Role) query.getSingleResult();
    }
}
