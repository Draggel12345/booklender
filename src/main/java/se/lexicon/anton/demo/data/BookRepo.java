package se.lexicon.anton.demo.data;

import org.springframework.data.repository.CrudRepository;

import se.lexicon.anton.demo.model.Book;

public interface BookRepo extends CrudRepository<Book, Integer>{

}
