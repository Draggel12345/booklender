package se.lexicon.anton.demo.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import se.lexicon.anton.demo.dto.LoanDto;
import se.lexicon.anton.demo.service.LoanService;

@RestController
public class LoanController {

	private LoanService service;

	@Autowired
	public LoanController(LoanService service) {
		this.service = service;
	}
	
	@GetMapping("api/loans/{loanId}")
	public ResponseEntity<LoanDto> findById(@PathVariable("loanId") long loanId) {
		 return ResponseEntity.ok(service.findById(loanId));
	}
	
	@GetMapping("api/loans/search")
	public ResponseEntity<List<LoanDto>> find(
			@RequestParam(defaultValue = "all", name = "type") String type,
			@RequestParam(required = false, name="value") String value) {
		if(type.equalsIgnoreCase("all") && value == null) {
			return ResponseEntity.ok(service.findAll());
		} else if(type.equalsIgnoreCase("terminated")) {
			return ResponseEntity.ok(service.findByTerminated(Boolean.valueOf(value)));
		} else if(type.equalsIgnoreCase("loan")) {
			return ResponseEntity.ok(service.findByLoanTakerId(Integer.parseInt(value)));
		} else if(type.equalsIgnoreCase("book")) {
			return ResponseEntity.ok(service.findByBookId(Integer.parseInt(value)));
		} else {
			throw new NoSuchElementException(type + "is not found in the database.");
		}
	}
	
	@PostMapping("api/loans")
	public ResponseEntity<LoanDto> create(@RequestBody LoanDto loan) {
		if(loan == null || loan.getLoanId() != 0) {
			throw new IllegalArgumentException();
		}
		
		return ResponseEntity.ok(service.create(loan));
	}
	
	@PutMapping("api/loans")
	public ResponseEntity<LoanDto> update(@RequestBody LoanDto loan) {
		if(loan == null || loan.getLoanId() == 0) {
			throw new IllegalArgumentException();
		}
		return ResponseEntity.ok(service.update(loan));
	}
	
	@PostMapping("api/loans/{loanId}")
	public ResponseEntity<?> terminate(@PathVariable("loanId") long loanId) {
		boolean isTerminated = service.terminate(loanId);
		if(!isTerminated) {
			throw new NoSuchElementException("No loan with id: " + loanId + " was found in the database.");
		}
		return new ResponseEntity<>("Loan with id: " + loanId + " was terminated from the database.", HttpStatus.OK);
	}
	
	@DeleteMapping("api/loans/{loanId}")
	public ResponseEntity<?> deleteById(@PathVariable("loanId") long loanId) {
		boolean isRemoved = service.delete(loanId);
		if(!isRemoved) {
			throw new NoSuchElementException("No loan with id: " + loanId + " was found in the database.");
		}
		return new ResponseEntity<>("Loan with id: " + loanId + " was removed from the database.", HttpStatus.OK);
	}
	
}
