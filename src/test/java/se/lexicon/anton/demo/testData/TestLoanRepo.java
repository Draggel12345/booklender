package se.lexicon.anton.demo.testData;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import se.lexicon.anton.demo.data.LoanRepo;
import se.lexicon.anton.demo.model.Book;
import se.lexicon.anton.demo.model.LibraryUser;
import se.lexicon.anton.demo.model.Loan;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
public class TestLoanRepo {

	@Autowired
	private LoanRepo testObject;
	private Loan testLoan;
	private LibraryUser testUser;
	private Book testBook;
	
	@Before
	public void setUp() {
		testUser = new LibraryUser(LocalDate.of(2019, 10, 23), "Test", "Test@Lexicon.se");
		testBook = new Book("Java", 30, BigDecimal.valueOf(10), "programming");
		testLoan = new Loan(testUser, testBook, LocalDate.of(2019, 10, 22));
		testObject.save(testLoan);
	}
	
	@Test
	public void testLoan_saved_correctly_and_succesfully() {
		assertNotNull(testLoan);
		assertTrue(testLoan.getLoanId() != 0);
		assertEquals(testUser, testLoan.getLoanTaker());
		assertEquals(testBook, testLoan.getBook());
		assertEquals(LocalDate.of(2019, 10, 22), testLoan.getLoanDate());
	}
	
	@Test
	public void given_id_should_return_optional_of_loanId() {
		Long id = testLoan.getLoanId();
		Optional<Loan> result = testObject.findById(id);
		assertTrue(result.isPresent());
		assertEquals(id, result.get().getLoanId(), 0);
	}
	
	@Test
	public void given_user_id_should_return_list_size_of_1() {
		int id = testUser.getUserId();
		int expectedSize = 1;
		List<Loan> result = testObject.findByLoanTakerUserId(id);
		assertEquals(expectedSize, result.size());
		}
	
	@Test
	public void given_book_id_should_return_optional_of_testBook_id() {
		int id = testBook.getBookId();
		int expectedSize = 1;
		List<Loan> result = testObject.findByBookBookId(id);
		assertEquals(expectedSize, result.size());
	}
	
	@Test
	public void given_terminated_should_return_list_size_of_1() {
		testLoan.returnBook();
		int expectedSize = 1;
		List<Loan> terminated = testObject.findByTerminated(testLoan.isTerminated());
		assertEquals(expectedSize, terminated.size());
	}
}
