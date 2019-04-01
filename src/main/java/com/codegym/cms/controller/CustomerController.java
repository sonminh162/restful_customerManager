package com.codegym.cms.controller;

import com.codegym.cms.model.Customer;
import com.codegym.cms.service.CustomerService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    //-------------------Retrieve All Customers--------------------------------------------------------

    @RequestMapping(value = "/customers/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Customer>> listAllCustomers(){
        List<Customer> customers = customerService.findAll();
        if(customers.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    //-------------------Retrieve Single Customer--------------------------------------------------------

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") int id) {
        System.out.println("Fetching Customer with id " + id);
        Customer customer = customerService.findById(id);
        if(customer == null) {
            System.out.println("Customer with id " + id + "not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    //-------------------Create a Customer--------------------------------------------------------

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.POST)
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") int id, @RequestBody Customer customer) {
        System.out.println("Updating Customer" + id);

        Customer currentCustomer = customerService.findById(id);

        if(currentCustomer == null) {
            System.out.println("Customer with id " + id + "not found ");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        currentCustomer.setFirstName(customer.getFirstName());
        currentCustomer.setLastName(customer.getLastName());
        currentCustomer.setId(customer.getId());

        customerService.save(currentCustomer);
        return new ResponseEntity<>(currentCustomer, HttpStatus.OK);
    }

    //------------------- Delete a Customer --------------------------------------------------------

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Customer> deleteCustomer(@PathVariable("id") int id){
        System.out.println("Fetching & Deleting Customer with id " + id);

        Customer customer = customerService.findById(id);
        if(customer == null){
            System.out.println("Unable to delete. Customer with id " + id + " not found ");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        customerService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
