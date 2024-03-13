package telran.java51.book.dao;

import java.util.Optional;
import java.util.stream.Stream;

import telran.java51.book.model.Publisher;

public interface PublisherRepository {

//	@Query("select p.publisherName from Book b join b.authors a join b.publisher p where a.name = ?1")
//	List<String> findPublishersByAuthor(String authorName);

	Stream<Publisher> findDistinctByBooksAuthorsName(String authorName);

	Optional<Publisher> findById(String publisher);

	Publisher save(Publisher publisher);
}
