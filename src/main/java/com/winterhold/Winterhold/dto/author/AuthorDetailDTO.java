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
public class AuthorDetailDTO {
    public String name;
    public LocalDate birthDate;
    public String deceasedDate;
    public String education;
    public String summary;
}
