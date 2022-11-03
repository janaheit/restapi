package be.abis.restcountriesapiclient.service;

import be.abis.restcountriesapiclient.model.Country;

import java.util.List;

public interface CountryService {

    List<Country> findCountry(String name);
    List<Country> findCountriesWithLanguage(String language);
}
