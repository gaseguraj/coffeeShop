package edu.mum.coffee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import edu.mum.coffee.domain.Person;
import edu.mum.coffee.service.PersonService;

@Controller
@RequestMapping("/person")
public class PersonController {

	@Autowired 
	PersonService personService;
	
	@RequestMapping(value={"/", "*"})
	@GetMapping
	public String home(){
		return "redirect:/person/all";
	}
	
	@RequestMapping("/new")
	@GetMapping
	public String redirectNew(Model model, Person person){
		model.addAttribute("action", "create");
		model.addAttribute("title", "Create");
		model.addAttribute("person",person);
		return "person";
	}
	
	@RequestMapping("/{id}")
	@GetMapping
	public String get(@PathVariable("id") long id,  Model model){
		model.addAttribute("action", "create");
		model.addAttribute("title", "Update");
		model.addAttribute("person",personService.findById(id));
		return "person";
	}
	
	
	@RequestMapping("/all")
	@GetMapping
	public String getAll(Model model){
		model.addAttribute("persons", personService.getAll());
		return "personList";
	}
	
	
	@RequestMapping("/create")
	@PostMapping
	public  String create(@ModelAttribute("Person") Person person){
		System.out.println("Person: " + person);
		person.setEnable(true);
		personService.savePerson(person);
		return "redirect:/person/all";
	}
	

}