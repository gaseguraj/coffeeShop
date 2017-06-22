package edu.mum.coffee.controller;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import edu.mum.coffee.domain.Order;
import edu.mum.coffee.domain.Orderline;
import edu.mum.coffee.service.OrderService;
import edu.mum.coffee.service.PersonService;
import edu.mum.coffee.service.ProductService;

@Controller
@RequestMapping("/order")
public class OrdenController {

	@Autowired
	OrderService orderService;
	@Autowired
	ProductService productService;
	@Autowired
	PersonService personService;
	
	Order newOrder;
	Orderline newOrderline;
	
	
	
	@RequestMapping("/")
	@GetMapping
	public String redirectHome(){
		return "redirect:/order/all";
	}
	
	
	@RequestMapping("/newOrder")
	@GetMapping
	public String redirectNewOrder(Model model){
		newOrder = null;
		model.addAttribute("products", productService.getAllProduct());
		model.addAttribute("newOrder", null);
		return "orderOrderlist";
	}
	
	@RequestMapping("/orderline")
	@GetMapping
	public String createOrderline(@RequestParam(value="productId", required = true) int productId, 
								  @RequestParam(value="quantity", required = true) 	int quantity,
								  Model model){
		
		if(newOrder == null){
			newOrder = new Order();
		}
		newOrderline = new Orderline();	
		newOrderline.setQuantity(quantity);
		newOrderline.setProduct(productService.getProduct(productId));
		
		System.out.println("New Order line: " + newOrderline);
		
		newOrder.addOrderLine(newOrderline);
		
		model.addAttribute("products", productService.getAllProduct());
		model.addAttribute("newOrder", newOrder);
		return "orderOrderlist";
	}
	
	@RequestMapping("/create")
	@PostMapping
	public String  create(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		newOrder.setPerson(personService.findById(personService.findByEmail(userName).get(0).getId()));
		
		newOrder.setOrderDate(new Date());
		orderService.save(newOrder);
		return "redirect:/success";
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
