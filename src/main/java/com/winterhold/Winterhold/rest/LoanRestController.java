package com.winterhold.Winterhold.rest;

import com.winterhold.Winterhold.dto.loan.UpsertLoanDTO;
import com.winterhold.Winterhold.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loan")
public class LoanRestController {

    @Autowired
    private LoanService service;

    @GetMapping
    public ResponseEntity<Object> get(
            @RequestParam(defaultValue = "") String customerName,
            @RequestParam(defaultValue = "") String bookTitle,
            @RequestParam(required = false) Boolean due,
            @RequestParam(defaultValue = "1") Integer page) {
        var rows = service.getRows(customerName, bookTitle,due,page);
        return ResponseEntity.status(HttpStatus.OK).body(rows);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable Long id){
        var dto = service.findOne(id);
        return ResponseEntity.status(200).body(dto);
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody UpsertLoanDTO dto, BindingResult bindingResult) {
        if(!bindingResult.hasErrors()){
            var response = service.save(dto);
            return ResponseEntity.status(201).body(response);
        }
        return ResponseEntity.status(422).body("error");
    }
//    getErrors(bindingResult.getAllErrors())

    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody UpsertLoanDTO dto, BindingResult bindingResult) {
        if(!bindingResult.hasErrors()){
            var response = service.save(dto);
            return ResponseEntity.status(201).body(response);
        }
        return ResponseEntity.status(422).body("error");
    }
}
