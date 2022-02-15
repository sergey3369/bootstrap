package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Role> getRole() {
        Query query = entityManager.
                createQuery("SELECT r FROM Role r");
        List<Role> list = query.getResultList();
        return list;
    }

    @Override
    public Role getRoleById(int id) {
        Query query = entityManager.
                createQuery("select r from Role r where r.id = :id");
        query.setParameter("id", (long) id);
        return (Role) query.getSingleResult();
    }
}
