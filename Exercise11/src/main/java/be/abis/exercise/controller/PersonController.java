package be.abis.exercise.controller;

import be.abis.exercise.error.ApiError;
import be.abis.exercise.exception.PersonAlreadyExistsException;
import be.abis.exercise.exception.PersonCannotBeDeletedException;
import be.abis.exercise.exception.PersonNotFoundException;
import be.abis.exercise.model.LoginModel;
import be.abis.exercise.model.Person;
import be.abis.exercise.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/persons")
public class PersonController {

    @Autowired
    PersonService personService;

    @PostMapping("login")
    public Person findPersonByMailAndPwd(@RequestBody LoginModel loginModel) throws PersonNotFoundException {
        return personService.findPerson(loginModel.getEmail(), loginModel.getPassword());
    }

    @GetMapping(value = "query", produces = MediaType.APPLICATION_XML_VALUE)
    public List<Person> findPersonByCompanyName(@RequestParam String compName){
        return personService.findPersonByCompany(compName);
    }

    @GetMapping(value= "{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Person findPersonByID(@PathVariable("id") int id) throws PersonNotFoundException {
        return personService.findPerson(id);
    }

    @GetMapping("")
    public List<Person> findAllPersons(){
        return personService.getAllPersons();
    }

    @PostMapping(value = "")
    public void addPerson(@RequestBody Person person) throws IOException, PersonAlreadyExistsException {
        personService.addPerson(person);
    }

    @DeleteMapping("{id}")
    public void deletePerson(@PathVariable("id") int id) throws PersonCannotBeDeletedException {
        personService.deletePerson(id);
    }

    @PatchMapping ("{id}")
    public void updatePassword(@PathVariable("id") int id, @RequestBody LoginModel loginModel) throws IOException {

        Person p;
        try{
            p = personService.findPerson(id);
        } catch (PersonNotFoundException e) {
            throw new RuntimeException(e);
        }

        personService.changePassword(p, loginModel.getPassword());
    }


}
