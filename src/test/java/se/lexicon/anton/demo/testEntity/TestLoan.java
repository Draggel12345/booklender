package se.lexicon.anton.demo.testEntity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import se.lexicon.anton.demo.model.Book;
import se.lexicon.anton.demo.model.LibraryUser;
import se.lexicon.anton.demo.model.Loan;

public class TestLoan {

	private Loan testObject;
	private LibraryUser testUser;
	private Book testBook;
	
	@Before
	public void setUp() {
		testUser = new LibraryUser(LocalDate.of(2019, 10, 22), "Test", "Test@Lexicon");
		testBook = new Book("Testing", 30, BigDecimal.valueOf(10.00), "description");
		testObject = new Loan(0, testUser, testBook, LocalDate.of(2019, 12, 10));
	}
	
	@Test
	public void testObject_created_success() {
		assertEquals(0, testObject.getLoanId());
		assertEquals(testUser, testObject.getLoanTaker());
		assertEquals(testBook, testObject.getBook());
		assertEquals(LocalDate.of(2019, 12, 10), testObject.getLoanDate());
		assertFalse(testObject.isTerminated());
		assertNotNull(testObject);
	}
	
	@Test
	public void test_hashCode_and_equals() {
		testUser = new LibraryUser(LocalDate.of(2019, 10, 22), "Test", "Test@Lexicon");
		testBook = new Book("Testing", 30, BigDecimal.valueOf(10.00), "description");
		Loan copy = new Loan(0, testUser, testBook, LocalDate.of(2019, 12, 10));
		
		assertTrue(copy.equals(testObject));
		assertEquals(copy.hashCode(), testObject.hashCode());
	}
	
	@Test
	public void test_toString_contains_correct_information() {
		String toString = testObject.toString();
		
		assertTrue(
				toString.contains("0") &&
				toString.contains(testUser.toString()) &&
				toString.contains(testBook.toString()) &&
				toString.contains("2019-12-10")
				);
	}
	
	@Test
	public void testObject_is_not_overdue() {
		assertFalse(testObject.isOverdue());
	}
	
	@Test
	public void testObject_is_overdue() {
		Book book = new Book("Test book", 30, BigDecimal.valueOf(10.00), "description");
		Loan testLoan = new Loan(0, testUser, book, LocalDate.now().minusDays(31));
		
		assertTrue(testLoan.isOverdue());
	}
	
	@Test
	public void testObject_get_fine_of_50() {
		BigDecimal expected = BigDecimal.valueOf(50);
		Book overdue = new Book("Test book", 15, BigDecimal.valueOf(10), "description");
		
		LocalDate twentyDaysAgo = LocalDate.now().minusDays(20);
		Loan testLoan = new Loan(0, testUser, overdue, twentyDaysAgo);
		
		assertEquals(expected, testLoan.getFine());
	}
	
	@Test
	public void testObject_get_fine_of_0() {
		BigDecimal expected = BigDecimal.ZERO;
		Book bookInTime = new Book("Test book", 15, BigDecimal.valueOf(10), "description");
		
		LocalDate fifteenDaysAgo = LocalDate.now().minusDays(15);
		Loan testLoan = new Loan(0, testUser, bookInTime, fifteenDaysAgo);
		
		assertEquals(expected, testLoan.getFine());
	}
	
	@Test
	public void testObject_extendLoan_return_true() {
		Book book = new Book("Test book", 30, BigDecimal.valueOf(10), "description");
		Loan testLoan = new Loan(0, testUser, book, LocalDate.now().minusDays(30));
		
		assertTrue(testLoan.extendLoan());
	}
	
	@Test
	public void testObject_extendLoan_return_false() {
		Book bookOverdue = new Book("Test book", 30, BigDecimal.valueOf(10), "description");
		
		LocalDate ThirtyDaysAgo = LocalDate.now().minusDays(35);
		
		Loan testLoan = new Loan(0, testUser, bookOverdue, ThirtyDaysAgo);
		
		assertFalse(testLoan.extendLoan());
	}
	
	@Test
	public void test_extendLoan_on_reserved_book_returns_false() {
		Book reservedBook = new Book("Reserved", 30, BigDecimal.valueOf(10), "description");
		Loan theLoan = new Loan(0, testUser, reservedBook, LocalDate.now().minusDays(15));
		reservedBook.setReserved(true);
		
		assertFalse(theLoan.extendLoan());
	}
	
	@Test
	public void test_return_book_loanIsTerminated_and_bookIsAvailable() {
		testObject.returnBook();
		
		assertTrue(testObject.isTerminated());
		assertTrue(testBook.isAvailable());
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void test_overdue_book_throws_IllegalArgumentException() {
		Book unavailableBook = new Book("Testing", 30, BigDecimal.valueOf(10), "description");
		unavailableBook.setAvailable(false);
		
		new Loan(0, testUser, unavailableBook, LocalDate.now());
	}
	

	
	
	
}
