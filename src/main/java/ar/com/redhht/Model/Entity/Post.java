package ar.com.redhht.Model.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY) private int id;
    @Column(name = "title") private String title;
    @Column(name = "body") private String body;

    @ManyToOne
    @JoinColumn(name = "author")
    private DbUser user;

    public DbUser getUser() {
        return user;
    }

    public void setUser(DbUser user) {
        this.user = user;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String songName) {
        this.title = songName;
    }

    public String getBody() { return body; }

    public void setBody(String artist) {
        this.body = artist;
    }


}
