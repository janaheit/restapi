package be.abis.exercise.controller;

import be.abis.exercise.error.ApiError;
import be.abis.exercise.exception.PersonAlreadyExistsException;
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
    public ResponseEntity<? extends Object> findPersonByMailAndPwd(@RequestBody LoginModel loginModel){
        try {
            Person p = personService.findPerson(loginModel.getEmail(), loginModel.getPassword());
            return new ResponseEntity<Person>(p, HttpStatus.OK);
        } catch (PersonNotFoundException e) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            ApiError err = new ApiError("person not found", status.value(), e.getMessage());
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("content-type", MediaType.APPLICATION_PROBLEM_JSON_VALUE);
            return new ResponseEntity<ApiError>(err, responseHeaders, status);
        }

    }

    @GetMapping(value = "query", produces = MediaType.APPLICATION_XML_VALUE)
    public List<Person> findPersonByCompanyName(@RequestParam String compName){
        return personService.findPersonByCompany(compName);
    }

    @GetMapping(value= "{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<? extends Object> findPersonByID(@PathVariable("id") int id){

        try {
            Person p = personService.findPerson(id);;
            return new ResponseEntity<Person>(p, HttpStatus.OK);
        } catch (PersonNotFoundException e) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            ApiError err = new ApiError("person not found", status.value(), e.getMessage());
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("content-type", MediaType.APPLICATION_PROBLEM_JSON_VALUE);
            return new ResponseEntity<ApiError>(err, responseHeaders, status);
        }
    }

    @GetMapping("")
    public List<Person> findAllPersons(){
        return personService.getAllPersons();
    }

    @PostMapping(value = "")
    public ResponseEntity<? extends Object> addPerson(@RequestBody Person person) throws IOException {
        System.out.println(person.getBirthDate());

        ResponseEntity<? extends Object> re=null;
        try{
            personService.addPerson(person);
            return new ResponseEntity<Person>(person, HttpStatus.OK);
        } catch (PersonAlreadyExistsException e) {
            HttpStatus status = HttpStatus.CONFLICT;
            ApiError err = new ApiError("person already exists", status.value(), e.getMessage());
            HttpHeaders responseHeaders = new HttpHeaders();
            return new ResponseEntity<ApiError>(err, responseHeaders, status);
        }
    }

    @DeleteMapping("{id}")
    public void deletePerson(@PathVariable("id") int id) {
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
