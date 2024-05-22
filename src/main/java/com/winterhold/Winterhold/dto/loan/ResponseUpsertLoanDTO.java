package com.winterhold.Winterhold.dto.loan;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUpsertLoanDTO {
    @NotBlank
    private String customerNumber;

    @NotBlank
    private String bookCode;

    @NotNull
    private LocalDate loanDate;

    private String note;
}
