package se.lexicon.anton.demo.testService;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.List;
import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import se.lexicon.anton.demo.data.LibraryUserRepo;
import se.lexicon.anton.demo.dto.LibraryUserDto;
import se.lexicon.anton.demo.model.LibraryUser;
import se.lexicon.anton.demo.service.LibraryUserService;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class TestLibraryUserService {

	@Autowired
	private LibraryUserService testObject;
	@Autowired
	private LibraryUserRepo repo;
	private LibraryUser user;

	
	@Before
	public void setUp() {
		user = new LibraryUser(LocalDate.of(2019, 10, 23), "Test", "Test@Lexicon.se");
		user = repo.save(user);
	}
	
	@Test
	public void given_id_should_be_equal_of_findById() {
		int id = user.getUserId();
		System.out.println(id);
		LibraryUserDto result = testObject.findById(id);
		
		assertEquals(id, result.getUserId());
	}
	
	@Test
	public void given_email_should_be_equal_of_findByEmail() {
		String email = user.getEmail();
		LibraryUserDto result = testObject.findByEmail(email);
		
		assertEquals(email, result.getEmail());
	}
	
	@Test
	public void find_all_should_return_list_size_of_1() {
		int expectedSize = 1;
		List<LibraryUserDto> result = testObject.findAll();
		assertEquals(expectedSize, result.size());
	}
	
	@Test
	public void create_object_from_dto_successfully() {
		LibraryUser one = new LibraryUser(LocalDate.of(2019, 10, 22), "Test", "Test@Hotmail.se");
		LibraryUserDto test = new LibraryUserDto();
		test.setUserId(one.getUserId());
		test.setRegDate(one.getRegDate());
		test.setName(one.getName());
		test.setEmail(one.getEmail());
		
		test = testObject.create(test);
		
		assertNotNull(test);
		assertEquals(2, repo.count());
		
		assertTrue(testObject.delete(test.getUserId()));
		
	}
	
	@Test
	public void testing_update_method_on_new_dto_successfully() {
		LibraryUserDto toUpdate = new LibraryUserDto();
		toUpdate.setUserId(user.getUserId());
		toUpdate.setRegDate(user.getRegDate());
		toUpdate.setName("Anton");
		toUpdate.setEmail("anton.edholm@Lexicon.se");
		
		toUpdate = testObject.update(toUpdate);
		
		assertEquals("Anton", toUpdate.getName());
		assertEquals("anton.edholm@Lexicon.se", toUpdate.getEmail());
		
	}
	
	@After
	public void after() {
		repo.delete(user);
	}
	
}
