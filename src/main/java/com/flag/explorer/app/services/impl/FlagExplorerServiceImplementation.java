package com.flag.explorer.app.services.impl;

import com.flag.explorer.app.model.Country;
import com.flag.explorer.app.model.CountryDetails;
import com.flag.explorer.app.services.FlagExplorerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class FlagExplorerServiceImplementation implements FlagExplorerService {
	
	@Value( "${countries.url}" )
	private String countriesUrl;
	
	@Value( "${country.details.url}" )
	private String countryDetailsUrl;

    private final RestTemplate restTemplate;

    public FlagExplorerServiceImplementation(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Country> findAllCountries() {
		List<Country> countries = new ArrayList<>();
		ResponseEntity<List<LinkedHashMap<String, Object>>> response =
									restTemplate.exchange(
													countriesUrl,
													HttpMethod.GET,
													null,
													new ParameterizedTypeReference<List<LinkedHashMap<String, Object>>>() {}
													);
		List<LinkedHashMap<String, Object>> bodyArray = response.getBody();

		if (bodyArray != null) {
			for (LinkedHashMap<String, Object> body : bodyArray) {
				// Handle nested structures:
				Map<String, Object> name = (Map<String, Object>) body.get("name");
				String countryName = (String) name.get("common");

				Map<String, Object> flags = (Map<String, Object>) body.get("flags");
				String countryFlag = (String) flags.get("png");

				countries.add(new Country(countryName, countryFlag));
			}
		}
		return countries;
    }

    @Override
    public CountryDetails findCountryDetailsByName(String name) {
		CountryDetails countryDetails = null;
		ResponseEntity<List<LinkedHashMap<String, Object>>> response =
									restTemplate.exchange(
													countryDetailsUrl + name,
													HttpMethod.GET,
													null,
													new ParameterizedTypeReference<List<LinkedHashMap<String, Object>>>() {}
													);
		List<LinkedHashMap<String, Object>> bodyArray = response.getBody();

		if (bodyArray != null) {
			for (LinkedHashMap<String, Object> body : bodyArray) {
				// Handle nested structures:
				Map<String, Object> jsonName = (Map<String, Object>) body.get("name");
				String countryName = (String) jsonName.get("common");

				Map<String, Object> flags = (Map<String, Object>) body.get("flags");
				String countryFlag = (String) flags.get("png");

				Integer population = (Integer) body.get("population");

				List<Object> jsonCapital = (List<Object>) body.get("capital");
				String capital = (String) jsonCapital.get(0);

				countryDetails = new CountryDetails(countryName, population, capital, countryFlag);
			}
		}
		
        return countryDetails;
    }
}
