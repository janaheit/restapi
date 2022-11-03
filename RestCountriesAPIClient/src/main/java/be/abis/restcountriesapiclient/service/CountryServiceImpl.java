package be.abis.restcountriesapiclient.service;

import be.abis.restcountriesapiclient.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    RestTemplate restTemplate;
    private String baseURL = "https://restcountries.com/v3.1";

    @Override
    public List<Country> findCountry(String name) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseURL+"/name/"+name);

        ResponseEntity<List<Country>> countryListResponseEntity = restTemplate.exchange(uriBuilder.toUriString(),
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Country>>() {
                });

        List<Country> countryList = countryListResponseEntity.getBody();
        return countryList;
    }

    @Override
    public List<Country> findCountriesWithLanguage(String language) {
        return null;
    }
}
