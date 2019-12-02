package se.lexicon.anton.demo.testDto;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import se.lexicon.anton.demo.dto.BookDto;

public class TestBookDto {

	private BookDto testObject;
	
	@Before
	public void setUp() {
		testObject = new BookDto();
		testObject.setBookId(1);
		testObject.setTitle("Java");
		testObject.setAvailable(true);
		testObject.setReserved(false);
		testObject.setMaxLoanDays(30);
		testObject.setFinePerDay(BigDecimal.valueOf(10));
		testObject.setDescription("description");
	}
	
	@Test
	public void testObject_created_successfully() {
		assertEquals(1, testObject.getBookId());
		assertEquals("Java", testObject.getTitle());
		assertTrue(testObject.isAvailable());
		assertFalse(testObject.isReserved());
		assertEquals(30, testObject.getMaxLoanDays());
		assertEquals(BigDecimal.valueOf(10), testObject.getFinePerDay());
		assertEquals("description", testObject.getDescription());
	}
	
}
