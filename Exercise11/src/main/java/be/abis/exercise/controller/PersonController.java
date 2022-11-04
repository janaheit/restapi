package be.abis.exercise.controller;

import be.abis.exercise.error.ApiError;
import be.abis.exercise.exception.ApiKeyNotCorrectException;
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
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/persons")
@Validated
@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class PersonController {

    @Autowired
    PersonService personService;

    @PostMapping("login")
    public ResponseEntity<? extends Object> findPersonByMailAndPwd(@RequestBody LoginModel loginModel) throws PersonNotFoundException {
        Person p = personService.findPerson(loginModel.getEmail(), loginModel.getPassword());
        HttpHeaders headers = new HttpHeaders();
        headers.add("api-key", personService.findApiKeyByID(p.getPersonId()));

        return new ResponseEntity<>(p, headers, HttpStatus.OK);
    }

    @GetMapping(value = "query", produces = MediaType.APPLICATION_XML_VALUE)
    public List<Person> findPersonByCompanyName(@RequestParam String compName){
        return personService.findPersonByCompany(compName);
    }

    @GetMapping(value= "{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Person findPersonByID(@PathVariable("id") @Max(5) @Digits(integer=1, fraction=0) int id) throws PersonNotFoundException {
        return personService.findPerson(id);
    }

    @GetMapping("")
    public List<Person> findAllPersons(){
        return personService.getAllPersons();
    }

    @PostMapping(value = "")
    public void addPerson(@Valid @RequestBody Person person) throws IOException, PersonAlreadyExistsException {
        personService.addPerson(person);
    }

    @DeleteMapping("{id}")
    @RolesAllowed("ROLE_ADMIN")
    public void deletePerson(@PathVariable("id") int id) throws PersonCannotBeDeletedException {
        personService.deletePerson(id);
    }

    @PatchMapping ("{id}")
    public void updatePassword(@Valid @PathVariable("id") int id, @RequestBody LoginModel loginModel,
                               @RequestHeader MultiValueMap<String, String> headers) throws IOException, PersonNotFoundException, ApiKeyNotCorrectException {

        Person p = personService.findPerson(id);

        boolean keyOK=false;

        if (headers.containsKey("api-key")){
            String auth = headers.get("api-key").get(0);
            System.out.println("key passed: "+auth);
            keyOK = (personService.findApiKeyByID(id).equals(auth));
        }

        if (keyOK){
            personService.changePassword(p, loginModel.getPassword());

        } else {
            throw new ApiKeyNotCorrectException("this api key does not exist");
        }

    }


}
