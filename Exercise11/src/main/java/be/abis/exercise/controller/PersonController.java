package be.abis.exercise.controller;

import be.abis.exercise.model.LoginModel;
import be.abis.exercise.model.Person;
import be.abis.exercise.repository.PersonRepository;
import be.abis.exercise.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    PersonService personService;

    @PostMapping("login")
    public Person findPersonByMailAndPwd(@RequestBody LoginModel loginModel){
        return personService.findPerson(loginModel.getEmail(), loginModel.getPassword());
    }

    @GetMapping("{id}")
    public Person findPersonByID(@PathVariable("id") int id){
        return personService.findPerson(id);
    }

    @GetMapping("")
    public List<Person> findAllPersons(){
        return personService.getAllPersons();
    }

    @PostMapping("")
    public void addPerson(@RequestBody Person person) throws IOException {
        personService.addPerson(person);
    }

    @DeleteMapping("{id}")
    public void deletePerson(@PathVariable("id") int id) {
        personService.deletePerson(id);
    }

    @PutMapping("{id}")
    public void updatePassword(@PathVariable("id") int id, @RequestBody Person person) throws IOException {
        personService.changePassword(person, person.getPassword());
    }


}
