package com.bpe.springboot.data.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bpe.springboot.data.rest.entity.Person;
import com.bpe.springboot.data.rest.repository.PersonRepository;

/**
 * This rest controller competes with the PersonRepository.  It's basically an
 * extra Rest service.
 * 
 * Returns a person based on his last name:
 * 
 * http://localhost:8080/restPerson?lastName=Baggins
 * 
 * @author polinchakb
 *
 */
@RestController
public class PersonController {
	
	@Autowired
	PersonRepository dao;
	
	@RequestMapping("/restPerson")
    public List<Person> findbylastName(@RequestParam(value="lastName") String name) {
        return dao.findByLastName(name);
    }

}
