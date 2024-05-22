package com.winterhold.Winterhold.repository;

import com.winterhold.Winterhold.dto.author.AuthorRowDTO;
import com.winterhold.Winterhold.dto.book.BookCategoryRowDTO;
import com.winterhold.Winterhold.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category,String> {

    @Query("""
            SELECT new com.winterhold.Winterhold.dto.book.BookCategoryRowDTO(
            cat.name, cat.floor, cat.isle, cat.bay, COUNT(bo.categoryName)
            )
            FROM Category AS cat
            LEFT JOIN cat.books AS bo
            WHERE cat.name LIKE %:name%
            GROUP BY cat.name, cat.floor, cat.isle ,cat.bay
            """)
    public Page<BookCategoryRowDTO> getRow(@Param("name") String name, Pageable pageable);
}
