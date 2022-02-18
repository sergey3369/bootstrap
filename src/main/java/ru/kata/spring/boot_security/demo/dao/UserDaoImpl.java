package ru.kata.spring.boot_security.demo.dao;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public User findUser(Long id) {
        Query query = entityManager.
                createQuery("SELECT u FROM User u where u.id =:id");
        query.setParameter("id", id);
        User user = (User) query.getSingleResult();
        return user;
    }

    @Override
    public User findUserByName(String email) {
        Query query = entityManager.
                createQuery("SELECT u FROM User u where u.email =:email");
        query.setParameter("email", email);
        User user = (User) query.getSingleResult();
        return user;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        Query query = entityManager.
                createQuery("SELECT e FROM User e");
        List<User> list = query.getResultList();
        return list;
    }

    @Override
    public void deleteUser(Long id) {
        Query query = entityManager.
                createQuery("delete from User u where u.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
