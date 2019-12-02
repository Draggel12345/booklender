package se.lexicon.anton.demo.service;

import java.util.List;
import java.util.Optional;

import se.lexicon.anton.demo.dto.LoanDto;

public interface LoanService {

	Optional<LoanDto> findByLoanId(long loanId);
	List<LoanDto> findByLoanTakerId(int userId);
	List<LoanDto> findByBookId(int bookId);
	List<LoanDto> findByTerminated(boolean isTerminated);
	List<LoanDto> findByAll();
	LoanDto create(LoanDto dto);
	LoanDto update(LoanDto dto);
	Boolean delete(long loanId);
}
