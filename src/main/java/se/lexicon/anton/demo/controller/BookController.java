package se.lexicon.anton.demo.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import se.lexicon.anton.demo.dto.BookDto;
import se.lexicon.anton.demo.service.BookService;

@RestController
public class BookController {

	private BookService service;

	@Autowired
	public BookController(BookService service) {
		this.service = service;
	}
	
	@GetMapping("api/books/{bookId}")
	public ResponseEntity<BookDto> findById(@PathVariable("bookId") int bookId) {
		return ResponseEntity.ok().body(service.findById(bookId));
	}
	
	@GetMapping("api/books/search")
	public ResponseEntity<List<BookDto>> find(
			@RequestParam(defaultValue = "all", name = "type") String type,
			@RequestParam(required = false, name = "value") String value) {
		if(type.equalsIgnoreCase("all") && value == null) {
			return ResponseEntity.ok(service.findAll());
		} else if (type.equalsIgnoreCase("title")) {
			return ResponseEntity.ok(service.findByTitle(value));
		} else if(type.equalsIgnoreCase("available")) {
			return ResponseEntity.ok(service.findByAvailable(Boolean.valueOf(value)));
		} else if(type.equalsIgnoreCase("reserved")) {
			return ResponseEntity.ok(service.findByReserved(Boolean.valueOf(value)));
		} else {
			throw new NoSuchElementException(type + " is not found in the database.");
		}
	}
	
	@PostMapping("api/books")
	public ResponseEntity<BookDto> create(@RequestBody BookDto book) {
		if(book == null || book.getBookId() != 0) {
			throw new IllegalArgumentException();
		}
		return ResponseEntity.ok().body(service.create(book));
	}
	
	@PutMapping("api/books")
	public ResponseEntity<BookDto> update(@RequestBody BookDto book) {
		if(book == null || book.getBookId() == 0) {
			throw new IllegalArgumentException();
		}
		return ResponseEntity.ok(service.update(book));
	}
	
	@DeleteMapping("api/books/{bookId}") 
	public ResponseEntity<?> deleteById(@PathVariable("bookId") int bookId) {
		boolean isRemoved = service.delete(bookId);
		if(!isRemoved) {
			throw new NoSuchElementException("No book with id: " + bookId + " was found in the database.");
		}
		return new ResponseEntity<>("Book with id: " + bookId + " was removed from the database.", HttpStatus.OK);
	}
}
