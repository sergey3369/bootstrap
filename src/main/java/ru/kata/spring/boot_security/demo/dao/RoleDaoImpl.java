package ru.kata.spring.boot_security.demo.dao;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {

    private EntityManager entityManager;

    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private Session session;


    @Override
    public List<Role> getRolesList() {
        session = entityManager.unwrap(Session.class);
        return session.createQuery("from Role").getResultList();
    }

    @Override
    public Role getRoleById(int id) {
        session = entityManager.unwrap(Session.class);
        return session.find(Role.class, id);
    }
}
