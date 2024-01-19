package org.example.firstwebauth.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "user_book")
public class UserBook {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;


    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    Book book;
    public UserBook() {
    }

    public UserBook(User user, Book book) {
        this.user = user;
        this.book = book;
    }
}
