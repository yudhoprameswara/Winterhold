package com.winterhold.Winterhold.dto.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookInfoDTO {
    private String code;
    private String title;
    private String categoryName;
    private String author;
    public Long floor;
    public String isle;
    public String bay;
}
