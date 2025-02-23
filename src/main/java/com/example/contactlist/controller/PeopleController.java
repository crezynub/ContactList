package com.example.contactlist.controller;

import com.example.contactlist.model.Person;
import com.example.contactlist.service.PeopleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class PeopleController {

    private final PeopleService peopleService;


    @GetMapping("/index")
    public String showPersonList(Model model) {
        model.addAttribute("people", peopleService.findAll());
        return "index";
    }

    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("person", new Person());
        return "add-person";
    }

    @PostMapping("/adduser")
    public String addPerson(@Valid Person person, BindingResult result) {
        if (result.hasErrors()) {
            return "add-person";
        }
        peopleService.save(person);
        return "redirect:/index";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Person person = peopleService.findById(id);
        if (person != null) {
            model.addAttribute("person", person);
            return "update-person";
        }

        return "redirect:/";
    }

    @PostMapping("/update/{id}")
    public String updatePerson(@PathVariable("id") long id, @Valid Person person, BindingResult result) {
        if (result.hasErrors()) {
            person.setId(id);
            return "update-person";
        }

        peopleService.update(person);

        return "redirect:/index";
    }

    @GetMapping("/delete/{id}")
    public String deletePerson(@PathVariable("id") long id) {
        peopleService.delete(id);

        return "redirect:/index";
    }
}