package org.example.firstwebauth.Model;

import org.example.firstwebauth.Controller.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, Integer> {
    List<Book> findByUserId(int userId);
    Optional<Book> findById(int bookId);

}
