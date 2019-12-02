package se.lexicon.anton.demo.testDto;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import se.lexicon.anton.demo.dto.LibraryUserDto;

public class TestLibraryUserDto {

	private LibraryUserDto testObject;
	
	@Before
	public void setUp() {
		testObject = new LibraryUserDto();
		testObject.setUserId(1);
		testObject.setRegDate(LocalDate.of(2019, 10, 23));
		testObject.setName("Test");
		testObject.setEmail("Test@Lexicon.se");
	}
	
	@Test
	public void testObject_created_successfully() {
		assertEquals(1, testObject.getUserId());
		assertEquals(LocalDate.of(2019, 10, 23), testObject.getRegDate());
		assertEquals("Test", testObject.getName());
		assertEquals("Test@Lexicon.se", testObject.getEmail());
	}
	
}
