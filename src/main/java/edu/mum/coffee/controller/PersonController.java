/**
 * Controller for person objects, this is used to create new users and permit
 * the user to update their information.
 * Project for the course CS545-WAA - Orlando Arrocha created on 2017/06/21
 * 
 * @author German Segura
 * @version 1.0
 */
package edu.mum.coffee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import edu.mum.coffee.domain.Person;
import edu.mum.coffee.domain.Role;
import edu.mum.coffee.service.PersonService;

@Controller
@RequestMapping("/person")
public class PersonController {

	@Autowired 
	PersonService personService;
	
	/**
	 * Redirect to the list of persons
	 * 
	 * @return String redirect
	 */
	@RequestMapping(value={"/", "*"})
	@GetMapping
	public String home(){
		return "redirect:/person/all";
	}
	
	/**
	 * Display he form for create a new person
	 * 
	 * @param Person 	Object person
	 * 					Person
	 * @return String view person.html
	 */
	@RequestMapping("/new")
	@GetMapping
	public String redirectNew(Model model, Person person){
		model.addAttribute("action", "create");
		model.addAttribute("title", "Create");
		model.addAttribute("person",person);
		return "person";
	}
	
	/**
	 * Show the information of the user in order to allow him to update it,
	 * the important thing is only the user can see and update his ows information,
	 * but the admin can see and update all the users.
	 * 
	 * @param id	Id of the user
	 * 				Long  
	 * @return String View person.html
	 */
	@RequestMapping("/{id}")
	@GetMapping
	public String get(@PathVariable("id") Long id,  Model model){
		//Information of the current user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		Person person = personService.findById(personService.findByEmail(userName).get(0).getId());
		//If the user id is not the same than the id of the current user logged
		if(!id.equals(person.getId())){
			//Ask if is an admin, if not redirect to index
			boolean hasUserRole = authentication.getAuthorities().stream()
			          .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
			if(!hasUserRole){
				return "redirect:/index";	
			}
		}
		//Display the information
		model.addAttribute("action", "create");
		model.addAttribute("title", "Update");
		model.addAttribute("person",personService.findById(id));
		return "person";
	}
	
	/**
	 * Display the list of the persons
	 * 
	 * @return String view personList.html
	 */
	@RequestMapping("/all")
	@GetMapping
	public String getAll(Model model){
		model.addAttribute("persons", personService.getAll());
		return "personList";
	}
	
	/**
	 * Create new user, receives the person param
	 * 
	 * @param person 	Object person 
	 * 					Person
	 * 
	 * @return String redirect
	 */
	@RequestMapping("/create")
	@PostMapping
	public  String create(@ModelAttribute("Person") Person person){
		System.out.println("Person: " + person);
		person.setEnable(true);
		personService.savePerson(person);
		
		Role role = new Role();
		role.setName(person.getEmail());
		role.setRole("ROLE_USER");
		personService.saveRole(role);
		return "redirect:/index";
	}
	

}
