package ru.inno.stc.entity;

import java.util.List;
import java.util.Objects;

public class Post {

    private int          likes;
    private String       title;
    private List<String> comments;

    public Post() {
    }

    public Post(String title, int likes, List<String> comments) {
        this.likes    = likes;
        this.comments = comments;
        this.title    = title;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Post{" +
               ", likes=" + likes +
               ", title='" + title + '\'' +
               ", comments=" + comments +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Post post = (Post) o;
        return likes == post.likes && Objects.equals(title, post.title) && Objects.equals(comments, post.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(likes, title, comments);
    }
}
