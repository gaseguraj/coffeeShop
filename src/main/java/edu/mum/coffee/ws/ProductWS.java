package edu.mum.coffee.ws;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.mum.coffee.domain.Product;
import edu.mum.coffee.service.ProductService;

@RestController
@RequestMapping("/ws")
public class ProductWS {

		@Autowired
		ProductService productService;
	
		@RequestMapping("/product/all")
		@GetMapping
		public List<Product> getAll(){
			return productService.getAllProduct();
		}
		
		@RequestMapping("/product")
		@PostMapping
		public  ResponseEntity<Object> create(@RequestBody Product product){
			System.out.println("Product: " + product);
			Product productNew = productService.save(product);
			if(productNew == null){
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}else{
				return new ResponseEntity<>(HttpStatus.OK);	
			}
		}
		
		@RequestMapping("/product/update")
		@PutMapping
		public  ResponseEntity<Object> update(@RequestBody Product product){
			System.out.println("Product: " + product);
			Product productNew = productService.save(product);
			if(productNew == null){
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}else{
				return new ResponseEntity<>(HttpStatus.OK);	
			}
		}
		
		@RequestMapping("/product/{id}")
		@DeleteMapping
		public  ResponseEntity<Object> delete(@PathVariable("id") int id){
			System.out.println("Product delete: " + id);
			productService.delete(productService.getProduct(id));
			return new ResponseEntity<>(HttpStatus.OK);	
		}
	
}
