package com.winterhold.Winterhold.service;

import com.winterhold.Winterhold.dto.author.*;
import com.winterhold.Winterhold.dto.book.BooksByCategoryDTO;
import com.winterhold.Winterhold.entity.Author;
import com.winterhold.Winterhold.repository.AuthorRepository;
import com.winterhold.Winterhold.repository.BookRepository;

import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    public Page<AuthorRowDTO> getAuthorRows(String fullName, Integer page){
        var pageable = PageRequest.of(page - 1, 10, Sort.by("id"));
        var rows = authorRepository.getRow(fullName,pageable);
        return rows;
    }
    public AuthorDetailDTO getAuthorDetail(Long id){
        var entity = authorRepository.findById(id).get();
        String fullName = entity.getTitle()+" "+entity.getFirstName()+" "+entity.getLastName();
        String deceasedStatus;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy");

        if(entity.getDeceasedDate() == null){
            deceasedStatus ="-";
        }
        else {
            deceasedStatus= entity.getDeceasedDate().format(formatter);
        }
        var dto = new AuthorDetailDTO(
                fullName,
                entity.getBirthDate(),
                deceasedStatus,
                entity.getEducation(),
                entity.getSummary()
        );
        return dto;
    }
    public List<AuthorBookDTO> getAuthorBooks(Long id){
        var rows = bookRepository.getAuthorBook(id);
        return rows;
    }



    public ResponseUpsertAuthorDTO save(UpsertAuthorDTO dto){
        var entity = new Author();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setBirthDate(dto.getBirthDate());
        entity.setDeceasedDate(dto.getDeceasedDate());
        entity.setEducation(dto.getEducation());
        entity.setSummary(dto.getSummary());
        var responeseEntity =authorRepository.save(entity);
        var responseDTO = new ResponseUpsertAuthorDTO(
                responeseEntity.getId(),
                responeseEntity.getTitle(),
                responeseEntity.getFirstName(),
                responeseEntity.getLastName(),
                responeseEntity.getBirthDate(),
                responeseEntity.getDeceasedDate(),
                responeseEntity.getEducation(),
                responeseEntity.getSummary()
        );
        return responseDTO;
    }
    public void delete(Long id){
        authorRepository.deleteById(id);
    }

    public UpsertAuthorDTO findOne(Long id){
       var entity = authorRepository.findById(id).get();
       var dto = new UpsertAuthorDTO(
        entity.getId(),
        entity.getTitle(),
        entity.getFirstName(),
        entity.getLastName(),
        entity.getBirthDate(),
        entity.getDeceasedDate(),
        entity.getEducation(),
        entity.getSummary()
       );
       return dto;
    }

    public Long countByBooks(Long authorId){
        return bookRepository.countByAuthor(authorId);
    }
}
