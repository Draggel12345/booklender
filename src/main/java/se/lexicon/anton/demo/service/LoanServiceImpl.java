package se.lexicon.anton.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.lexicon.anton.demo.converters.EntityDtoConverter;
import se.lexicon.anton.demo.data.LoanRepo;
import se.lexicon.anton.demo.dto.LoanDto;
import se.lexicon.anton.demo.model.Loan;

@Service
@Transactional
public class LoanServiceImpl implements LoanService {

	private LoanRepo repo;
	private EntityDtoConverter converter;

	@Autowired
	public LoanServiceImpl(LoanRepo repo, EntityDtoConverter converter) {
		this.repo = repo;
		this.converter = converter;
	}

	@Override
	public Optional<LoanDto> findByLoanId(long loanId) {
		return Optional.of(converter.loanToDto(repo.findByLoanId(loanId).get()));
	}

	@Override
	public List<LoanDto> findByLoanTakerId(int userId) throws IllegalArgumentException {
		List<Loan> users = repo.findByLoanTakerUserId(userId);
		List<LoanDto> dtos = new ArrayList<>();
		for(Loan loan : users) {
			dtos.add(converter.loanToDto(loan));
			return dtos;
		}
		throw new IllegalArgumentException(" Couldnt find loan taker by id:");
	}

	@Override
	public List<LoanDto> findByBookId(int bookId) throws IllegalArgumentException {
		List<Loan> books = repo.findByBookBookId(bookId);
		List<LoanDto> dtos = new ArrayList<>();
		for(Loan loan : books) {
			dtos.add(converter.loanToDto(loan));
			return dtos;
		}
		throw new IllegalArgumentException(" Couldnt find book by id: ");
	}

	@Override
	public List<LoanDto> findByTerminated(boolean isTerminated) throws IllegalArgumentException {
		List<Loan> terminatedBooks = repo.findByTerminated(isTerminated);
		List<LoanDto> dtos = new ArrayList<>();
		for(Loan loan : terminatedBooks) {
			dtos.add(converter.loanToDto(loan));
			return dtos;
		}
		
		throw new IllegalArgumentException( "Couldnt find terminated book: ");
	}

	@Override
	public List<LoanDto> findByAll() {
		return converter.loansToDtos((List<Loan>) repo.findAll());
	}

	@Override
	public LoanDto create(LoanDto dto) {
		Loan loan = converter.dtoToLoan(dto);
		repo.save(loan);
		return converter.loanToDto(loan);
	}

	@Override
	public LoanDto update(LoanDto dto) {
		Loan loan = converter.dtoToLoan(dto);
		Optional<Loan> old = repo.findById(loan.getLoanId());
		if(old.isPresent()) {
			
			Loan original = old.get();
			
			original.setLoanTaker(loan.getLoanTaker());
			original.setBook(loan.getBook());
			
			repo.save(original);
			return converter.loanToDto(original);
		}
		
		return converter.loanToDto(loan);
	}

	@Override
	public Boolean delete(long loanId) {
		Optional<Loan> toRemove = repo.findById(loanId);
		if(toRemove.isPresent()) {
			Loan old = toRemove.get();
			repo.delete(old);
			return true;
		}
		return false;
	}

}
