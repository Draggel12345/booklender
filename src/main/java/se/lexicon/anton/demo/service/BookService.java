package se.lexicon.anton.demo.service;

import java.util.List;
import se.lexicon.anton.demo.dto.BookDto;

public interface BookService {

	BookDto findById(int bookId);
	List<BookDto> findByTitle(String title);
	List<BookDto> findByAvailable(boolean available);
	List<BookDto> findByReserved(boolean reserved);
	List<BookDto> findAll();
	BookDto create(BookDto bookDto);
	BookDto update(BookDto bookDto);
	boolean delete(int bookId);
	
}
