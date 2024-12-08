package ar.com.redhht.Model.Table;

import ar.com.redhht.Model.Entity.DbUser;
import ar.com.redhht.Model.Entity.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostRepository{

    @PersistenceContext
    private EntityManager entityManager;

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

    @Transactional
    public void save(Post post) {
        entityManager.persist(post);
    }

    @Transactional
    public void remove(Post post) {
        entityManager.remove(post);
    }

    @Transactional
    public void update(Post post) {
        entityManager.merge(post);
    }

}
