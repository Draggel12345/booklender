package se.lexicon.anton.demo.service;

import java.util.List;
import java.util.Optional;

import se.lexicon.anton.demo.dto.LibraryUserDto;

public interface LibraryUserService {

	Optional<LibraryUserDto> findById(int userId);
	Optional<LibraryUserDto> findByEmail(String email);
	List<LibraryUserDto> findAll();
	LibraryUserDto create(LibraryUserDto dto);
	LibraryUserDto update(LibraryUserDto dto);
	boolean delete(int userId);
}
