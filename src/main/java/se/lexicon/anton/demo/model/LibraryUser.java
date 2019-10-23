package se.lexicon.anton.demo.model;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LibraryUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	
	private LocalDate regDate;
	private String name;
	
	@Column(unique = true, length = 50)
	private String email;
	
	public LibraryUser(int userId, LocalDate regDate, String name, String email) {
		this(regDate, name, email);
		this.userId = userId;
	}

	public LibraryUser(LocalDate regDate, String name, String email) {
		this.regDate = regDate;
		setName(name);
		setEmail(email);
	}
	
	public LibraryUser() {}

	public LocalDate getRegDate() {
		return regDate;
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
	
	public int getUserId() {
		return userId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, name, regDate, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LibraryUser other = (LibraryUser) obj;
		return Objects.equals(email, other.email) && Objects.equals(name, other.name)
				&& Objects.equals(regDate, other.regDate) && userId == other.userId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LibraryUser [userId=");
		builder.append(userId);
		builder.append(", regDate=");
		builder.append(regDate);
		builder.append(", name=");
		builder.append(name);
		builder.append(", email=");
		builder.append(email);
		builder.append("]");
		return builder.toString();
	}
}
