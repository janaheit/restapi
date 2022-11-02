package be.abis.myclient11.service;

import be.abis.myclient11.model.LoginModel;
import be.abis.myclient11.model.Person;

import java.util.List;

public interface PersonService {
    Person findPersonByID(int id);
    Person findPersonByEmailAndPassword(LoginModel loginModel);
    List<Person> findPersonByCompName(String compName);
    List<Person> findAllPersons();
    void addPerson(Person person);
    void deletePerson(int id);
    void changePassword(int id, LoginModel loginModel);
}
