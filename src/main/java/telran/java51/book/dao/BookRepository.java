package telran.java51.book.dao;



import org.springframework.data.jpa.repository.JpaRepository;

import telran.java51.book.model.Book;

public interface BookRepository extends JpaRepository<Book, String> {

}
