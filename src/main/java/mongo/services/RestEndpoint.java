package mongo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value="/customer")
public class RestEndpoint {
	

@Autowired
public CustomerRepository repository;
	
	@RequestMapping(value="/all", method=RequestMethod.GET)
	public List<Customer> getAllCustomers() {
		return repository.findAll();
	}
	
	@RequestMapping(value="/ln/{lastName}", method=RequestMethod.GET)
	public List<Customer> getCustomerByLastName(@PathVariable String lastName) {
		return repository.findByLastName(lastName);
	}
	
	@RequestMapping(value="/fn/{firstName}", method=RequestMethod.GET)
	public Customer getCustomerByFirstName(@PathVariable String firstName) {
		return repository.findByFirstName(firstName);
	}
	
	@RequestMapping(value="/fn/{firstName}", method=RequestMethod.DELETE)
	public String deleteCustomerByFirstName(@PathVariable String firstName) {
		Customer customer = repository.findByFirstName(firstName);
		repository.delete(customer);
		return "deleted:" + customer.toString();
	}
	
	@RequestMapping(value="/new/{firstName}/{lastName}", method=RequestMethod.POST)
	public Customer postCustomer(@PathVariable String firstName, @PathVariable String lastName) {
		Customer customer = new Customer(firstName, lastName);
		repository.save(customer);
		return customer;
	}
}
