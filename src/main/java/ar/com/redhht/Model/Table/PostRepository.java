package ar.com.redhht.Model.Table;

import ar.com.redhht.Model.Entity.DbUser;
import ar.com.redhht.Model.Entity.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostRepository{

    private final EntityManager entityManager;

    PostRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Post> getAll() {
        return this.entityManager.createQuery("SELECT p FROM Post p", Post.class).getResultList();
    }

    public Post getById(int id) {
        try {
            return this.entityManager.createQuery("SELECT p FROM Post p WHERE id = :id", Post.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void save(Post post) {
        entityManager.getTransaction().begin();
        entityManager.persist(post);
        entityManager.getTransaction().commit();
    }

    public void update(Post post) {
        entityManager.getTransaction().begin();
        entityManager.merge(post);
        entityManager.getTransaction().commit();
    }

}
