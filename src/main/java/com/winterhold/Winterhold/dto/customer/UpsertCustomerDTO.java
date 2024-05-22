package com.winterhold.Winterhold.dto.customer;

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
public class UpsertCustomerDTO {
    @NotBlank
    private String membershipNumber;

    @NotBlank
    private String firstName;

    private String lastName;

    @NotNull
    private LocalDate birthDate;

    @NotBlank
    private String gender;

    private String phone;

    private String address;

    private LocalDate membershipExpireDate;
}
