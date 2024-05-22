package com.winterhold.Winterhold.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CustomerRowDTO {
    private String membershipNumber;
    private String name;
    private LocalDate expireDate;
}
