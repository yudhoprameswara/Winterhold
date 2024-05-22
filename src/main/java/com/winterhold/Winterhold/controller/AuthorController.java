package com.winterhold.Winterhold.controller;

import com.winterhold.Winterhold.dto.author.UpsertAuthorDTO;
import com.winterhold.Winterhold.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    private AuthorService service;

    @GetMapping("/index")
    public String index(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "") String fullName, Model model){

        var rows = service.getAuthorRows(fullName, page);
        model.addAttribute("grid", rows);
        model.addAttribute("totalPages", rows.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("fullName", fullName);
        return "author/author-index";
    }

    @GetMapping("/upsertForm")
    public String upsertForm(@RequestParam(required = false) Long id,Model model){

        var dto = new UpsertAuthorDTO();
        if(id != null){
            dto = service.findOne(id);
        }
        model.addAttribute("dto",dto);
        return "author/author-form";
    }

    @PostMapping("/upsert")
    public String upsert(@Valid @ModelAttribute("dto") UpsertAuthorDTO dto, BindingResult bindingResult){
        if(!bindingResult.hasErrors()){
            service.save(dto);
            return "redirect:/author/index";
        }
        return  "author/author-form";
    }


    @RequestMapping(value="/delete",method = {RequestMethod.GET,RequestMethod.POST})
    public String delete(@RequestParam(required = true) Long id, Model model){
        var dependencies = service.countByBooks(id);
        if(dependencies == 0){
            service.delete(id);
            return "redirect:/author/index";
        }
        model.addAttribute("dependencies", dependencies);
        return "author/author-delete";
    }
    @GetMapping("/detail")
    public String detail(@RequestParam(required = true) Long id, Model model){
        var info = service.getAuthorDetail(id);
        var books = service.getAuthorBooks(id);
        model.addAttribute("info", info);
        model.addAttribute("books", books);
        return "author/author-detail";
    }


}
