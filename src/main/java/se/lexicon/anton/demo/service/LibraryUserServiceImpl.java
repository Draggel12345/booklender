package se.lexicon.anton.demo.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.lexicon.anton.demo.converters.EntityDtoConverter;
import se.lexicon.anton.demo.data.LibraryUserRepo;
import se.lexicon.anton.demo.dto.LibraryUserDto;
import se.lexicon.anton.demo.model.LibraryUser;

@Service
@Transactional
public class LibraryUserServiceImpl implements LibraryUserService {

	private LibraryUserRepo repo;
	private EntityDtoConverter converter;
	
	@Autowired
	public LibraryUserServiceImpl(LibraryUserRepo userRepo, EntityDtoConverter converter) {
		this.repo = userRepo;
		this.converter = converter;
	}

	@Override
	public Optional<LibraryUserDto> findById(int userId) {
		
		return Optional.of(converter.libraryUserToDto(repo.findById(userId).get()));
	}

	@Override
	public Optional<LibraryUserDto> findByEmail(String email) {
		
		return Optional.of(converter.libraryUserToDto(repo.findByEmailStartsWithIgnoreCase(email).get()));
		
	}

	@Override
	public List<LibraryUserDto> findAll() {
		return converter.libraryUsersToDtos(repo.findAll());
	}

	/**
	 * convertera dto till object.
	 * spara objectet i databasen.
	 * convertera det sparade objectet till dto.
	 * return nya dto.
	 */
	@Override
	public LibraryUserDto create(LibraryUserDto dto) {
		LibraryUser user = converter.dtoToLibraryUser(dto);
		repo.save(user);
		return converter.libraryUserToDto(user);
		
	}

	@Override
	public LibraryUserDto update(LibraryUserDto dto) {
		LibraryUser user = converter.dtoToLibraryUser(dto);
		Optional<LibraryUser> old = repo.findByUserId(user.getUserId());
		if(old.isPresent()) {
			
			LibraryUser original = old.get();
			
			original.setName(user.getName());
			original.setEmail(user.getEmail());
			
			repo.save(original);
			return converter.libraryUserToDto(original);
		}
		
		return converter.libraryUserToDto(user);
	}

	@Override
	public boolean delete(int userId) {
		Optional<LibraryUser> toRemove = repo.findById(userId);
		if(toRemove.isPresent()) {
			LibraryUser old = toRemove.get();
			repo.delete(old);
			return true;
		}
		return false;
	}
}
