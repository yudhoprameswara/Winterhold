package com.winterhold.Winterhold.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpsertBookDTO {

    @NotBlank(message = "tidak boleh kosong")
    @Size(max = 20, message = "tidak boleh lebih dari 20 karakter")
    private String code;

    @NotBlank(message = "tidak boleh kosong")
    @Size(max = 100, message = "tidak boleh lebih dari 100 karakter")
    private String title;

    @NotBlank(message = "tidak boleh kosong")
    private String categoryName;

    private Boolean isBorrowed;

    @NotNull(message = "tidak boleh kosong")
    private Long authorId;
    private LocalDate releaseDate;
    private Long totalPage;
    private String summary;
}
