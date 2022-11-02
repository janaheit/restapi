package be.abis.myclient11;

import be.abis.myclient11.model.Address;
import be.abis.myclient11.model.Company;
import be.abis.myclient11.model.LoginModel;
import be.abis.myclient11.model.Person;
import be.abis.myclient11.service.PersonService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClientServiceTest {

    @Autowired
    PersonService personService;

    @Test
    @Order(1)
    public void findPersonWithID1ReturnsJohnDoe(){
        Person p = personService.findPersonByID(1);
        assertEquals("John", p.getFirstName());
    }

    @Test
    @Order(2)
    public void findPersonWithEmailAndPassword(){
        LoginModel model = new LoginModel("bob.smith@oracle.com", "newPassword1256");
        Person p = personService.findPersonByEmailAndPassword(model);
        assertEquals("Bob", p.getFirstName());
    }

    @Test
    @Order(3)
    public void findPersonByCompName(){
        String compName = "Oracle";
        List<Person> personList = personService.findPersonByCompName(compName);
        assertEquals(1, personList.size());
    }

    @Test
    @Order(4)
    public void findAllPersons(){
        List<Person> personList = personService.findAllPersons();
        assertEquals(3, personList.size());
    }

    @Test
    @Order(5)
    public void addPerson(){
        Address a = new Address("Diestsevest",32,"3000","Leuven");
        Company c = new Company("Abis","016/455610","BE12345678",a);
        Person p = new Person(4,"Sandy","Schillebeeckx", LocalDate.of(2012, 12, 21),
                "sschillebeeckx@abis.be","abis123","nl",c);
        personService.addPerson(p);
        assertEquals("Sandy",personService.findPersonByID(4).getFirstName());
    }

    @Test
    @Order(7)
    public void deletePerson(){
        personService.deletePerson(4);
        assertEquals(null, personService.findPersonByID(4));
    }

    @Test
    @Order(6)
    public void changePasswordForAddedPerson(){
        LoginModel loginModel = new LoginModel("sschillebeeckx@abis.be", "newPassword");
        personService.changePassword(4, loginModel);
        assertEquals("newPassword", personService.findPersonByID(4).getPassword());
    }
}
