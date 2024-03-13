package telran.java51.book.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import telran.java51.book.model.Book;

@Repository
public class BookRepositoryImpl implements BookRepository {
	@PersistenceContext
	EntityManager em;

	@Override
	public Stream<Book> findByAuthorsName(String name) {
		List<Book> books = em.createQuery("select b from Book b join b.authors a where a.name = :value", Book.class)
				.setParameter("value", name).getResultList();
		return books.stream();
	}

	@Override
	public Stream<Book> findByPublisherName(String name) {
		List<Book> books = em.createQuery("select b from Book b join b.publisher p where p.name = :value", Book.class)
				.setParameter("value", name).getResultList();
		return books.stream();
	}



	@Override
	public boolean existsById(String isbn) {
		return em.find(Book.class, isbn) != null;
	}

	@Override
	@Transactional
	public Book save(Book book) {
		em.persist(book);
		return book;
	}

	@Override
	public Optional<Book> findById(String isbn) {
		return Optional.of(em.find(Book.class, isbn));
	}

	@Override
	@Transactional
	public void deleteById(String isbn) {
		Book book = em.find(Book.class, isbn);
		em.remove(book);

	}

}
