package se.lexicon.anton.demo.data;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import se.lexicon.anton.demo.model.Book;

public interface BookRepo extends CrudRepository<Book, Integer>{

	List<Book> findByTitleStartsWithIgnoreCase(String title);
	List<Book> findByAvailable(boolean available);
	List<Book> findByReserved(boolean reserved);
}
