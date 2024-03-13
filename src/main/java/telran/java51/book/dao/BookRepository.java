package telran.java51.book.dao;

import java.util.Optional;
import java.util.stream.Stream;

import telran.java51.book.model.Book;

public interface BookRepository  {
	Stream<Book> findByAuthorsName(String name);

	boolean existsById(String isbn);

	Book save(Book book);

	Optional<Book> findById(String isbn);

	void deleteById(String isbn);

	Stream<Book> findByPublisherName(String name);
}
