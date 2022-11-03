package be.abis.thirdpartyapiclient.service;

import be.abis.thirdpartyapiclient.model.Rate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class RateServiceImpl implements RateService {
    @Autowired
    RestTemplate restTemplate;
    private String baseURL = "https://api.exchangerate.host";

    @Override
    public double getExchangeRate(String fromCur, String toCur) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseURL+"/convert")
                .queryParam("from",fromCur)
                .queryParam("to", toCur);

        ResponseEntity<Rate> responseEntity = restTemplate.exchange(uriBuilder.toUriString(),
                HttpMethod.GET, null, Rate.class);

        Rate rate = responseEntity.getBody();
        return rate.getResult();
    }
}
