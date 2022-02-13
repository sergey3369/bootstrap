package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

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
    public List<Role> getStringArrayToSetRole(String[] roles) {
        List<Role> list = new ArrayList<>();
        for (String string:roles){
            Query query = entityManager.
                    createQuery("SELECT e FROM Role e where e.role=:rol");
            query.setParameter("rol",string);
            list.add((Role) query.getSingleResult());
        }
        return list;
    }
}
