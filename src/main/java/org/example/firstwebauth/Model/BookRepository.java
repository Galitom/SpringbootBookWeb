package org.example.firstwebauth.Model;

import org.example.firstwebauth.Controller.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Integer> {
}
