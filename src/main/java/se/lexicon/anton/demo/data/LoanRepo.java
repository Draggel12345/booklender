package se.lexicon.anton.demo.data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import se.lexicon.anton.demo.model.Loan;

public interface LoanRepo extends CrudRepository<Loan, Long> {

	Optional<Loan> findByLoanId(Long loanId);
	List<Loan> findByLoanTakerUserId(int userId);
	List<Loan> findByBookBookId(int bookId);
	List<Loan> findByTerminated(boolean terminated);
}
