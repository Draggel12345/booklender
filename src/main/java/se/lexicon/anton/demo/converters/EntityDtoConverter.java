package se.lexicon.anton.demo.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import se.lexicon.anton.demo.dto.BookDto;
import se.lexicon.anton.demo.dto.LibraryUserDto;
import se.lexicon.anton.demo.dto.LoanDto;
import se.lexicon.anton.demo.model.Book;
import se.lexicon.anton.demo.model.EntityFactory;
import se.lexicon.anton.demo.model.LibraryUser;
import se.lexicon.anton.demo.model.Loan;

@Component
public class EntityDtoConverter extends EntityFactory {

	public LibraryUserDto libraryUserToDto(LibraryUser user) {
		LibraryUserDto dto = new LibraryUserDto();
		dto.setUserId(user.getUserId());
		dto.setRegDate(user.getRegDate());
		dto.setName(user.getName());
		dto.setEmail(user.getEmail()
				);
		return dto;
	}
	
	public List<LibraryUserDto> libraryUsersToDtos(Iterable<LibraryUser> iterable) {
		List<LibraryUserDto> dtos = new ArrayList<>();
		for(LibraryUser user : iterable) {
			dtos.add(libraryUserToDto(user));
		}
		return dtos;
	}
	
	public LibraryUser dtoToLibraryUser(LibraryUserDto dto) {
		LibraryUser user = creatLibraryUser(
				dto.getUserId(), 
				dto.getRegDate(), 
				dto.getName(), 
				dto.getEmail()
				);
		return user;
	}
	
	public List<LibraryUser> dtosToLibraryUsers(List<LibraryUserDto> dtos) {
		List<LibraryUser> users = new ArrayList<>();
		for(LibraryUserDto dto : dtos) {
			users.add(dtoToLibraryUser(dto));
		}
		return users;
	}
	
	public BookDto bookToDto(Book book) {
		BookDto dto = new BookDto();
		dto.setBookId(book.getBookId());
		dto.setTitle(book.getTitle());
		dto.setAvailable(book.isAvailable());
		dto.setReserved(book.isReserved());
		dto.setMaxLoanDays(book.getMaxLoanDays());
		dto.setFinePerDay(book.getFinePerDay());
		dto.setDescription(book.getDescription());
		return dto;
	}
	
	public List<BookDto> booksToDtos(Iterable<Book> iterable) {
		List<BookDto> dtos = new ArrayList<>();
		for(Book book : iterable) {
			dtos.add(bookToDto(book));
		}
		return dtos;
	}
	
	public Book dtoToBook(BookDto dto) {
		Book book = creatBook(
				dto.getBookId(), 
				dto.getTitle(),  
				dto.isAvailable(),
				dto.isReserved(),
				dto.getMaxLoanDays(), 
				dto.getFinePerDay(), 
				dto.getDescription()
				);
		return book;
	}
	
	public List<Book> dtosToBooks(List<BookDto> dtos) {
		List<Book> books = new ArrayList<>();
		for(BookDto dto : dtos) {
			books.add(dtoToBook(dto));
		}
		return books;
	}
	
	public LoanDto loanToDto(Loan loan) {
		LoanDto dto = new LoanDto();
		dto.setLoanId(loan.getLoanId());
		dto.setLoanTaker(libraryUserToDto(loan.getLoanTaker()));
		dto.setBook(bookToDto(loan.getBook()));
		dto.setLoanDate(loan.getLoanDate());
		dto.setTerminated(loan.isTerminated());
		return dto;
	}
	
	public List<LoanDto> loansToDtos(List<Loan> loans) {
		List<LoanDto> dtos = new ArrayList<>();
		for(Loan loan : loans) {
			dtos.add(loanToDto(loan));
		}
		return dtos;
	}
	
	public Loan dtoToLoan(LoanDto dto) {
		Loan loan = creatLoan(
				dto.getLoanId(), 
				dtoToLibraryUser(dto.getLoanTaker()), 
				dtoToBook(dto.getBook()), 
				dto.getLoanDate(),
				dto.isTerminated()
				);
		return loan;
	}
	
	public List<Loan> dtosToLoans(List<LoanDto> dtos) {
		List<Loan> loans = new ArrayList<>();
		for(LoanDto dto : dtos) {
			loans.add(dtoToLoan(dto));
		}
		return loans;
	}
	
}
