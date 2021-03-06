package se.lexicon.anton.demo.data;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import se.lexicon.anton.demo.model.LibraryUser;

public interface LibraryUserRepo extends CrudRepository<LibraryUser, Integer> {

	Optional<LibraryUser> findByEmail(String email);
}
