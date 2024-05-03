package ar.com.redhht.Config;

import ar.com.redhht.Model.Entity.DbUser;
import ar.com.redhht.Model.Table.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    @Autowired
    UserDetailsService userDetailsService(UserRepository userRepository) {
        InMemoryUserDetailsManager detailsManager = new InMemoryUserDetailsManager();

        for (DbUser u : userRepository.getAll()){
            detailsManager.createUser(User
                    .withUsername(u.getUser())
                    .password(u.getPassword())
                    .authorities("read")
                    .build());
        }

        return detailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
