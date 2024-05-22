package com.winterhold.Winterhold.dto.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ResponseUpsertCategoryDTO {
    public String categoryName;
    public Long floor;
    public String isle;
    public String bay;
}

