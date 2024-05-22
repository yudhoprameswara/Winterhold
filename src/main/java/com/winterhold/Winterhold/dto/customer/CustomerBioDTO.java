package com.winterhold.Winterhold.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerBioDTO {
    private String membershipNumber;
    private String name;
    private LocalDate birthDate;
    private String gender;
    private String phone;
    private String address;
}
