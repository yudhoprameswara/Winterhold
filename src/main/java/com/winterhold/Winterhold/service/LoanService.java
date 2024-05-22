package com.winterhold.Winterhold.service;

import com.winterhold.Winterhold.dto.book.ResponseUpsertCategoryDTO;
import com.winterhold.Winterhold.dto.book.UpsertCategoryDTO;
import com.winterhold.Winterhold.dto.customer.CustomerRowDTO;
import com.winterhold.Winterhold.dto.loan.LoanRowDTO;
import com.winterhold.Winterhold.dto.loan.ResponseUpsertLoanDTO;
import com.winterhold.Winterhold.dto.loan.UpsertLoanDTO;
import com.winterhold.Winterhold.dto.utility.DropdownDTO;
import com.winterhold.Winterhold.entity.Category;
import com.winterhold.Winterhold.entity.Loan;
import com.winterhold.Winterhold.repository.BookRepository;
import com.winterhold.Winterhold.repository.CustomerRepository;
import com.winterhold.Winterhold.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BookRepository bookRepository;

    public Page<LoanRowDTO> getRows(String customerName, String bookTitle,Boolean due, Integer page){
        var pageable = PageRequest.of(page - 1, 10, Sort.by("id"));
        LocalDate date = null;
        if(due != null){
            date = LocalDate.now();
        }
        var rows = loanRepository.getRows(bookTitle,customerName,date,pageable);
        return rows;
    }

    public ResponseUpsertLoanDTO save(UpsertLoanDTO dto){
        var entity = new Loan();
        entity.setCustomerNumber(dto.getCustomerNumber());
        entity.setBookCode(dto.getBookCode());
        entity.setLoanDate(dto.getLoanDate());
        entity.setNote(dto.getNote());

        if(dto.getDueDate() == null){
            entity.setDueDate(LocalDate.now().plusDays(5));
        }
        else {
            entity.setDueDate(dto.getDueDate());
        }

        var responseEntity = loanRepository.save(entity);
        var responseDTO = new ResponseUpsertLoanDTO(
                responseEntity.getCustomerNumber(),
                responseEntity.getBookCode(),
                responseEntity.getLoanDate(),
                responseEntity.getNote()
        );
        return responseDTO;
    }

    public UpsertLoanDTO findOne(Long id){
        var entity = loanRepository.findById(id).get();
        var dto = new UpsertLoanDTO(
                entity.getCustomerNumber(),
                entity.getBookCode(),
                entity.getDueDate(),
                entity.getLoanDate(),
                entity.getNote()
        );
        return dto;
    }

    public void returnById(Long id){
        var entity = loanRepository.findById(id).get();
        if(entity.getReturnDate() == null){
            entity.setReturnDate(LocalDate.now());
        }
        loanRepository.save(entity);
    }

    public List<DropdownDTO> getCustomerDropdown(){
        var dropdown = customerRepository.customerDropdown();
        return dropdown;
    }

    public List<DropdownDTO> getBookDropdown(){
        var dropdown = bookRepository.booksDropdown();
        return dropdown;
    }
}
