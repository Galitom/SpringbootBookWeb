package org.example.firstwebauth.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.example.firstwebauth.Model.User;

import java.util.HashSet;
import java.util.Set;

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

    @NotNull
    @Min(0)
    double price;

    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL, orphanRemoval = true)
    Set<UserBook> user_book;


    public Book() {
    }

    public Book(String titolo, String autore, String descrizione, double price) {
        this.titolo = titolo;
        this.autore = autore;
        this.descrizione = descrizione;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
