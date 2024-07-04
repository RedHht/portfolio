package ar.com.redhht;

import ar.com.redhht.Model.Entity.DbUser;
import ar.com.redhht.Model.Table.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.yaml.snakeyaml.Yaml;

import java.io.File;

@SpringBootApplication
public class RedhhtApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedhhtApplication.class, args);
    }

}
