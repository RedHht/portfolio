package ar.com.redhht.Services;

import ar.com.redhht.Model.Entity.DbUser;
import ar.com.redhht.Model.Table.IUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsRepositoryService implements UserDetailsService {
    private final IUserRepository userRepository;

    public UserDetailsRepositoryService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<DbUser> dbUser = userRepository.getDbUserByUser(username);

        if (dbUser.isEmpty()) throw new UsernameNotFoundException("User not found.");

        return User.builder()
                .username(dbUser.get().getUser())
                .password(dbUser.get().getPassword())
                .build();
    }
}
