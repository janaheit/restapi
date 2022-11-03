package be.abis.restcountriesapiclient;

import be.abis.restcountriesapiclient.model.Country;
import be.abis.restcountriesapiclient.service.CountryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CountryServiceTst {
    @Autowired
    CountryService countryService;

    @Test
    void findGermany(){
        List<Country> countries = countryService.findCountry("germany");
        assertEquals("Germany", countries.get(0).getName().getCommon());
    }

}
