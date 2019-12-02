package se.lexicon.anton.demo.data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import se.lexicon.anton.demo.model.Book;

public interface BookRepo extends CrudRepository<Book, Integer>{

	Optional<Book> findByBookId(int bookId);
	List<Book> findByTitleStartsWithIgnoreCase(String title);
	List<Book> findByAvailable(boolean available);
	List<Book> findByReserved(boolean reserved);
	List<Book> findAll();
	
}
