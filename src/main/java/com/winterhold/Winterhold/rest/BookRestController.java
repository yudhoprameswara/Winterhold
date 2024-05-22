package com.winterhold.Winterhold.rest;

import com.winterhold.Winterhold.dto.author.UpsertAuthorDTO;
import com.winterhold.Winterhold.dto.book.UpsertCategoryDTO;
import com.winterhold.Winterhold.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book")
public class BookRestController {

    @Autowired
    private BookService service;

    @GetMapping
    public ResponseEntity<Object> get(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "1") Integer page){
        var rows = service.getCategoryRows(name, page);
        return ResponseEntity.status(HttpStatus.OK).body(rows);

    }

    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody UpsertCategoryDTO dto, BindingResult bindingResult) {
        if(!bindingResult.hasErrors()){
            var response = service.save(dto);
            return ResponseEntity.status(201).body(response);
        }
        return ResponseEntity.status(422).body("error");
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody UpsertCategoryDTO dto, BindingResult bindingResult) {
        if(!bindingResult.hasErrors()){
            var response = service.save(dto);
            return ResponseEntity.status(201).body(response);
        }
        return ResponseEntity.status(422).body("error");
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<Object> get(@PathVariable String category){
        var dto = service.findOneCategory(category);
        return ResponseEntity.status(200).body(dto);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Object> getBook(@PathVariable String code){
        var dto = service.findOneWithName(code);
        return ResponseEntity.status(200).body(dto);
    }

    @GetMapping("/detail/{categoryName}")
    public ResponseEntity<Object> getBooksByCategory(@PathVariable String categoryName,
                                                     @RequestParam(defaultValue = "") String bookTitle,
                                                     @RequestParam(defaultValue = "") String authorName,
                                                     @RequestParam(required = false) Boolean isBorrowed,
                                                     @RequestParam(defaultValue = "1") Integer page){
        var rows = service.getBooksByCategory(categoryName,bookTitle,authorName,isBorrowed,page);
        return ResponseEntity.status(HttpStatus.OK).body(rows);
    }

    @GetMapping("/summary/{code}")
    public ResponseEntity<Object> getByCode(@PathVariable String code){
        var dto = service.getSummary(code);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @DeleteMapping("/delete/category/{category}")
    public ResponseEntity delete(@PathVariable String category){
        var dependencies = service.countByCategory(category);
        if(dependencies == 0){
            service.delete(category);
            return ResponseEntity.status(200).body(category);
        }
        return ResponseEntity.status(409).body(dependencies);
    }
}
