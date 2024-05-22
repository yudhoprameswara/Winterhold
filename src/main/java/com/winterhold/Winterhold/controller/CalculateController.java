package com.winterhold.Winterhold.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/calculate")
public class CalculateController {

    @GetMapping("/calculateForm")
    public String get(){
        return "calculate/calculate-form-one";
    }

    @PostMapping("calculate")
    public String post(@RequestParam Long angkaPertama,
                @RequestParam Long angkaKedua,
                Model model){
        Long hasil = angkaPertama + angkaKedua;
        model.addAttribute("hasilTambah",hasil);
        return "calculate/calculate-form-two";
    }

    @PostMapping("calculateTwo")
    public String postTwo(@RequestParam Long hasil,
                       @RequestParam Long angkaKetiga,
                       Model model){
        Long hasilKali = hasil + angkaKetiga;
        model.addAttribute("hasilKali",hasilKali);
        return "calculate/calculate-form-three";
    }
}
