package se.lexicon.anton.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import se.lexicon.anton.demo.dto.LibraryUserDto;
import se.lexicon.anton.demo.service.LibraryUserService;
import se.lexicon.anton.demo.validation.OnCreate;
import se.lexicon.anton.demo.validation.OnUpdate;

@RestController
public class LibraryUserController {

	private LibraryUserService service;
	
	@Autowired
	public LibraryUserController(LibraryUserService service) {
		this.service = service;
	}
	
	@GetMapping("api/library_users/{userId}")
	public ResponseEntity<LibraryUserDto> findById(@PathVariable("userId") int userId) {
		return ResponseEntity.ok().body(service.findById(userId));
	}
		
	@GetMapping("api/library_users/search")
	public ResponseEntity<LibraryUserDto> findByEmail(@RequestParam(required = true, name = "email") String email) {
		return ResponseEntity.ok().body(service.findByEmail(email));
	}
	
	@GetMapping("api/library_users")
	public ResponseEntity<List<LibraryUserDto>> findAll() {
		return ResponseEntity.ok().body(service.findAll());
	}
	
	@PostMapping("api/library_users")
	public ResponseEntity<LibraryUserDto> create(@Validated(OnCreate.class) @RequestBody LibraryUserDto libraryUser) {
		if(libraryUser == null || libraryUser.getUserId() != 0) {
			throw new IllegalArgumentException();
		}
		return ResponseEntity.ok(service.create(libraryUser));
	}
	
	@PutMapping("api/library_users")
	public ResponseEntity<LibraryUserDto> update(@Validated(OnUpdate.class) @RequestBody LibraryUserDto libraryUser) {
		if(libraryUser == null || libraryUser.getUserId() == 0) {
			throw new IllegalArgumentException();
		}
		return ResponseEntity.ok(service.update(libraryUser));
	}
	
	@DeleteMapping("api/library_users/{userId}")
	public ResponseEntity<?> deleteById(@PathVariable("userId") int userId) {
		boolean isRemoved = service.delete(userId);
		if(!isRemoved) {
			throw new NoSuchElementException("No library user with id: " + userId + " was found in the database.");
		}
		return new ResponseEntity<>("Library user with id: " + userId + " was removed from the database.", HttpStatus.OK);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handelValidationErrorOnLibraryUserEmail(MethodArgumentNotValidException e){
		Map<String, Object> errors = new HashMap<>();
		errors.put("code", 400);
		errors.put("status", HttpStatus.BAD_REQUEST);
		e.getBindingResult().getAllErrors().forEach(error ->{
			String fieldName = ((FieldError)error).getField();
			String message = error.getDefaultMessage();
			errors.put(fieldName, message);
		});
		return ResponseEntity.badRequest().body(errors);
	}
}
