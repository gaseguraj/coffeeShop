/**
 * Controller for product objects, this is used to create new products, list
 * products, update and delete.
 * Project for the course CS545-WAA - Orlando Arrocha created on 2017/06/21
 * 
 * @author German Segura
 * @version 1.0
 */
package edu.mum.coffee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.mum.coffee.domain.Product;
import edu.mum.coffee.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {

		@Autowired
		ProductService productService;
	
		/**
		 * Redirect to the list of products
		 * 
		 * @return String redirect
		 */
		@RequestMapping(value={"/", "*"})
		@GetMapping
		public String home(){
			return "redirect:/product/all";
		}
		
		/**
		 * Display he form for create a new product
		 * 
		 * @return String view product.html
		 */
		@RequestMapping("/new")
		@GetMapping
		public String redirectNew(Model model, Product product){
			model.addAttribute("action", "create");
			model.addAttribute("title", "Create");
			model.addAttribute("product",product);
			return "product";
		}
		
		/**
		 * Display the form to update one product
		 * @param id 	Id of the product
		 * 				int 
		 * @return String view product.html
		 */
		@RequestMapping("/{id}")
		@GetMapping
		public String redirectUpdate(@PathVariable("id") int id, Model model){
			model.addAttribute("action", "update");
			model.addAttribute("title", "Update");
			model.addAttribute("product", productService.getProduct(id));
			return "product";
		}
		
		/**
		 * Display the list of the products
		 * 
		 * @return String view productList.html
		 */
		@RequestMapping("/all")
		@GetMapping
		public String getAll(Model model){
			model.addAttribute("products", productService.getAllProduct());
			return "productList";
		}
		
		/**
		 * Create a new product
		 * 
		 * @param Product 	Object product
		 * 					Product
		 * @return String redirect
		 */
		@RequestMapping("/create")
		@PostMapping
		public  String create(@ModelAttribute("Product") Product product){
			System.out.println("Product: " + product);
			productService.save(product);
			return "redirect:/product/all";
		}
		
		/**
		 * Update a product
		 * 
		 * @param product 	Object product
		 * 					Product
		 * @return String redirect
		 */
		@RequestMapping("/update")
		@PostMapping
		public  String update(@ModelAttribute("Product") Product product){
			System.out.println("Product: " + product);
			productService.save(product);
			return "redirect:/product/all";
		}
		
		/**
		 * Delete a product by id
		 * 
		 * @param id 	Id of the product
		 * 				int
		 * @return String redirect
		 */
		@RequestMapping("/delete/{id}")
		@GetMapping
		public  String delete(@PathVariable("id") int id){
			System.out.println("Product delete: " + id);
			productService.delete(productService.getProduct(id));
			return "redirect:/product/all";	
		}
	
}
