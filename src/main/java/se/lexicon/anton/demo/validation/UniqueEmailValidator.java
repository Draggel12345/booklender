package se.lexicon.anton.demo.validation;

import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import se.lexicon.anton.demo.data.LibraryUserRepo;
import se.lexicon.anton.demo.model.LibraryUser;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

	private LibraryUserRepo repo;
	
	@Autowired
	public UniqueEmailValidator(LibraryUserRepo repo) {
		this.repo = repo;
	}

	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		Optional<LibraryUser> user = repo.findByEmail(email);
		return !user.isPresent();
	}
}
