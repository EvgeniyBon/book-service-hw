package telran.java51.book.service;

import telran.java51.book.dto.AuthorDto;
import telran.java51.book.dto.BookDto;

public interface BookService {
	boolean addBook(BookDto bookDto);

	BookDto findBookByIsbn(String isbn);

	BookDto removeBook(String isbn);

	BookDto updateBookTitle(String isbn, String title);

	Iterable<BookDto> getBooksByAuthor(String author);

	Iterable<BookDto> getBooksByPublisher(String publisher);

	Iterable<AuthorDto> getAuthors(String isbn);

	Iterable<String> getPublishersByAuthor(String author);

	AuthorDto removeAuthor(String author);
}
