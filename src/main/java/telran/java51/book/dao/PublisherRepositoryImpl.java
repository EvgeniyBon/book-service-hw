package telran.java51.book.dao;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import telran.java51.book.model.Publisher;

@Repository
public class PublisherRepositoryImpl implements PublisherRepository {

	@PersistenceContext
	EntityManager em;



	@Override
	public Stream<Publisher> findDistinctByBooksAuthorsName(String authorName) {
		return em
				.createQuery("select p from Publisher p join p.books b join b.authors a  where a.name = :value",
						Publisher.class)
				.setParameter("value", authorName).getResultStream().distinct();
	}

	@Override
	public Optional<Publisher> findById(String publisher) {
		return Optional.ofNullable(em.find(Publisher.class, publisher));
	}

	@Override
	@Transactional
	public Publisher save(Publisher publisher) {
//		em.persist(publisher);
		em.merge(publisher);
		return publisher;
	}

}
