package ru.kata.spring.boot_security.demo.dao;


import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;
import javax.persistence.*;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getUsers() {
        Query query = entityManager.
                createQuery("SELECT e FROM User e");
        List<User> list = query.getResultList();
        return list;
    }

    @Override
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public User getUser(int id) {
        Query query = entityManager.
                createQuery("select u from User u where u.id = :id");
        query.setParameter("id", (long) id);
        return (User) query.getSingleResult();
    }

    @Override
    public void deleteUser(Integer id) {
        Query query = entityManager.
                createQuery("delete from User u where u.id = :id");
        query.setParameter("id", (long) id);
        query.executeUpdate();
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public User findByUsername(String username) {
        Query query = entityManager.
                createQuery("select u from User u where u.name = :name");
        query.setParameter("name", username);
        return (User) query.getSingleResult();
    }
}
