package org.example.firstwebauth.Model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserBookRepository extends CrudRepository<UserBook, Integer> {
    @Query("select ub from UserBook ub inner join  Book b on ub.book.id=:bookId and ub.user.id=:userId")
    Optional<UserBook> findBooksByUserBooks(@Param("userId") Integer userId, @Param("bookId") Integer bookId);
}
