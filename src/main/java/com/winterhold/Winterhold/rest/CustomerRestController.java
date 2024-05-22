package com.winterhold.Winterhold.rest;

import com.winterhold.Winterhold.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CustomerRestController {

    @Autowired
    private CustomerService service;

    @GetMapping
    public ResponseEntity<Object> get(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String membershipNumber,
            @RequestParam(required = false)Boolean expired,
            @RequestParam(defaultValue = "1") Integer page){
        var rows = service.getRows(name,membershipNumber,expired,page);
        return ResponseEntity.status(HttpStatus.OK).body(rows);
    }

    @GetMapping("/bio/{membershipNumber}")
    public ResponseEntity<Object> get(@PathVariable String membershipNumber){
        var dto = service.getBio(membershipNumber);
        return ResponseEntity.status(200).body(dto);
    }

    @GetMapping("/{membershipNumber}")
    public ResponseEntity<Object> getById(@PathVariable String membershipNumber){
        var dto = service.findOne(membershipNumber);
        return ResponseEntity.status(200).body(dto);
    }

}
