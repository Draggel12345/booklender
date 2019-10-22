package se.lexicon.anton.demo.testEntity;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import se.lexicon.anton.demo.model.Book;

public class TestBook {

	private Book testObject;
	
	@Before
	public void setUp() {
		testObject = new Book(0, "Test name", 30, BigDecimal.valueOf(10.00), "Test description");
	}
	
	@Test
	public void testObject_created_success() {
		assertEquals(0, testObject.getBookId());
		assertEquals("Test name", testObject.getTitle());
		assertEquals(30, testObject.getMaxLoanDays());
		assertEquals(BigDecimal.valueOf(10.00), testObject.getFinePerDay());
		assertEquals("Test description", testObject.getDescription());
		assertNotNull(testObject);
	}
	
	@Test
	public void test_hashCode_and_equals() {
		Book copy = new Book(0, "Test name", 30, BigDecimal.valueOf(10.00), "Test description");
		
		assertTrue(copy.equals(testObject));
		assertEquals(copy.hashCode(), testObject.hashCode());
	}
	
	@Test
	public void test_toString_contains_correct_information() {
		String toString = testObject.toString();
		
		assertTrue( 
				toString.contains("0") &&
				toString.contains("Test name") &&
				toString.contains("30") &&
				toString.contains(testObject.getFinePerDay().toString()) &&
				toString.contains("Test description")
				);
	}
	
}
