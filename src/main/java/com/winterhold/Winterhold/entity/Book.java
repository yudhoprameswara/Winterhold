package com.winterhold.Winterhold.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Book")
@Entity
public class Book {
    @Id
    @Column(name = "Code")
    private String code;

    @Column(name = "Title")
    private String title;

    @Column(name = "CategoryName")
    private String categoryName;

    @ManyToOne
    @JoinColumn(name="CategoryName",insertable = false,updatable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name="AuthorId",insertable = false,updatable = false)
    private Author author;

    @Column(name = "AuthorId")
    private Long authorId;

    @Column(name = "IsBorrowed")
    private Boolean isBorrowed;

    @Column(name = "Summary")
    private String summary;

    @Column(name = "ReleaseDate")
    private LocalDate releaseDate;

    @Column(name = "TotalPage")
    private Long totalPage;


}
