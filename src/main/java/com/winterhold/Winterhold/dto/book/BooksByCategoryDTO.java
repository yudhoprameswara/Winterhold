package com.winterhold.Winterhold.dto.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BooksByCategoryDTO {
    private String code;
    private String title;
    private String authorName;
    private Boolean isBorrowed;
    private LocalDate releaseDate;
    private Long totalPage;
    private String summary;

}
