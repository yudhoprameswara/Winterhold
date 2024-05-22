package com.winterhold.Winterhold.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Table(name="Loan")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="CustomerNumber",insertable = false,updatable = false)
    private Customer customer;

    @Column(name = "CustomerNumber")
    private String customerNumber;

    @ManyToOne
    @JoinColumn(name="bookCode",insertable = false,updatable = false)
    private Book book;

    @Column(name = "BookCode")
    private String bookCode;

    @Column(name = "LoanDate")
    private LocalDate loanDate;

    @Column(name = "DueDate")
    private LocalDate dueDate;

    @Column(name = "ReturnDate")
    private LocalDate returnDate;

    @Column(name = "Note")
    private String note;
}
