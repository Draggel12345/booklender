package se.lexicon.anton.demo.service;

import java.util.List;
import java.util.NoSuchElementException;
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
	public LibraryUserDto findById(int userId)  {
		Optional<LibraryUser> user = repo.findById(userId);
		LibraryUser original = user.get();
		return converter.libraryUserToDto(original);
	}

	@Override
	public LibraryUserDto findByEmail(String email) {
		Optional<LibraryUser> user = repo.findByEmail(email);
		LibraryUser original = user.get();
		return converter.libraryUserToDto(original);
	}

	@Override
	public List<LibraryUserDto> findAll() throws NoSuchElementException {
		List<LibraryUser> users = (List<LibraryUser>)repo.findAll();
		if(users.isEmpty()) {
			throw new NoSuchElementException("There are no library users in the database.");
		}
		return converter.libraryUsersToDtos(users);
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
		user = repo.save(user);
		return converter.libraryUserToDto(user);
		
	}

	@Override
	public LibraryUserDto update(LibraryUserDto dto) {
		LibraryUser old = repo.findById(dto.getUserId()).get();
		
		old.setName(dto.getName());
		old.setEmail(dto.getEmail());
			
		repo.save(old);
		return dto;
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
