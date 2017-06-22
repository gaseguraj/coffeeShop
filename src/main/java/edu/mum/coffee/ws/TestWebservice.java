package edu.mum.coffee.ws;


import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public class TestWebservice {

	private final String uriOrder = "http://localhost:8080/ws/order"; 
	private final String uriPerson = "http://localhost:8080/ws/person";
	private final String uriProduct = "http://localhost:8080/ws/product";
	
	private RestTemplate restTemplate = new RestTemplate();
	
	public static void main(String[] args) {
		TestWebservice tw = new TestWebservice();
		
		//Orders
		tw.testGetOrders();
		//Persons
		tw.testGetPersons();
		//Products
		tw.testGetProducts();
	}

	public void testGetOrders(){
		 ResponseEntity<String> result = restTemplate.exchange(uriOrder + "/all",
	                            HttpMethod.GET, null, new ParameterizedTypeReference<String>() {});
		 System.out.println("Get Orders: " + result);
	}
	
	public void testGetPersons(){
		 ResponseEntity<String> result = restTemplate.exchange(uriPerson + "/all",
	                            HttpMethod.GET, null, new ParameterizedTypeReference<String>() {});
		 System.out.println("Get Persons: " + result);
	}
	
	public void testGetProducts(){
		 ResponseEntity<String> result = restTemplate.exchange(uriProduct + "/all",
	                            HttpMethod.GET, null, new ParameterizedTypeReference<String>() {});
		 System.out.println("Get Products: " + result);
	}
}
