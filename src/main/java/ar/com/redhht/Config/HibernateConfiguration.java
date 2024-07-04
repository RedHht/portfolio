package ar.com.redhht.Config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class HibernateConfiguration {

    @Value("${mysql.host}")
    String host;
    @Value("${mysql.database}")
    String database;
    @Value("${mysql.user}")
    String username;
    @Value("${mysql.password}")
    String password;

    @Bean
    EntityManager newEntityManager() {
        String url = "jdbc:mysql://" + host + ":3306/" + database;

        HashMap<String, String> map = new HashMap<>();
        map.put("jakarta.persistence.jdbc.url", url);
        map.put("jakarta.persistence.jdbc.user", this.username);
        map.put("jakarta.persistence.jdbc.password", this.password);

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(
                "jpa-hibernate-mysql",
                map);
        return entityManagerFactory.createEntityManager();
    }

}
