package be.abis.myclient11.service;

import be.abis.myclient11.exception.PersonAlreadyExistsException;
import be.abis.myclient11.exception.PersonNotFoundException;
import be.abis.myclient11.model.LoginModel;
import be.abis.myclient11.model.Person;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface PersonService {
    Person findPersonByID(int id) throws PersonNotFoundException, JsonProcessingException;
    Person findPersonByEmailAndPassword(LoginModel loginModel) throws PersonNotFoundException, JsonProcessingException;
    List<Person> findPersonByCompName(String compName);
    List<Person> findAllPersons();
    Boolean addPerson(Person person) throws PersonNotFoundException, JsonProcessingException, PersonAlreadyExistsException;
    void deletePerson(int id);
    void changePassword(int id, LoginModel loginModel);
}
