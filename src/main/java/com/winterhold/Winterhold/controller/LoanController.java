package com.winterhold.Winterhold.controller;

import com.winterhold.Winterhold.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/loan")
public class LoanController {

    @Autowired
    private LoanService service;

    @GetMapping("/index")
    public String index( @RequestParam(defaultValue = "") String customerName,
                         @RequestParam(defaultValue = "") String bookTitle,
                         @RequestParam(required = false) Boolean due,
                         @RequestParam(defaultValue = "1") Integer page,
                         Model model){
        var rows = service.getRows(customerName,bookTitle,due,page);
        var customerDropdown = service.getCustomerDropdown();
        var bookDropdown = service.getBookDropdown();
        model.addAttribute("customerDropdown",customerDropdown);
        model.addAttribute("bookDropdown",bookDropdown);
        model.addAttribute("grid", rows);
        model.addAttribute("totalPages", rows.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("customerName", customerName);
        return "loan/loan-index";
    }

    @GetMapping("/return")
    public String returns(@RequestParam Long id ){
        service.returnById(id);
        return  "redirect:/loan/index";
    }

}
