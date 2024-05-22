package com.winterhold.Winterhold.rest;

import com.winterhold.Winterhold.dto.author.UpsertAuthorDTO;
import com.winterhold.Winterhold.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/author")
public class AuthorRestController {

    @Autowired
    private AuthorService service;

    @GetMapping
    public ResponseEntity<Object> get(
            @RequestParam(defaultValue = "") String fullName,
            @RequestParam(defaultValue = "1") Integer page){
        var rows = service.getAuthorRows(fullName, page);
        return ResponseEntity.status(HttpStatus.OK).body(rows);

    }

    @GetMapping("/book/{id}")
    public ResponseEntity<Object> get(@PathVariable Long id){
        var rows = service.getAuthorBooks(id);
        return ResponseEntity.status(HttpStatus.OK).body(rows);
    }

    @GetMapping("/countBook/{id}")
    public ResponseEntity<Object> getCountBook(@PathVariable Long id){
        var books = service.countByBooks(id);
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getDetails(@PathVariable Long id){
        var rows = service.getAuthorDetail(id);
        return ResponseEntity.status(HttpStatus.OK).body(rows);
    }



    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody UpsertAuthorDTO dto, BindingResult bindingResult) {
        if(!bindingResult.hasErrors()){
            var response = service.save(dto);
            return ResponseEntity.status(201).body(response);
        }
        return ResponseEntity.status(422).body("error gan");
    }

    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody  UpsertAuthorDTO dto,BindingResult bindingResult) {
        if(!bindingResult.hasErrors()) {
            var response = service.save(dto);
            return ResponseEntity.status(200).body(response);
        }
        return ResponseEntity.status(422).body("error gan");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) Long id){
//        var dependencies = service.countByBooks(id);
//        if(dependencies == 0){
            service.delete(id);
            return ResponseEntity.status(200).body(id);
//        }
//        return ResponseEntity.status(409).body(dependencies);
    }
}
