package be.abis.myclient11.service;

import be.abis.myclient11.exception.PersonAlreadyExistsException;
import be.abis.myclient11.exception.PersonCannotBeDeletedException;
import be.abis.myclient11.exception.PersonNotFoundException;
import be.abis.myclient11.exception.ValidationException;
import be.abis.myclient11.model.LoginModel;
import be.abis.myclient11.model.Person;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface PersonService {
    Person findPersonByID(int id) throws PersonNotFoundException, JsonProcessingException;
    Person findPersonByEmailAndPassword(LoginModel loginModel) throws PersonNotFoundException, JsonProcessingException;
    List<Person> findPersonByCompName(String compName);
    List<Person> findAllPersons();
    Boolean addPerson(Person person) throws PersonNotFoundException, JsonProcessingException, PersonAlreadyExistsException, ValidationException;
    void deletePerson(int id) throws PersonCannotBeDeletedException, JsonProcessingException;
    void changePassword(int id, String apiKey, LoginModel loginModel);
}
