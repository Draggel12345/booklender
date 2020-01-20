package se.lexicon.anton.demo.service;

import java.util.List;
import se.lexicon.anton.demo.dto.LoanDto;

public interface LoanService {

	LoanDto findById(long loanId);
	List<LoanDto> findByLoanTakerId(int userId);
	List<LoanDto> findByBookId(int bookId);
	List<LoanDto> findByTerminated(boolean isTerminated);
	List<LoanDto> findAll();
	Boolean terminate(long loanId);
	LoanDto create(LoanDto dto);
	LoanDto update(LoanDto dto);
	Boolean delete(long loanId);
}
