package ar.com.redhht.Model.Table;

import ar.com.redhht.Model.Entity.DbUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRepository implements UserDetailsService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<DbUser> getAll() {
        return entityManager.createQuery("SELECT u FROM DbUser u", DbUser.class).getResultList();
    }

    public DbUser getByUser(String userName) {
        try {
            return this.entityManager.createQuery("SELECT p FROM DbUser p WHERE user = :userName", DbUser.class)
                    .setParameter("userName", userName)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            DbUser dbUser = getByUser(username);
            return User.builder()
                    .username(dbUser.getUser())
                    .password(dbUser.getPassword())
                    .build();
        } catch (Exception e) {
            throw new UsernameNotFoundException("Username not found!");
        }


    }
}
