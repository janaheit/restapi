package be.abis.myclient11.service;

import be.abis.myclient11.error.ApiError;
import be.abis.myclient11.exception.PersonAlreadyExistsException;
import be.abis.myclient11.exception.PersonNotFoundException;
import be.abis.myclient11.model.LoginModel;
import be.abis.myclient11.model.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class AbisPersonService implements PersonService {

    @Autowired RestTemplate restTemplate;
    private String baseUrl = "http://localhost:8080/exercise/personapi/persons";

    @Override
    public Person findPersonByID(int id) throws PersonNotFoundException, JsonProcessingException {
        ResponseEntity<? extends Object> re = null;
        try{
            re = restTemplate.getForEntity(baseUrl+"/"+id, Person.class);
            return (Person)re.getBody();
        } catch (HttpStatusCodeException e){
            if (HttpStatus.NOT_FOUND == e.getStatusCode()){
                String serr = e.getResponseBodyAsString();
                ApiError ae = new ObjectMapper().readValue(serr, ApiError.class);
                throw new PersonNotFoundException(ae.getDescription());
            } else {
                System.out.println("Some other error occured");
            }
        }
        return null;
    }

    @Override
    public Person findPersonByEmailAndPassword(LoginModel loginModel) throws PersonNotFoundException, JsonProcessingException {

        ResponseEntity<? extends Object> re = null;
        try{
            re = restTemplate.postForEntity(baseUrl+"/login", loginModel, Person.class);
            return (Person)re.getBody();
        } catch (HttpStatusCodeException e){
            if (HttpStatus.NOT_FOUND == e.getStatusCode()){
                String serr = e.getResponseBodyAsString();
                ApiError ae = new ObjectMapper().readValue(serr, ApiError.class);
                throw new PersonNotFoundException(ae.getDescription());
            } else {
                System.out.println("Some other error occured");
            }
        }
        return null;
    }

    @Override
    public List<Person> findPersonByCompName(String compName) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl+"/query")
                            .queryParam("compName",compName);

        ResponseEntity<List<Person>> personListResponseEntity = restTemplate.exchange(uriBuilder.toUriString(),
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Person>>() {
                });

        List<Person> personList = personListResponseEntity.getBody();

        return personList;
    }

    @Override
    public List<Person> findAllPersons() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl);
        ResponseEntity<List<Person>> personListResponseEntity = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Person>>() {});
        return personListResponseEntity.getBody();
    }

    @Override
    public Boolean addPerson(Person person) throws JsonProcessingException, PersonAlreadyExistsException {

        ResponseEntity<? extends Object> re = null;
        try{
            re = restTemplate.postForEntity(baseUrl, person, Person.class);
            return true;
        } catch (HttpStatusCodeException e){
            if (HttpStatus.CONFLICT == e.getStatusCode()){
                String serr = e.getResponseBodyAsString();
                ApiError ae = new ObjectMapper().readValue(serr, ApiError.class);
                throw new PersonAlreadyExistsException(ae.getDescription());
            } else {
                System.out.println("Some other error occured");
            }
        }
        return false;
    }


    @Override
    public void deletePerson(int id) {
        restTemplate.delete(baseUrl+"/"+id);
    }

    @Override
    public void changePassword(int id, LoginModel loginModel) {

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl+"/"+id);
        HttpEntity<LoginModel> requestEntity = new HttpEntity<>(loginModel);
        restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.PATCH,
                requestEntity, Void.class);
    }
}
