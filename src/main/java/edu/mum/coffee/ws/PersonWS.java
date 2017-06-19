package edu.mum.coffee.ws;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.mum.coffee.domain.Person;
import edu.mum.coffee.service.PersonService;

@RestController
@RequestMapping("/ws")
public class PersonWS {

	@Autowired 
	PersonService personService;
	
	@RequestMapping(value = "/person/{email}", method = RequestMethod.GET)
	@GetMapping
	public List<Person> getAll(@PathVariable("email") String email){
		return personService.findByEmail(email);
	}
	
	@RequestMapping(value = "/person/id/{id}", method = RequestMethod.GET)
	@GetMapping
	public Person getById(@PathVariable("id") Long id){
		return personService.findById(id);
	}
	
	@RequestMapping("/person")
	@PostMapping
	public  ResponseEntity<Object> create(@RequestBody Person person){
		System.out.println("Person: " + person);
		Person personNew = personService.savePerson(person);
		if(personNew == null){
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}else{
			return new ResponseEntity<>(HttpStatus.OK);	
		}
	}
	
	@RequestMapping("/person/update")
	@PutMapping
	public  ResponseEntity<Object> update(@RequestBody Person person){
		System.out.println("Person: " + person);
		Person personNew = personService.savePerson(person);
		if(personNew == null){
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}else{
			return new ResponseEntity<>(HttpStatus.OK);	
		}
	}
	
	@RequestMapping(value = "/person/{id}", method= RequestMethod.DELETE)
	public  ResponseEntity<Object> delete(@PathVariable("id") Long id){
		System.out.println("Person delete: " + id);
		personService.removePerson(personService.findById(id));
		return new ResponseEntity<>(HttpStatus.OK);	
	}
}
