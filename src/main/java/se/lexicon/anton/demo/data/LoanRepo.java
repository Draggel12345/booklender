package se.lexicon.anton.demo.data;

import org.springframework.data.repository.CrudRepository;

import se.lexicon.anton.demo.model.Loan;

public interface LoanRepo extends CrudRepository<Loan, Long> {

}
