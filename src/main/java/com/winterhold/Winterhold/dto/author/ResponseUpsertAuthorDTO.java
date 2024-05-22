package com.winterhold.Winterhold.dto.author;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseUpsertAuthorDTO {
    private Long id;
    private String title;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private LocalDate deceasedDate;
    private String education;
    private String summary;
}
