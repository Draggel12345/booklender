package se.lexicon.anton.demo.data;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import se.lexicon.anton.demo.model.Loan;

public interface LoanRepo extends CrudRepository<Loan, Long> {

	List<Loan> findByLoanTakerUserId(int userId);
	List<Loan> findByBookBookId(int bookId);
	List<Loan> findByTerminated(boolean terminated);
}
