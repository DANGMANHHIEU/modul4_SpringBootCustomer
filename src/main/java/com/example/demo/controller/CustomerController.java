package com.example.demo.controller;

import com.example.demo.model.Customer;
import com.example.demo.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@RestController
public class CustomerController {
    @Autowired
    private ICustomerService customerService;

    @GetMapping("/customers")
    public ModelAndView showList(){
        ModelAndView modelAndView = new ModelAndView("/list");
        modelAndView.addObject("customer",customerService.findAll());
        return modelAndView;
    }

    @GetMapping("/create-customer")
    public ModelAndView showCreate(){
        ModelAndView modelAndView = new ModelAndView("/create");
        modelAndView.addObject("customer",new Customer());
        return modelAndView;
    }
    @PostMapping("/create-customer")
    public ModelAndView saveCustomer(@ModelAttribute("customer") Customer customer){
        customerService.save(customer);
        ModelAndView modelAndView = new ModelAndView("/create");
        modelAndView.addObject("message","New customer created successfully");
        modelAndView.addObject("customer",new Customer());
        return modelAndView;
    }

    @GetMapping("/{id}/edit")
    public ModelAndView  showEdit(@PathVariable Long id){
        Optional<Customer> customer= customerService.findById(id);
        if(customer.isPresent()){
            ModelAndView modelAndView = new ModelAndView("/edit");
            modelAndView.addObject("customer",customer.get());
              return modelAndView;
        }else {
            return new ModelAndView("/error");
        }
    }

    @PostMapping("/edit")
    public ModelAndView update(@ModelAttribute("customer") Customer customer){
        customerService.save(customer);
        ModelAndView modelAndView = new ModelAndView("/edit");
        modelAndView.addObject("message","Customer updated successfully");
        modelAndView.addObject("customer",new Customer());
        return modelAndView;
    }

    @GetMapping("/{id}/delete")
    public ModelAndView delete(@PathVariable Long id){
                Optional<Customer> customer = customerService.findById(id);
                customerService.remove(customer.get().getId());
                return new ModelAndView("redirect:/customers");
    }


}
