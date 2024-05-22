package com.winterhold.Winterhold.repository;

import com.winterhold.Winterhold.dto.loan.LoanRowDTO;
import com.winterhold.Winterhold.entity.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface LoanRepository extends JpaRepository<Loan,Long> {

    @Query(""" 
            SELECT new com.winterhold.Winterhold.dto.loan.LoanRowDTO(
            lo.id, bo.title, CONCAT(cus.firstName,' ',cus.lastName)
            ,lo.loanDate , lo.dueDate, lo.returnDate, lo.customerNumber, lo.bookCode
            )
            FROM Loan AS lo
            JOIN Book AS bo ON lo.bookCode = bo.code
            JOIN Customer AS cus ON lo.customerNumber = cus.membershipNumber
            WHERE bo.title LIKE %:bookTitle%
            AND CONCAT(cus.firstName,' ',cus.lastName) LIKE %:name%
            AND (:date IS NULL OR lo.dueDate > :date)
            
            """)
    public Page<LoanRowDTO> getRows(@Param("bookTitle") String bookTitle,
                                   @Param("name") String customerName,
                                   @Param("date") LocalDate date,
                                   Pageable pageable);

    @Query("""
            SELECT COUNT(lo.bookCode)
            FROM Loan AS lo
            WHERE lo.bookCode = :code
            """)
    public Long countByBook(@Param("code")String code);

    @Query("""
            SELECT COUNT(lo.customerNumber)
            FROM Loan AS lo
            WHERE lo.customerNumber = :customerNumber
            """)
    public Long countByCustomer(@Param("customerNumber")String customerNumber);
}
