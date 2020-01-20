package se.lexicon.anton.demo.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import se.lexicon.anton.demo.validation.UniqueEmail;

public class LibraryUserDto {

	@PositiveOrZero(message = "Id can't be negative")
	private int userId;
	private LocalDate regDate;
	private String name;
	
	@NotEmpty(message = "This field is mandatory")
	@Size(max = 50)
	@Email(message = "Email must be a proper structure"
	, regexp = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$")
	@UniqueEmail
	private String email;
	
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public LocalDate getRegDate() {
		return regDate;
	}
	
	public void setRegDate(LocalDate regDate) {
		this.regDate = regDate;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
}
