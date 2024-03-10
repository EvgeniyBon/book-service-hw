package telran.java51.book.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import telran.java51.book.dao.AuthorRepository;
import telran.java51.book.dao.BookRepository;
import telran.java51.book.dao.PublisherRepository;
import telran.java51.book.dto.AuthorDto;
import telran.java51.book.dto.BookDto;
import telran.java51.book.dto.exceptions.EntityNotFoundException;
import telran.java51.book.model.Author;
import telran.java51.book.model.Book;
import telran.java51.book.model.Publisher;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
	final BookRepository bookRepository;
	final ModelMapper modelMapper;
	final PublisherRepository publisherRepository;
	final AuthorRepository authorRepository;

	@Override
	@Transactional
	public boolean addBook(BookDto bookDto) {
		if (bookRepository.existsById(bookDto.getIsbn())) {
			return false;
		}
		// Publisher
		Publisher publisher = publisherRepository.findById(bookDto.getPublisher())
				.orElse(publisherRepository.save(new Publisher(bookDto.getPublisher())));
		// Authors
		Set<Author> authors = bookDto.getAuthors().stream()
				.map(a -> authorRepository.findById(a.getName())
						.orElse(authorRepository.save(new Author(a.getName(), a.getBirthDate()))))
				.collect(Collectors.toSet());
		Book book = new Book(bookDto.getIsbn(), bookDto.getTitle(), authors, publisher);
		bookRepository.save(book);
		return true;
	}

	@Transactional(readOnly = true)
	@Override
	public BookDto findBookByIsbn(String isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
		return modelMapper.map(book, BookDto.class);
	}

	@Transactional
	@Override
	public BookDto removeBook(String isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
		bookRepository.delete(book);
		return modelMapper.map(book, BookDto.class);
	}

	@Transactional
	@Override
	public BookDto updateBookTitle(String isbn, String title) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
		if (title != null) {
			book.setTitle(title);
		}
		return modelMapper.map(book, BookDto.class);
	}

	@Transactional(readOnly = true)
	@Override
	public Iterable<BookDto> getBooksByAuthor(String author) {
		return bookRepository.getBooksByAuthor(author).stream().map(b -> modelMapper.map(b, BookDto.class)).toList();
	}

	@Transactional(readOnly = true)
	@Override
	public Iterable<BookDto> getBooksByPublisher(String publisher) {
		return bookRepository.findByPublisher(publisher).stream().map(b -> modelMapper.map(b, BookDto.class)).toList();
	}

	@Transactional(readOnly = true)
	@Override
	public Iterable<AuthorDto> getAuthors(String isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
		return book.getAuthors().stream().map(a -> modelMapper.map(a, AuthorDto.class)).toList();
	}

	@Transactional(readOnly = true)
	@Override
	public Iterable<String> getPublishersByAuthor(String author) {
		return bookRepository.getPublishersByAuthor(author).stream().map(p -> p.toString()).toList();
	}

	@Transactional
	@Override
	public AuthorDto removeAuthor(String author) {
		Author victim = authorRepository.findById(author).orElseThrow(EntityNotFoundException::new);
		bookRepository.getBooksByAuthor(author).stream()
				.peek(a -> a.getAuthors().removeIf(b -> b.getName().equals(author))).toList();
		authorRepository.delete(victim);
		return modelMapper.map(victim, AuthorDto.class);
	}
}
