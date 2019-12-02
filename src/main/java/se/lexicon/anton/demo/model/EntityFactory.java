package se.lexicon.anton.demo.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public abstract class EntityFactory {

	protected LibraryUser creatLibraryUser(int userId, LocalDate regDate, String name, String email) {
		return new LibraryUser(userId, regDate, name, email);
	}
	
	protected Book creatBook(int bookId, String title, boolean available, boolean reserved, int maxLoanDays, BigDecimal finePerDay, String description) {
		return new Book(bookId, title, available, reserved, maxLoanDays, finePerDay, description);
	}
	
	protected Loan creatLoan(long loanId, LibraryUser loanTaker, Book book, LocalDate loanDate, boolean terminated) {
		return new Loan(loanId, loanTaker, book, loanDate, terminated);
	}
	
}
