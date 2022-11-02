package be.abis.myclient11.service;

import be.abis.myclient11.model.LoginModel;
import be.abis.myclient11.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class AbisPersonService implements PersonService {

    @Autowired RestTemplate restTemplate;
    private String baseUrl = "http://localhost:8080/exercise/personapi/persons";

    @Override
    public Person findPersonByID(int id) {
        return restTemplate.getForObject(baseUrl+"/"+id, Person.class);
    }

    @Override
    public Person findPersonByEmailAndPassword(LoginModel loginModel) {
        return restTemplate.postForObject(baseUrl+"/login", loginModel, Person.class);
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
    public void addPerson(Person person) {
        restTemplate.postForObject(baseUrl, person, Void.class);
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
