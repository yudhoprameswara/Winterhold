package com.winterhold.Winterhold.service;

import com.winterhold.Winterhold.dto.author.AuthorRowDTO;
import com.winterhold.Winterhold.dto.book.*;
import com.winterhold.Winterhold.dto.utility.DropdownDTO;
import com.winterhold.Winterhold.entity.Book;
import com.winterhold.Winterhold.entity.Category;
import com.winterhold.Winterhold.repository.AuthorRepository;
import com.winterhold.Winterhold.repository.BookRepository;
import com.winterhold.Winterhold.repository.CategoryRepository;
import com.winterhold.Winterhold.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private LoanRepository loanRepository;

    public Page<BookCategoryRowDTO> getCategoryRows(String name, Integer page){
        var pageable = PageRequest.of(page - 1, 10, Sort.by("id"));
        var rows = categoryRepository.getRow(name,pageable);
        return rows;
    }

    public ResponseUpsertCategoryDTO save(UpsertCategoryDTO dto){
        var entity = new Category();
        entity.setName(dto.getCategoryName());
        entity.setFloor(dto.getFloor());
        entity.setIsle(dto.getIsle());
        entity.setBay(dto.getBay());
        var responseEntity = categoryRepository.save(entity);
        var responseDTO = new ResponseUpsertCategoryDTO(
                responseEntity.getName(),
                responseEntity.getFloor(),
                responseEntity.getIsle(),
                responseEntity.getBay()
        );
        return responseDTO;
    }

    public UpsertCategoryDTO findOneCategory(String category){
        var entity = categoryRepository.findById(category).get();
        var dto = new UpsertCategoryDTO(
                entity.getName(),
                entity.getFloor(),
                entity.getIsle(),
                entity.getBay()
        );
        return dto;
    }
    public UpsertBookDTO findOneByBook(String code){
        var entity= bookRepository.findById(code).get();
        var dto = new UpsertBookDTO(
                entity.getCode(),
                entity.getTitle(),
                entity.getCategoryName(),
                entity.getIsBorrowed(),
                entity.getAuthorId(),
                entity.getReleaseDate(),
                entity.getTotalPage(),
                entity.getSummary()
        );
        return dto;
    }

    public BookInfoDTO findOneWithName(String code) {
        var dto = bookRepository.getBookInfo(code);
//        var dto = new BookInfoDTO(
//                entity.getCode(),
//                entity.getTitle(),
//                entity.getCategoryName(),
//                entity.getAuthor(),
//                entity.getFloor(),
//                entity.getIsle(),
//                entity.getBay()
//        );
        return dto;
    }

    public Page<BooksByCategoryDTO> getBooksByCategory(String categoryName, String bookTitle, String authorName,Boolean isBorrowed,Integer page){
        var pageable = PageRequest.of(page - 1, 10, Sort.by("id"));
        var rows = bookRepository.getBooksByCategory(categoryName,bookTitle,authorName,isBorrowed,pageable);
        return rows;
    }

    public Long countByCategory(String categoryName){
        return bookRepository.countByCategory(categoryName);
    }

    public Long countByLoan(String code){
        return loanRepository.countByBook(code);
    }
    public void delete(String categoryName){
        categoryRepository.deleteById(categoryName);
    }
    public void deleteBook(String code){
        bookRepository.deleteById(code);
    }
    public BookSummaryDTO getSummary(String code){
        var entity = bookRepository.findById(code).get();
        var dto = new BookSummaryDTO(
                entity.getSummary()
        );
        return dto;
    }

    public List<DropdownDTO> getAuthorDropdown(){
        return authorRepository.getDropdown();
    }

    public void save(UpsertBookDTO dto){
        var entity = new Book();
        entity.setCode(dto.getCode());
        entity.setTitle(dto.getTitle());
        entity.setCategoryName(dto.getCategoryName());
        entity.setAuthorId(dto.getAuthorId());
        entity.setReleaseDate(dto.getReleaseDate());
        entity.setTotalPage(dto.getTotalPage());
        entity.setSummary(dto.getSummary());

        if(dto.getIsBorrowed() == null){
            entity.setIsBorrowed(true);
        }
        else {
        entity.setIsBorrowed(dto.getIsBorrowed());
        }

        bookRepository.save(entity);
    }


}
