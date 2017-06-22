/**
 * Controller for the orders, this is used to create new orders and list the orders
 * Project for the course CS545-WAA - Orlando Arrocha created on 2017/06/21
 * 
 * @author German Segura
 * @version 1.0
 */
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
	
	/**
	 * Redirect to the list of orders
	 * 
	 * @return String redirect
	 */
	@RequestMapping("/")
	@GetMapping
	public String redirectHome(){
		return "redirect:/order/all";
	}
	
	/**
	 * Show the form for create a new order
	 * 
	 * @return String View orderOrderList.html
	 */
	@RequestMapping("/newOrder")
	@GetMapping
	public String redirectNewOrder(Model model){
		newOrder = null;
		model.addAttribute("products", productService.getAllProduct());
		model.addAttribute("newOrder", null);
		return "orderOrderlist";
	}
	
	/**
	 * This code is in charge to create the Orderline objects in memory, and 
	 * they are going to be displayed on the screen to the user.
	 * 
	 * @return String View orderOrderList.html
	 */
	@RequestMapping("/orderline")
	@GetMapping
	public String createOrderline(@RequestParam(value="productId", required = true) int productId, 
								  @RequestParam(value="quantity", required = true) 	int quantity,
								  Model model){
		//Create object Order
		if(newOrder == null){
			newOrder = new Order();
		}
		//Create new object Orderline every time
		newOrderline = new Orderline();	
		newOrderline.setQuantity(quantity);
		newOrderline.setProduct(productService.getProduct(productId));
		
		//Add to the list the new Orderline
		newOrder.addOrderLine(newOrderline);
		
		//Add to the model to show the current orderline
		model.addAttribute("products", productService.getAllProduct());
		model.addAttribute("newOrder", newOrder);
		return "orderOrderlist";
	}
	
	/**
	 * In this method is the order created, needs the id of the current user and 
	 * the list of Orderlines
	 * 
	 * @return String redirect
	 */
	@RequestMapping("/create")
	@PostMapping
	public String  create(){
		//Info of the user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		//Set the person who is creating the order
		newOrder.setPerson(personService.findById(personService.findByEmail(userName).get(0).getId()));
		//Set the current date
		newOrder.setOrderDate(new Date());
		//Save the order
		orderService.save(newOrder);
		return "redirect:/success";
	}
	
	/**
	 * Retrieve a order by id
	 * 
	 * @return String view order.html
	 */
	@RequestMapping("/{id}")
	@GetMapping
	public String  get(@PathVariable("id") Integer id, Model model){
		model.addAttribute("order",orderService.findById(id));
		return "order";
	}
	
	/**
	 * Display all the orders created in the application
	 * 
	 * @return String view orderList.html
	 */
	@RequestMapping("/all")
	@GetMapping
	public String getAll(Model model){
		model.addAttribute("orders", orderService.findAll());
		return "orderList";
	}
}
