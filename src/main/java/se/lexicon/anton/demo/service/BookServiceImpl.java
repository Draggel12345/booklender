package se.lexicon.anton.demo.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.lexicon.anton.demo.converters.EntityDtoConverter;
import se.lexicon.anton.demo.data.BookRepo;
import se.lexicon.anton.demo.dto.BookDto;
import se.lexicon.anton.demo.model.Book;

@Service
@Transactional
public class BookServiceImpl implements BookService {
	
	private BookRepo repo;
	private EntityDtoConverter converter;
	
	@Autowired
	public BookServiceImpl(BookRepo repo, EntityDtoConverter converter) {
		this.repo = repo;
		this.converter = converter;
	}

	@Override
	public BookDto findById(int bookId) {
		Optional<Book> optional = repo.findById(bookId);
		Book book = optional.get();
		return converter.bookToDto(book);	
	}

	@Override
	public List<BookDto> findByTitle(String title) throws NoSuchElementException {
		List<Book> books = repo.findByTitleStartsWithIgnoreCase(title);
		if(books.isEmpty()) {
			throw new NoSuchElementException("There is no book with the title - " + title + " in the database.");
		} else {
			return converter.booksToDtos(books);
		}
	}

	@Override
	public List<BookDto> findByAvailable(boolean available) throws NoSuchElementException {
		List<Book> books = repo.findByAvailable(available);
		if(books.isEmpty()) {
			throw new NoSuchElementException("There are no available books in the database.");
		} else {
			return converter.booksToDtos(books);
		}
	}

	@Override
	public List<BookDto> findByReserved(boolean reserved) throws NoSuchElementException{
		List<Book> books = repo.findByReserved(reserved);
		if(books.isEmpty()) {
			throw new NoSuchElementException("There are no reserved books in the database.");
		} else {
			return converter.booksToDtos(books);
		}
	}

	@Override
	public List<BookDto> findAll() throws NoSuchElementException{
		List<Book> books = (List<Book>)repo.findAll();
		if(books.isEmpty()) {
			throw new NoSuchElementException("There are no books in the database.");
		} else {
			return converter.booksToDtos(books);
		}
	}

	@Override
	public BookDto create(BookDto bookDto) {
		Book book = converter.dtoToBook(bookDto);
		book = repo.save(book);
		return converter.bookToDto(book);
	}

	@Override
	public BookDto update(BookDto bookDto) {
		Book old = repo.findById(bookDto.getBookId()).get();
		
		old.setTitle(bookDto.getTitle());
		old.setDescription(bookDto.getDescription());
		old.setMaxLoanDays(bookDto.getMaxLoanDays());
		old.setFinePerDay(bookDto.getFinePerDay());
		
		repo.save(old);
		return bookDto;
	}

	@Override
	public boolean delete(int bookId) {
		Optional<Book> toRemove = repo.findById(bookId);
		if(toRemove.isPresent()) {
			Book old = toRemove.get();
			repo.delete(old);
			return true;
		}
		return false;
	}
}
