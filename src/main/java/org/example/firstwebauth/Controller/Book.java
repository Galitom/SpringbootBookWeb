package org.example.firstwebauth.Controller;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.example.firstwebauth.Model.User;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Size(min=2, max=30)
    String titolo;

    @NotNull
    @Size(min=2, max=50)
    String autore;

    @Size(min=0, max=30)
    String descrizione;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Book() {
    }

    public Book(String titolo, String autore, String descrizione) {
        this.titolo = titolo;
        this.autore = autore;
        this.descrizione = descrizione;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    @Override
    public String toString() {
        return "Book{" +
                "titolo='" + titolo + '\'' +
                ", autore='" + autore + '\'' +
                ", descrizione='" + descrizione + '\'' +
                '}';
    }
}
