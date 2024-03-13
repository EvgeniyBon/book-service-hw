package telran.java51.book.dao;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import telran.java51.book.model.Author;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {
	@PersistenceContext
	EntityManager em;

	@Override
	public Optional<Author> findById(String authorName) {
		return Optional.ofNullable(em.find(Author.class, authorName));
	}

	@Override
	@Transactional
	public void deleteById(String authorName) {
	Author author = em.find(Author.class, authorName);
	em.remove(author);
	}

	@Override
	@Transactional
	public Author save(Author author) {
		em.persist(author);
		return author;
	}

}
