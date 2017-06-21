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
	
		
		@RequestMapping(value={"/", "*"})
		@GetMapping
		public String home(){
			return "redirect:/product/all";
		}
		
		@RequestMapping("/new")
		@GetMapping
		public String redirectNew(Model model, Product product){
			model.addAttribute("action", "create");
			model.addAttribute("title", "Create");
			model.addAttribute("product",product);
			return "product";
		}
		
		@RequestMapping("/{id}")
		@GetMapping
		public String redirectUpdate(@PathVariable("id") int id, Model model){
			model.addAttribute("action", "update");
			model.addAttribute("title", "Update");
			model.addAttribute("product", productService.getProduct(id));
			return "product";
		}
		
		
		@RequestMapping("/all")
		@GetMapping
		public String getAll(Model model){
			model.addAttribute("products", productService.getAllProduct());
			return "productList";
		}
		
		
		@RequestMapping("/create")
		@PostMapping
		public  String create(@ModelAttribute("Product") Product product){
			System.out.println("Product: " + product);
			productService.save(product);
			return "redirect:/product/all";
		}
		
		@RequestMapping("/update")
		@PostMapping
		public  String update(@ModelAttribute("Product") Product product){
			System.out.println("Product: " + product);
			productService.save(product);
			return "redirect:/product/all";
		}
		
		@RequestMapping("/delete/{id}")
		@GetMapping
		public  String delete(@PathVariable("id") int id){
			System.out.println("Product delete: " + id);
			productService.delete(productService.getProduct(id));
			return "redirect:/product/all";	
		}
	
}
