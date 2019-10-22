package se.lexicon.anton.demo.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Book {

	private int bookId;
	private String title;
	private boolean available;
	private boolean reserved;
	private int maxLoanDays;
	private BigDecimal finePerDay;
	private String description;
	
	public Book(int bookId, String title, int maxLoanDays, BigDecimal finePerDay, String description) {
		this(title, maxLoanDays, finePerDay, description);
		this.bookId = bookId;
	}
	
	public Book(String title, boolean available, boolean reserved, int maxLoanDays, BigDecimal finePerDay, String description) {
		this.title = title;
		this.available = available;
		this.reserved = reserved;
		this.maxLoanDays = maxLoanDays;
		this.finePerDay = finePerDay;
		this.description = description;
	}

	public Book(String title, int maxLoanDays, BigDecimal finePerDay, String description) {
		setTitle(title);
		setMaxLoanDays(maxLoanDays);
		setFinePerDay(finePerDay);
		setDescription(description);
	}

	public Book() {}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public boolean isAvailable() {
		return available;
	}
	
	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	public boolean isReserved() {
		return reserved;
	}
	
	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}
	
	public int getMaxLoanDays() {
		return maxLoanDays;
	}
	
	public void setMaxLoanDays(int maxLoanDays) {
		this.maxLoanDays = maxLoanDays;
	}
	
	public BigDecimal getFinePerDay() {
		return finePerDay;
	}
	
	public void setFinePerDay(BigDecimal finePerDay) {
		this.finePerDay = finePerDay;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getBookId() {
		return bookId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(available, bookId, description, finePerDay, maxLoanDays, reserved, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		return available == other.available && bookId == other.bookId && Objects.equals(description, other.description)
				&& Objects.equals(finePerDay, other.finePerDay) && maxLoanDays == other.maxLoanDays
				&& reserved == other.reserved && Objects.equals(title, other.title);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Book [bookId=");
		builder.append(bookId);
		builder.append(", title=");
		builder.append(title);
		builder.append(", available=");
		builder.append(available);
		builder.append(", reserved=");
		builder.append(reserved);
		builder.append(", maxLoanDays=");
		builder.append(maxLoanDays);
		builder.append(", finePerDay=");
		builder.append(finePerDay);
		builder.append(", description=");
		builder.append(description);
		builder.append("]");
		return builder.toString();
	}
}
