package be.abis.myclient11.service;

import be.abis.myclient11.error.ApiError;
import be.abis.myclient11.error.ValidationError;
import be.abis.myclient11.exception.PersonAlreadyExistsException;
import be.abis.myclient11.exception.PersonCannotBeDeletedException;
import be.abis.myclient11.exception.PersonNotFoundException;
import be.abis.myclient11.exception.ValidationException;
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
    private static String apiKey=null;

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
            } else if (HttpStatus.UNAUTHORIZED==e.getStatusCode()){
                System.out.println("unauthorised");
            }else {
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
            this.apiKey = re.getHeaders().get("api-key").get(0);
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
    public Boolean addPerson(Person person) throws JsonProcessingException, PersonAlreadyExistsException, ValidationException {

        ResponseEntity<? extends Object> re = null;
        try{
            re = restTemplate.postForEntity(baseUrl, person, Person.class);
            return true;
        } catch (HttpStatusCodeException e){
            if (HttpStatus.CONFLICT == e.getStatusCode()){
                String serr = e.getResponseBodyAsString();
                ApiError ae = new ObjectMapper().readValue(serr, ApiError.class);
                throw new PersonAlreadyExistsException(ae.getDescription());
            } if (HttpStatus.BAD_REQUEST == e.getStatusCode()) {
                String serr = e.getResponseBodyAsString();
                ApiError ae = new ObjectMapper().readValue(serr, ApiError.class);
                List<ValidationError> validationErrors = ae.getInvalidParams();
                if (validationErrors.size()!=0){
                    String errorString = validationErrors.toString();
                    throw new ValidationException(errorString);
                }
            } else {
                System.out.println("Some other error occured");
            }
        }
        return false;
    }


    @Override
    public void deletePerson(int id) throws PersonCannotBeDeletedException, JsonProcessingException {
        try{
            restTemplate.delete(baseUrl+"/"+id);
        } catch (HttpStatusCodeException e){
            if (HttpStatus.NOT_FOUND == e.getStatusCode()){
                String serr = e.getResponseBodyAsString();
                ApiError ae = new ObjectMapper().readValue(serr, ApiError.class);
                throw new PersonCannotBeDeletedException(ae.getDescription());
            } else {
                System.out.println("Some other error occured");
            }
        }
    }

    @Override
    public void changePassword(int id, String apiKey, LoginModel loginModel) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("api-key", apiKey);
        HttpEntity<LoginModel> requestEntity = new HttpEntity<>(loginModel, headers);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl+"/"+id);
        restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.PATCH,
                requestEntity, Void.class);
    }
}
