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

    @Column(name = "author") private String author;

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
