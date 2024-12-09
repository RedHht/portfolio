package ar.com.redhht.Model.Table;

import ar.com.redhht.Model.Entity.DbUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IUserRepository extends CrudRepository<DbUser, Long> {
    Optional<DbUser> getDbUserByUser(String username);
}
