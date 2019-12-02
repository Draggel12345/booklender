package se.lexicon.anton.demo.testData;
import static org.junit.Assert.*;

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

import se.lexicon.anton.demo.data.LibraryUserRepo;
import se.lexicon.anton.demo.model.LibraryUser;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
public class TestLibraryUserRepo {

	@Autowired
	private LibraryUserRepo testObject;
	private LibraryUser testUser;
	
	@Before
	public void setUp() {
		testUser = new LibraryUser(LocalDate.of(2019, 10, 23), "Test", "Test@Lexicon.se");
		testObject.save(testUser);
	}
	
	@Test
	public void test_save_user_successfully_created() {
		assertNotNull(testUser);
		assertTrue(testUser.getUserId() != 0);
		assertEquals(LocalDate.of(2019, 10, 23), testUser.getRegDate());
		assertEquals("Test", testUser.getName());
		assertEquals("Test@Lexicon.se", testUser.getEmail());
	}
	
	@Test
	public void given_id_should_return_optional_of_librayUser_Id() {
		int id = testUser.getUserId();
		Optional<LibraryUser> result = testObject.findById(id);
		assertTrue(result.isPresent());
		assertEquals(id, result.get().getUserId());
	}
	
	@Test
	public void given_email_should_return_optional_of_libraryUser_email() {
		String email = "Test@Lexic";
		Optional<LibraryUser> result = testObject.findByEmailStartsWithIgnoreCase(email);
		assertTrue(result.isPresent());
		assertEquals("Test@Lexicon.se", result.get().getEmail());
	}
	
	@Test
	public void given_findAll_should_return_size_1_of_list() {
		int expectedSize = 1;
		List<LibraryUser> result = testObject.findAll();
		assertEquals(expectedSize, result.size());
	}
	
	@Test
	public void test_delete_testUser_should_return_optional_of_empty() {
		testObject.deleteById(testUser.getUserId());
		Optional<LibraryUser> empty = testObject.findById(testUser.getUserId());
		assertFalse(empty.isPresent());
		assertEquals(empty, Optional.empty());
	}
}
