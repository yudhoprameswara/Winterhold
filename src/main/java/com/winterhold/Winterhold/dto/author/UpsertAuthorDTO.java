package com.winterhold.Winterhold.dto.author;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpsertAuthorDTO {
    private Long id;

    @NotBlank(message = "x")
    @Size(max = 10 , message = "tidak boleh lebih dari 10 karakter")
    private String title;

    @NotBlank(message = "x")
    @Size(max = 50 , message = "tidak boleh lebih dari 50 karakter")
    private String firstName;

    private String lastName;

    @NotNull(message = "x")
    private LocalDate birthDate;
    private LocalDate deceasedDate;
    private String education;
    private String summary;
}
