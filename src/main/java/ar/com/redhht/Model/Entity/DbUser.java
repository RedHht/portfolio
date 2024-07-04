package ar.com.redhht.Model.Entity;

import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name = "users")
public class DbUser{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY) private int id;
    @Column(name = "user") private String user;
    @Column(name = "password") private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

}
