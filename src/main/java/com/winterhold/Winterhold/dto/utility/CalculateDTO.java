package com.winterhold.Winterhold.dto.utility;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CalculateDTO {
    private Long angkaPertama;
    private Long angkaKedua;
    private Long angkaKetiga;
    private Long hasilTambah;
    private Long hasilKali;
}
