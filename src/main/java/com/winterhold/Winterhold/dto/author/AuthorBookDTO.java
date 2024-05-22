package com.winterhold.Winterhold.dto.author;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorBookDTO {

    public String title;
    public String category;
    public Boolean borrowed;
    public LocalDate releaseDate;
    public Long totalPage;
}
