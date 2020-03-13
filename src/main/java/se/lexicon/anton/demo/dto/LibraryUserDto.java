package se.lexicon.anton.demo.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import se.lexicon.anton.demo.validation.OnCreate;
import se.lexicon.anton.demo.validation.OnUpdate;
import se.lexicon.anton.demo.validation.UniqueEmail;

public class LibraryUserDto {

	private int userId;
	private LocalDate regDate;
	private String name;

	@NotEmpty(message = "This field is mandatory", groups = {OnCreate.class, OnUpdate.class})
	@Email(message = "Email must be a proper structure",
	regexp = "^[aA-zZ0-9._%+-]+@[aA-zZ0-9.-]+\\.[aA-zZ]{2,6}$", groups = {OnCreate.class, OnUpdate.class })
	@UniqueEmail(groups = { OnCreate.class })
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
