package se.lexicon.anton.demo.service;

import java.util.List;
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
	public Optional<BookDto> findByBookId(int bookId) {
		return Optional.of(converter.bookToDto(repo.findById(bookId).get())); 
	}

	@Override
	public List<BookDto> findByTitle(String title) {
		return converter.booksToDtos(repo.findByTitleStartsWithIgnoreCase(title));
	}

	@Override
	public List<BookDto> findByAvailable(boolean available) {
		return converter.booksToDtos(repo.findByAvailable(available));
	}

	@Override
	public List<BookDto> findByReserved(boolean reserved) {
		return converter.booksToDtos(repo.findByReserved(reserved));
	}

	@Override
	public List<BookDto> findByAll() {
		return converter.booksToDtos(repo.findAll());
	}

	@Override
	public BookDto create(BookDto bookDto) {
		Book book = converter.dtoToBook(bookDto);
		repo.save(book);
		return converter.bookToDto(book);
	}

	@Override
	public BookDto update(BookDto bookDto) {
		Book book = converter.dtoToBook(bookDto);
		Optional<Book> old = repo.findByBookId(book.getBookId());
		if(old.isPresent()) {
			
			Book original = old.get();
			
			original.setTitle(book.getTitle());
			original.setAvailable(book.isAvailable());
			original.setReserved(book.isReserved());
			original.setMaxLoanDays(book.getMaxLoanDays());
			original.setFinePerDay(book.getFinePerDay());
			original.setDescription(book.getDescription());
			
			repo.save(original);
			return converter.bookToDto(original);
		}
		
		return converter.bookToDto(book);
	}

	@Override
	public boolean delete(int bookId) {
		Optional<Book> toRemove = repo.findByBookId(bookId);
		if(toRemove.isPresent()) {
			Book old = toRemove.get();
			repo.delete(old);
			return true;
		}
		return false;
	}
}
