package se.lexicon.anton.demo.testConverters;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import se.lexicon.anton.demo.converters.EntityDtoConverter;
import se.lexicon.anton.demo.dto.BookDto;
import se.lexicon.anton.demo.dto.LibraryUserDto;
import se.lexicon.anton.demo.dto.LoanDto;
import se.lexicon.anton.demo.model.Book;
import se.lexicon.anton.demo.model.EntityFactory;
import se.lexicon.anton.demo.model.LibraryUser;
import se.lexicon.anton.demo.model.Loan;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestEntityDtoConverter extends EntityFactory{

	@Autowired
	private EntityDtoConverter testObject;
	
	private LoanDto creatLoanDto() {
		LibraryUserDto userDto = new LibraryUserDto();
		userDto.setUserId(1);
		userDto.setRegDate(LocalDate.of(2019, 10, 23));
		userDto.setName("Test");
		userDto.setEmail("Test@Lexicon.se");
		
		BookDto bookDto = new BookDto();
		bookDto.setBookId(1);
		bookDto.setTitle("Java");
		bookDto.setMaxLoanDays(30);
		bookDto.setFinePerDay(BigDecimal.valueOf(10));
		bookDto.setDescription("description");
		
		LoanDto loanDto = new LoanDto();
		loanDto.setLoanId(1);
		loanDto.setLoanTaker(userDto);
		loanDto.setBook(bookDto);
		loanDto.setLoanDate(LocalDate.of(2019, 10, 24));
		return loanDto;
	}
	
	private Loan creatLoan() {
		LibraryUser user = creatLibraryUser(1, LocalDate.of(2019, 10, 23), "Test", "Test@Lexicon.se");
		
		Book book = creatBook(1, "Java", true, false, 30, BigDecimal.valueOf(10), "description");
		
		Loan loan = creatLoan(1, user, book, LocalDate.of(2019, 10, 24), false);
		
		return loan;
	}
	
	
	private LoanDto dtoLoan;
	private Loan loan;
	
	@Before
	public void setUp() {
		dtoLoan = creatLoanDto();
		loan = creatLoan();
	}
	
	@Test
	public void given_loan_should_return_loanDto() {
		LoanDto actual = testObject.loanToDto(loan);
		assertEquals(1, actual.getLoanId());
		assertEquals("Test", actual.getLoanTaker().getName());
		assertEquals("Java", actual.getBook().getTitle());
		assertEquals(LocalDate.of(2019, 10, 24), actual.getLoanDate());
	}
	
	@Test
	public void given_loanDto_should_return_loan() {
		Loan actual = testObject.dtoToLoan(dtoLoan);
		assertEquals(1, actual.getLoanId());
		assertEquals("Test", actual.getLoanTaker().getName());
		assertEquals("Java", actual.getBook().getTitle());
		assertEquals(LocalDate.of(2019, 10, 24), actual.getLoanDate());
	}
	
}
