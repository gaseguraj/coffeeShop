package edu.mum.coffee.ws;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.mum.coffee.domain.Order;
import edu.mum.coffee.service.OrderService;

@RestController
@RequestMapping("/ws")
public class OrdenWS {

	@Autowired
	OrderService orderService;
	
	@RequestMapping("/order")
	@PostMapping
	public ResponseEntity<Void>  create(@RequestBody Order order){
		Order newOrder =  orderService.save(order);
		if(newOrder == null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping("/order/{id}")
	@GetMapping
	public ResponseEntity<Order>  get(@PathVariable("id") Integer id){
		Order order =  orderService.findById(id);
		if(order == null){
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Order>(order, HttpStatus.OK);
	}
	
	@RequestMapping("/order/all")
	@GetMapping
	public List<Order> getAll(){
		return orderService.findAll();
	}
}
