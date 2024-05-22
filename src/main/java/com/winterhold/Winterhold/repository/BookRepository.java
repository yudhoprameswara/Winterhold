package com.winterhold.Winterhold.repository;

import com.winterhold.Winterhold.dto.author.AuthorBookDTO;
import com.winterhold.Winterhold.dto.book.BookInfoDTO;
import com.winterhold.Winterhold.dto.book.BooksByCategoryDTO;
import com.winterhold.Winterhold.dto.utility.DropdownDTO;
import com.winterhold.Winterhold.entity.Author;
import com.winterhold.Winterhold.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,String> {

    @Query("""
            SELECT new com.winterhold.Winterhold.dto.author.AuthorBookDTO(
            bo.title, bo.categoryName, bo.isBorrowed, bo.releaseDate, bo.totalPage
            )
            FROM Book AS bo
            WHERE bo.authorId = :id
            """)
    public List<AuthorBookDTO> getAuthorBook(@Param("id") Long id);

    @Query("""
            SELECT COUNT(bo.authorId)
            FROM Book AS bo
            WHERE bo.authorId = :authorId
            """)
    public Long countByAuthor(@Param("authorId") Long authorId);

    @Query("""
            SELECT COUNT(bo.categoryName)
            FROM Book AS bo
            WHERE bo.categoryName = :category
            """)
    public Long countByCategory(@Param("category") String category);

    @Query("""
            SELECT new com.winterhold.Winterhold.dto.book.BooksByCategoryDTO(
            bo.code,
            bo.title,
            CONCAT(aut.title,' ',aut.firstName,' ',aut.lastName),
            bo.isBorrowed,
            bo.releaseDate,
            bo.totalPage,
            bo.summary
            )
            FROM Book AS bo
            JOIN Author AS aut
            ON bo.authorId = aut.id
            WHERE bo.categoryName = :categoryName
            AND CONCAT(aut.title,' ',aut.firstName,' ',aut.lastName) LIKE %:authorName%
            AND bo.title LIKE %:bookTitle%
            AND (:isBorrowed IS NULL OR bo.isBorrowed = :isBorrowed)
            """)
    public Page<BooksByCategoryDTO> getBooksByCategory(@Param("categoryName")String categoryName,
                                                       @Param("bookTitle")String bookTitle,
                                                       @Param("authorName")String authorName,
                                                       @Param("isBorrowed")Boolean isBorrowed,
                                                       Pageable pageable);

    @Query("""
            SELECT new com.winterhold.Winterhold.dto.utility.DropdownDTO(
            bo.code,bo.title
            )
            FROM Book AS bo
            WHERE bo.isBorrowed = false
            """)
    public List<DropdownDTO> booksDropdown();

    @Query("""
            SELECT new com.winterhold.Winterhold.dto.book.BookInfoDTO(
            bo.code, bo.title, bo.categoryName,
            CONCAT(aut.title,' ',aut.firstName,' ',aut.lastName),
            cat.floor, cat.isle, cat.bay
            )
            FROM Book AS bo
            JOIN Author AS aut ON bo.authorId = aut.id
            JOIN Category AS cat ON bo.categoryName = cat.name
            WHERE bo.code = :code
            """)
    public BookInfoDTO getBookInfo(@Param("code")String code);
}
