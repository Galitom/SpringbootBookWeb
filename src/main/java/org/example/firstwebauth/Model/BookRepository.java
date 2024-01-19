package org.example.firstwebauth.Model;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, Integer> {
    @Query("SELECT b FROM Book b JOIN b.users u WHERE u.id = :userId")
    List<Book> findBooksByUserId(@Param("userId") Integer userId);
}
