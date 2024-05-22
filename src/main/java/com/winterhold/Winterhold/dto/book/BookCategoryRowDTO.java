package com.winterhold.Winterhold.dto.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookCategoryRowDTO {
    private String category;
    private Long floor;
    private String isle;
    private String bay;
    private Long totalBooks;
}
