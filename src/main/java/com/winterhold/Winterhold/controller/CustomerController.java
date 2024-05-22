package com.winterhold.Winterhold.controller;

import com.winterhold.Winterhold.dto.author.UpsertAuthorDTO;
import com.winterhold.Winterhold.dto.customer.UpsertCustomerDTO;
import com.winterhold.Winterhold.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @GetMapping("/index")
    public String index(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "") String name,
                        @RequestParam(defaultValue = "") String membershipNumber,
                        @RequestParam(required = false) Boolean expired,
                        Model model){

        var rows = service.getRows(name,membershipNumber,expired,page);
        model.addAttribute("grid", rows);
        model.addAttribute("totalPages", rows.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("name", name);
        return "customer/customer-index";
    }

    @GetMapping("/upsertForm")
    public String upsertForm(@RequestParam(required = false) String membershipNumber,Model model){

        var dto = new UpsertCustomerDTO();
        if(membershipNumber != null){
            dto = service.findOne(membershipNumber);
        }
        model.addAttribute("dto",dto);
        return "customer/customer-upsert-form";
    }

    @PostMapping("/upsert")
    public String upsert(@Valid @ModelAttribute("dto") UpsertCustomerDTO dto, BindingResult bindingResult){
        if(!bindingResult.hasErrors()){
            service.save(dto);
            return "redirect:/customer/index";
        }
        return  "customer/customer-upsert-form";
    }

    @RequestMapping(value="/delete",method = {RequestMethod.GET,RequestMethod.POST})
    public String delete(@RequestParam String membershipNumber, Model model){
        var dependencies = service.countByCustomerNumber(membershipNumber);
        if(dependencies == 0){
            service.delete(membershipNumber);
            return "redirect:/customer/index";
        }
        model.addAttribute("dependencies", dependencies);
        return "customer/customer-delete";
    }

    @RequestMapping("/extend")
    public String extend(@RequestParam String membershipNumber){
        service.extend(membershipNumber);
        return "redirect:/customer/index";
    }
}
