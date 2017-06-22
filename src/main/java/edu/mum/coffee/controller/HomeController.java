package edu.mum.coffee.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import edu.mum.coffee.domain.Person;
import edu.mum.coffee.domain.Product;
import edu.mum.coffee.service.PersonService;

@Controller
public class HomeController {
	
	@Autowired
	PersonService personService;
	
	@GetMapping({"/", "/index", "/home"})
	@PostMapping("/index")
	public String homePage(Model model) {
        final String uri = "http://localhost:8080/ws/product/all";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Product>> rateResponse =
                restTemplate.exchange(uri,
                            HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>() {
                    });
        List<Product> products = rateResponse.getBody();
        model.addAttribute("products", products);
        
		return "index";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping({"/secure"})
	public String securePage() {
		return "secure";
	}
	
	@RequestMapping("/success") 
	@GetMapping
	public String redirectSuccess(){
		return "success";
	}
	
	@RequestMapping("/signup") 
	@GetMapping
	public String redirectSignUp(){
		return "redirect:/person/new";
	}
	
	@RequestMapping("/logout")
	@GetMapping
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/login";
	}
	
	@GetMapping({"/myinfo"})
	public String myInfo() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		Person person = personService.findById(personService.findByEmail(userName).get(0).getId());
		return "redirect:/person/"+person.getId();
	}
	
	
}
