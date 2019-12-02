package se.lexicon.anton.demo.dto;

import java.time.LocalDate;

public class LibraryUserDto {

	private int userId;
	private LocalDate regDate;
	private String name;
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
