package se.lexicon.anton.demo.dto;

import java.time.LocalDate;

public class LoanDto {

	private long loanId;
	private LibraryUserDto loanTaker;
	private BookDto book;
	private LocalDate loanDate;
	private boolean terminated;
	
	public LoanDto(long loanId, LibraryUserDto loanTaker, BookDto book, LocalDate loanDate) {
		setLoanId(loanId);
		setLoanTaker(loanTaker);
		setBook(book);
		setLoanDate(loanDate);
	}

	public LoanDto() {}
	
	public long getLoanId() {
		return loanId;
	}
	
	public void setLoanId(long loanId) {
		this.loanId = loanId;
	}
	
	public LibraryUserDto getLoanTaker() {
		return loanTaker;
	}
	
	public void setLoanTaker(LibraryUserDto loanTaker) {
		this.loanTaker = loanTaker;
	}
	
	public BookDto getBook() {
		return book;
	}
	
	public void setBook(BookDto book) {
		this.book = book;
	}
	
	public LocalDate getLoanDate() {
		return loanDate;
	}
	
	public void setLoanDate(LocalDate loanDate) {
		this.loanDate = loanDate;
	}
	
	public boolean isTerminated() {
		return terminated;
	}
	
	public void setTerminated(boolean terminated) {
		this.terminated = terminated;
	}
}
