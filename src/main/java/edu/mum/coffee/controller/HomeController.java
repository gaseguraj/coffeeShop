/**
 * Controller for anonymous users, can login to the application or see products
 * Project for the course CS545-WAA - Orlando Arrocha created on 2017/06/21
 * 
 * @author German Segura
 * @version 1.0
 */

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

	/**
	 * Show the index of the Web Application and show the products consuming
	 * the product Restful WebService
	 * 
	 * @param model
	 *            Model to show the information in the Web Page
	 * @return String index.html
	 */
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
	
	/**
	 * Show the login page
	 * 
	 * @return String login.html
	 */
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	/**
	 * Show the page success, used after create a new order
	 * 
	 * @return String success.html
	 */
	@RequestMapping("/success") 
	@GetMapping
	public String redirectSuccess(){
		return "success";
	}
	
	/**
	 * Show the page to register in the application
	 * 
	 * @return String Redirect 
	 */
	@RequestMapping("/signup") 
	@GetMapping
	public String redirectSignUp(){
		return "redirect:/person/new";
	}
	
	/**
	 * Make the logout of the user
	 * 
	 * @return String redirect
	 */
	@RequestMapping("/logout")
	@GetMapping
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/login";
	}
	
	/**
	 * Show the page with the information of the user, for this is necessary to
	 * retrieve the information of the logged user 
	 * 
	 * @return String redirect 
	 */
	@GetMapping({"/myinfo"})
	public String myInfo() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		Person person = personService.findById(personService.findByEmail(userName).get(0).getId());
		return "redirect:/person/"+person.getId();
	}
	
	
}
