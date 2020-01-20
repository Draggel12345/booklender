package se.lexicon.anton.demo.testService;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.List;
import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import se.lexicon.anton.demo.data.BookRepo;
import se.lexicon.anton.demo.dto.BookDto;
import se.lexicon.anton.demo.model.Book;
import se.lexicon.anton.demo.service.BookService;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class TestBookService {
	
	@Autowired
	private BookService testObject;
	@Autowired
	private BookRepo repo;
	private Book book1;
	private Book book2;
	
	@Before
	public void setUp() {
		book1 = new Book("Java", 30, BigDecimal.valueOf(10), "description");
		book1 = repo.save(book1);
		book2 = new Book("OCA", 30, BigDecimal.valueOf(10), "description");
		book2 = repo.save(book2);
		book2.setReserved(true);
	}
	
	@Test
	public void given_id_should_return_optional_of_findById() {
		int id = book1.getBookId();
		BookDto result = testObject.findById(id);
		
		assertEquals(id, result.getBookId());
	}
	
	@Test
	public void given_title_should_return_list_size_of_1() {
		String title = book1.getTitle();
		int expectedSize = 1;
		List<BookDto> result = testObject.findByTitle(title);
		assertEquals(expectedSize, result.size());
	}
	
	@Test
	public void book_available_should_return_list_size_of_2() {
		boolean bookToFind = book1.isAvailable();
		int expectedSize = 2;
		List<BookDto> result = testObject.findByAvailable(bookToFind);
		assertEquals(expectedSize, result.size());
	}
	
	@Test
	public void book_reserved_should_return_list_size_of_1() {
		boolean bookToFind = book2.isReserved();
		int expectedSize = 1;
		List<BookDto> result = testObject.findByReserved(bookToFind);
		assertEquals(expectedSize, result.size());
	}
	
	@Test
	public void findAll_should_return_list_size_of_2() {
		int expectedSize = 2;
		List<BookDto> result = testObject.findAll();
		assertEquals(expectedSize, result.size());
	}
	
	@Test
	public void create_object_from_dto_successfully() {
		Book toCreate = new Book("Lexicon", 30, BigDecimal.valueOf(10), "description");
		BookDto dto = new BookDto();
		dto.setBookId(toCreate.getBookId());
		dto.setTitle(toCreate.getTitle());
		dto.setAvailable(toCreate.isAvailable());
		dto.setReserved(toCreate.isReserved());
		dto.setMaxLoanDays(toCreate.getMaxLoanDays());
		dto.setFinePerDay(toCreate.getFinePerDay());
		dto.setDescription(toCreate.getDescription());
		
		dto = testObject.create(dto);
		
		assertNotNull(dto);
		assertEquals(3, repo.count());
		
		assertTrue(testObject.delete(dto.getBookId()));
	}
	
	@Test
	public void testing_update_method_on_new_dto_successfully() {
		BookDto toUpdate = new BookDto();
		toUpdate.setBookId(book1.getBookId());
		toUpdate.setTitle("API");
		toUpdate.setAvailable(book1.isAvailable());
		toUpdate.setReserved(book1.isReserved());
		toUpdate.setMaxLoanDays(book1.getMaxLoanDays());
		toUpdate.setFinePerDay(book1.getFinePerDay());
		toUpdate.setDescription(book1.getDescription());
		
		toUpdate = testObject.update(toUpdate);
		
		assertEquals("API", toUpdate.getTitle());
	}
	
	@After
	public void after() {
		repo.deleteAll();
	}

}
