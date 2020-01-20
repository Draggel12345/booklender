package se.lexicon.anton.demo.service;

import java.util.List;
import se.lexicon.anton.demo.dto.LibraryUserDto;

public interface LibraryUserService {

	LibraryUserDto findById(int userId);
	LibraryUserDto findByEmail(String email);
	List<LibraryUserDto> findAll();
	LibraryUserDto create(LibraryUserDto dto);
	LibraryUserDto update(LibraryUserDto dto);
	boolean delete(int userId);
}
