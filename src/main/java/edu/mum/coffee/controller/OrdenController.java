package edu.mum.coffee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.mum.coffee.domain.Order;
import edu.mum.coffee.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrdenController {

	@Autowired
	OrderService orderService;
	
	@RequestMapping("/")
	@GetMapping
	public String redirectHome(){
		return "redirect:/order/all";
	}
	
	
	@RequestMapping("/order")
	@PostMapping
	public String  create(@ModelAttribute("Order") Order order, BindingResult result){
		Order newOrder =  orderService.save(order);
		return "redirect:/order/all";
	}
	
	@RequestMapping("/{id}")
	@GetMapping
	public String  get(@PathVariable("id") Integer id, Model model){
		model.addAttribute("order",orderService.findById(id));
		return "order";
	}
	
	@RequestMapping("/all")
	@GetMapping
	public String getAll(Model model){
		model.addAttribute("orders", orderService.findAll());
		return "orderList";
	}
}
