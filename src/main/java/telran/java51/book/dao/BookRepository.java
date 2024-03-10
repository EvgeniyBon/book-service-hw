package telran.java51.book.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.java51.book.model.Book;
import telran.java51.book.model.Publisher;

public interface BookRepository extends JpaRepository<Book, String> {

	@Query("select b from Book b  join b.authors a  where a.name = ?1")
	List<Book> getBooksByAuthor(String author);

	@Query("select b from Book b  join b.publisher p  where p.publisherName = ?1")
	List<Book> findByPublisher(String publisher);

	@Query("select b.publisher from Book b join b.authors a where a.name = ?1")
	List<Publisher> getPublishersByAuthor(String author);

}
