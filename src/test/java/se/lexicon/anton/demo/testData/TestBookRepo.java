package se.lexicon.anton.demo.testData;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import se.lexicon.anton.demo.data.BookRepo;
import se.lexicon.anton.demo.model.Book;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
public class TestBookRepo {

	@Autowired
	private BookRepo testObject;
	private Book testBook;
	
	@Before
	public void setUp() {
		testBook = new Book("Java", 30, BigDecimal.valueOf(10), "programming");
		testObject.save(testBook);
	}
	
	@Test
	public void testBook_saved_and_created_successfully() {
		assertNotNull(testBook);
		assertTrue(testBook.getBookId() != 0);
		assertEquals("Java", testBook.getTitle());
		assertEquals(30, testBook.getMaxLoanDays());
		assertEquals(BigDecimal.valueOf(10), testBook.getFinePerDay());
		assertEquals("programming", testBook.getDescription());
	}
	
	@Test
	public void given_id_should_return_optional_of_bookId() {
		int id = testBook.getBookId();
		Optional<Book> result = testObject.findById(id);
		assertTrue(result.isPresent());
		assertEquals(id, result.get().getBookId());
	}
	
	@Test
	public void given_jav_should_return_list_size_of_1() {
		String title = "jav";
		int expectedSize = 1;
		List<Book> result = testObject.findByTitleStartsWithIgnoreCase(title);
		assertEquals(expectedSize, result.size());
	}
	
	@Test
	public void book_is_available_should_return_list_size_of_1() {
		int expectedSize = 1;
		List<Book> result = testObject.findByAvailable(testBook.isAvailable());
		assertEquals(expectedSize, result.size());
	}
	
	@Test
	public void book_is_reserved_should_return_list_size_of_1() {
		testBook.setReserved(true);
		int expectedSize = 1;
		List<Book> result = testObject.findByReserved(testBook.isReserved());
		assertEquals(expectedSize, result.size());
	}
	
	@Test
	public void given_findAll_should_return_list_size_of_2() {
		Book book = new Book("C++", 30, BigDecimal.valueOf(10), "description");
		testObject.save(book);
		int expectedSize = 2;
		List<Book> result = (List<Book>) testObject.findAll();
		assertEquals(expectedSize, result.size());
	}
}
