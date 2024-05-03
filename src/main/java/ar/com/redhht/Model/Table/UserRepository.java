package ar.com.redhht.Model.Table;

import ar.com.redhht.Model.Entity.DbUser;
import ar.com.redhht.Model.Entity.Post;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRepository {
    private final EntityManager entityManager;

    @Autowired
    UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<DbUser> getAll() {
        return this.entityManager.createQuery("SELECT u FROM DbUser u", DbUser.class).getResultList();
    }
}
