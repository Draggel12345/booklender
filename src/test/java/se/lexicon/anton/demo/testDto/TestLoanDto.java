package se.lexicon.anton.demo.testDto;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import se.lexicon.anton.demo.dto.BookDto;
import se.lexicon.anton.demo.dto.LibraryUserDto;
import se.lexicon.anton.demo.dto.LoanDto;

public class TestLoanDto {

	private LoanDto testObject;
	private LibraryUserDto testUser;
	private BookDto testBook;
	
	@Before
	public void setUp() {
	testUser = new LibraryUserDto();
	testUser.setUserId(1);
	testUser.setRegDate(LocalDate.of(2019, 10, 23));
	testUser.setName("Test");
	testUser.setEmail("Test@Lexicon.se");
	
	testBook = new BookDto();
	testBook.setBookId(1);
	testBook.setTitle("Java");
	testBook.setAvailable(true);
	testBook.setReserved(false);
	testBook.setMaxLoanDays(30);
	testBook.setFinePerDay(BigDecimal.valueOf(10));
	testBook.setDescription("description");
	
	testObject = new LoanDto(1, testUser, testBook, LocalDate.of(2019, 10, 24));
	}
	
	@Test
	public void testObject_created_successfully() {
		assertNotNull(testObject);
		assertTrue(testObject.getLoanId() != 0);
		assertEquals(1, testObject.getLoanId());
		assertEquals(testUser, testObject.getLoanTaker());
		assertEquals(testBook, testObject.getBook());
		assertEquals(LocalDate.of(2019, 10, 24), testObject.getLoanDate());
	}
}
