package be.abis.exercise;

import be.abis.exercise.model.Address;
import be.abis.exercise.model.Company;
import be.abis.exercise.model.Person;
import be.abis.exercise.repository.PersonRepository;
import be.abis.exercise.utils.DateUtil;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonRepositoryTest {
	
	@Autowired
	PersonRepository personRepository;

	@Test
	@Order(1)
	public void person1ShouldBeCalledJohn(){
		String firstName = personRepository.findPerson(1).getFirstName();
		assertEquals("John",firstName);
	}

	@Test
	@Order(2)
	public void thereShouldBe3PersonsInTheFile(){
		int nrOfPersons = personRepository.getAllPersons().size();
		assertEquals(3,nrOfPersons);
	}

	@Test
	@Order(3)
	public void addNewPerson() throws IOException {
		Address a = new Address("Diestsevest",32,"3000","Leuven");
		Company c = new Company("Abis","016/455610","BE12345678",a);
		Person p = new Person(4,"Sandy","Schillebeeckx", DateUtil.parseDate("21/12/2012"),
				"sschillebeeckx@abis.be","abis123","nl",c);
		personRepository.addPerson(p);
	}

	@Test
	@Order(4)
	public void changePassWordOfAddedPerson() throws IOException {
		Person p = personRepository.findPerson("sschillebeeckx@abis.be","abis123");
		personRepository.changePassword(p,"blabla");
	}

	@Test
	@Order(5)
	public void deleteAddedPerson(){
		personRepository.deletePerson(4);
	}

	@Test
	@Order(6)
	public void findOracleReturnsBob(){
		assertEquals("Bob", personRepository.findPersonByCompany("Oracle").get(0).getFirstName());
	}
	

}
