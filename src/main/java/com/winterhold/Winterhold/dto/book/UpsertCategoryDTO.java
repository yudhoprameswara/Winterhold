package com.winterhold.Winterhold.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpsertCategoryDTO {

    @NotBlank(message = "tidak boleh kosong")
    @Size(max = 100,message = "tidak boleh lebih dari 100 karakter")
    public String categoryName;

    @NotNull(message = "tidak boleh kosong")
    public Long floor;

    @Size(max = 10,message = "tidak boleh lebih dari 10 karakter")
    @NotBlank(message = "tidak boleh kosong")
    public String isle;

    @Size(max = 10,message = "tidak boleh lebih dari 10 karakter")
    @NotBlank(message = "tidak boleh kosong")
    public String bay;
}
