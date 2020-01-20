package se.lexicon.anton.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.lexicon.anton.demo.converters.EntityDtoConverter;
import se.lexicon.anton.demo.data.BookRepo;
import se.lexicon.anton.demo.data.LibraryUserRepo;
import se.lexicon.anton.demo.data.LoanRepo;
import se.lexicon.anton.demo.dto.LoanDto;
import se.lexicon.anton.demo.model.Loan;

@Service
@Transactional
public class LoanServiceImpl implements LoanService {

	private LoanRepo repo;
	private EntityDtoConverter converter;
	private LibraryUserRepo userRepo;
	private BookRepo bookRepo;

	@Autowired
	public LoanServiceImpl(LoanRepo repo, EntityDtoConverter converter, LibraryUserRepo userRepo, BookRepo bookRepo) {
		this.repo = repo;
		this.converter = converter;
		this.userRepo = userRepo;
		this.bookRepo = bookRepo;
	}

	@Override
	public LoanDto findById(long loanId) {
		Optional<Loan> optional = repo.findById(loanId);
		Loan loan = optional.get();
		return converter.loanToDto(loan);
	}

	@Override
	public List<LoanDto> findByLoanTakerId(int userId) throws NoSuchElementException {
		List<Loan> users = repo.findByLoanTakerUserId(userId);
		List<LoanDto> dtos = new ArrayList<>();
		for(Loan loan : users) {
			dtos.add(converter.loanToDto(loan));
			return dtos;
		}
		throw new NoSuchElementException(" Couldnt find loan taker by id - " + userId);
	}

	@Override
	public List<LoanDto> findByBookId(int bookId) throws NoSuchElementException {
		List<Loan> books = repo.findByBookBookId(bookId);
		List<LoanDto> dtos = new ArrayList<>();
		if(books.isEmpty()) {
			throw new NoSuchElementException(" Couldnt find book by id - " + bookId);
		}
		books.stream().forEach(b -> dtos.add(converter.loanToDto(b)));
		return dtos;
	}

	@Override
	public List<LoanDto> findByTerminated(boolean isTerminated) throws NoSuchElementException {
		List<Loan> terminatedBooks = repo.findByTerminated(isTerminated);
		List<LoanDto> dtos = new ArrayList<>(); 
		if(terminatedBooks.isEmpty()) {
			throw new NoSuchElementException("There are no terminated loans in the database");
		}
		terminatedBooks.stream().forEach(t -> dtos.add(converter.loanToDto(t)));
		return dtos; 
	}

	@Override
	public List<LoanDto> findAll() {
		List<Loan> loans = (List<Loan>)repo.findAll();
		if(loans.isEmpty()) {
			throw new NoSuchElementException("There are no loans in the database.");
		} 
		return converter.loansToDtos(loans); 
	}

	@Override
	public LoanDto create(LoanDto dto) {
		Loan loan = converter.dtoToLoan(dto);
		
		loan.setLoanTaker(userRepo.findById(loan.getLoanTaker().getUserId()).get());
		loan.setBook(bookRepo.findById(loan.getBook().getBookId()).get());
		
		loan = repo.save(loan);
		return converter.loanToDto(loan);
	}

	@Override
	public LoanDto update(LoanDto dto) {
		Loan old = repo.findById(dto.getLoanId()).get();
		
		old.setBook(converter.dtoToBook(dto.getBook()));
		old.setLoanTaker(converter.dtoToLibraryUser(dto.getLoanTaker()));
		
		repo.save(old);
		return dto;
	}
	
	@Override
	public Boolean terminate(long loanId) {
		Optional<Loan> toTerminate = repo.findById(loanId);
		if(toTerminate.isPresent()) {
			Loan old = toTerminate.get();
			old.returnBook();
			return true;
		}
		return false;
	}
	
	@Override
	public Boolean delete(long loanId) {
		Optional<Loan> toRemove = repo.findById(loanId);
		if(toRemove.isPresent()) {
			Loan old = toRemove.get();
			old.returnBook();
			repo.delete(old);
			return true;
		}
		return false;
	}

}
