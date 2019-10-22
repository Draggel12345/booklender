package se.lexicon.anton.demo.testEntity;

import se.lexicon.anton.demo.model.LibraryUser;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

public class TestLibraryUser {

	private LibraryUser testObject;
	
	@Before
	public void setUp() {
		testObject = new LibraryUser(0, LocalDate.of(2019, 10, 22), "Test", "Test@Lexicon.se");
	}
	
	@Test
	public void testObject_created_success() {
		assertEquals(0, testObject.getUserId());
		assertEquals(LocalDate.of(2019, 10, 22), testObject.getRegDate());
		assertEquals("Test", testObject.getName());
		assertEquals("Test@Lexicon.se", testObject.getEmail());
		assertNotNull(testObject);
	}
	
	@Test
	public void test_hashCode_and_equals() {
		LibraryUser copy = new LibraryUser(0, LocalDate.of(2019, 10, 22), "Test", "Test@Lexicon.se");
		
		assertTrue(copy.equals(testObject));
		assertEquals(copy.hashCode(), testObject.hashCode());
	}
	
	@Test
	public void test_toString_contains_correct_information() {
		String toString = testObject.toString();
		
		assertTrue(
				toString.contains("0") &&
				toString.contains("2019-10-22") &&
				toString.contains("Test") &&
				toString.contains("Test@Lexicon.se")
				);
	}
}
