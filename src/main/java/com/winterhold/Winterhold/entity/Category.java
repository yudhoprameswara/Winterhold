package com.winterhold.Winterhold.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Table(name = "Category")
@Entity
public class Category {

    @Id
    @Column(name = "Name")
    private String name;

    @Column(name = "Floor")
    private Long floor;

    @Column(name = "Isle")
    private String isle;

    @Column(name = "Bay")
    private String bay;

    @OneToMany(mappedBy = "category")
    private List<Book> books;

}
