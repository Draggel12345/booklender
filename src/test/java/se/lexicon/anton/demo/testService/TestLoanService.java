package se.lexicon.anton.demo.testService;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import se.lexicon.anton.demo.converters.EntityDtoConverter;
import se.lexicon.anton.demo.data.LoanRepo;
import se.lexicon.anton.demo.dto.LoanDto;
import se.lexicon.anton.demo.model.Book;
import se.lexicon.anton.demo.model.LibraryUser;
import se.lexicon.anton.demo.model.Loan;
import se.lexicon.anton.demo.service.LoanService;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class TestLoanService {

	@Autowired 
	private LoanService testObject;
	@Autowired
	private LoanRepo repo;
	@Autowired
	private EntityDtoConverter converter;
	
	private LibraryUser user;
	private Book book;
	private Loan loan;
	private LibraryUser newUser;
	private Book newBook;
	private Loan newLoan;
	
	@Before
	public void setUp() {
		user = new LibraryUser(LocalDate.of(2019, 10, 23), "Test", "Test@Lexicon.se");
		
		book = new Book( "Java", 30, BigDecimal.valueOf(10), "description");
		
		loan = new Loan(user, book, LocalDate.of(2019, 10, 23));
		loan = repo.save(loan);
		
		newUser = new LibraryUser(LocalDate.of(2019, 10, 24), "Meh", "Meh@live.se");
		newBook = new Book("OCA", 30, BigDecimal.valueOf(10), "description");
		newLoan = new Loan(newUser, newBook, LocalDate.of(2019, 10, 24));
		newLoan = repo.save(newLoan);
	}
	
	@Test
	public void given_id_should_return_optional_of_loanId() {
		long id = loan.getLoanId();
		Optional<LoanDto> result = testObject.findByLoanId(id);
		assertTrue(result.isPresent());
		assertEquals(id, result.get().getLoanId());
	}
	
	@Test
	public void given_loan_taker_id_should_return_list_size_of_1() {
		int id = user.getUserId();
		int expectedSize = 1;
		List<LoanDto> result = testObject.findByLoanTakerId(id);
		assertNotNull(result);
		assertEquals(expectedSize, result.size());
	}
	
	@Test
	public void given_book_id_should_return_list_size_of_1() {
		int id = book.getBookId();
		int expectedSize = 1;
		List<LoanDto> result = testObject.findByBookId(id);
		assertNotNull(result);
		assertEquals(expectedSize, result.size());
	}
	
	@Test
	public void loan_set_terminated_should_return_list_size_of_1() {
		newLoan.returnBook();
		
		int expectedSize = 1;
		List<LoanDto> result = testObject.findByTerminated(newLoan.isTerminated());
		System.out.println(result.size());
		assertEquals(expectedSize, result.size());
		assertTrue(testObject.delete(newLoan.getLoanId()));
	}
	
	@Test
	public void testing_findByAll_method() {
		int expectedSize = 2;
		List<LoanDto> result = testObject.findByAll();
		assertEquals(expectedSize, result.size());
	}
	
	@Test
	public void create_object_from_dto_successfully() {
		LibraryUser newerUser = new LibraryUser(LocalDate.of(2019, 10, 24), "Emil", "Emil@hotmail.se");
		Book newerBook = new Book("OCA", 30, BigDecimal.valueOf(10), "description");
		Loan newerLoan = new Loan(newerUser, newerBook, LocalDate.of(2019, 10, 24));
		LoanDto dto = new LoanDto();
		dto.setLoanId(newerLoan.getLoanId());
		dto.setLoanTaker(converter.libraryUserToDto(newerLoan.getLoanTaker()));
		dto.setBook(converter.bookToDto(newerLoan.getBook()));
		dto.setLoanDate(newerLoan.getLoanDate());
		
		dto = testObject.create(dto);
		
		assertNotNull(dto);
		assertEquals(3, repo.count());
		
		assertTrue(testObject.delete(dto.getLoanId()));
		
	}
	
	@Test
	public void testing_update_method_on_new_dto_successfully() {
		Book test = new Book("C++", 30, BigDecimal.valueOf(10), "description");
		
		LoanDto toUpdate = new LoanDto();
		toUpdate.setLoanId(loan.getLoanId());
		toUpdate.setLoanTaker(converter.libraryUserToDto(loan.getLoanTaker()));
		toUpdate.setBook(converter.bookToDto(test));
		toUpdate.setLoanDate(loan.getLoanDate());
		
		toUpdate = testObject.update(toUpdate);
		System.out.println(toUpdate.getBook().getTitle());
		assertEquals("C++", toUpdate.getBook().getTitle());
	}
	
	@After
	public void after() {
		repo.deleteAll();
	}
}
