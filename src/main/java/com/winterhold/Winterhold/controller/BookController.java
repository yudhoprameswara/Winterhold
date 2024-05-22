package com.winterhold.Winterhold.controller;

import com.winterhold.Winterhold.dto.author.UpsertAuthorDTO;
import com.winterhold.Winterhold.dto.book.UpsertBookDTO;
import com.winterhold.Winterhold.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService service;

    @GetMapping("/index")
    public String index(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "") String name, Model model){

        var rows = service.getCategoryRows(name, page);
        model.addAttribute("grid", rows);
        model.addAttribute("totalPages", rows.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("name", name);
        return "book/book-index";
    }

    @GetMapping("/detail")
    public String detail(@RequestParam(defaultValue = "1") Integer page,
                         @RequestParam String categoryName,
                         @RequestParam(defaultValue = "") String bookTitle,
                         @RequestParam(defaultValue = "") String authorName,
                         @RequestParam(required = false) Boolean isBorrowed,
                         Model model){
        var rows =service.getBooksByCategory(categoryName,bookTitle,authorName,isBorrowed,page);
        model.addAttribute("categoryName",categoryName);
        model.addAttribute("grid", rows);
        model.addAttribute("totalPages", rows.getTotalPages());
        model.addAttribute("currentPage", page);
        return "book/books-by-category";
    }

    @RequestMapping(value="category/delete",method = {RequestMethod.GET,RequestMethod.POST})
    public String delete(@RequestParam(required = true) String categoryName, Model model){
        var dependencies = service.countByCategory(categoryName);
        if(dependencies == 0){
            service.delete(categoryName);
            return "redirect:/book/index";
        }
        model.addAttribute("dependencies", dependencies);
        return "book/book-category-delete";
    }

    @RequestMapping(value="/delete",method = {RequestMethod.GET,RequestMethod.POST})
    public String deleteBook(@RequestParam(required = true) String code, Model model){
        var dependencies = service.countByLoan(code);
        if(dependencies == 0){
            service.deleteBook(code);
            return "redirect:/book/index";
        }
        model.addAttribute("dependencies", dependencies);
        return "book/book-delete";
    }



    @GetMapping("/upsertForm")
    public String upsertForm(@RequestParam(required = false) String code,
                             @RequestParam String categoryName,
                              Model model) {
        var dto = new UpsertBookDTO();
        if (code != null) {
            dto = service.findOneByBook(code);
        }
        model.addAttribute("categoryName",categoryName);
        model.addAttribute("dto", dto);
        model.addAttribute("authorDropdown", service.getAuthorDropdown());
        return "book/book-upsert-form";
        }

    @PostMapping("/upsert")
    public String upsert(@Valid @ModelAttribute("dto") UpsertBookDTO dto, BindingResult bindingResult){
        if(!bindingResult.hasErrors()){
            service.save(dto);
            return "redirect:/book/detail?categoryName="+dto.getCategoryName();
        }
        return  "book/book-form";
    }
}
