package com.flag.explorer.app;

import com.flag.explorer.app.model.Country;
import com.flag.explorer.app.model.CountryDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@SpringBootTest
class FlagExplorerApplicationTests {
	@Autowired
	private RestTemplate restTemplate;

    @Test
	public void testReadCountryDetailPass() {
		String countryNameToSearch = "Turkey";
		ResponseEntity<CountryDetails> countryDetailsResult = this.restTemplate.exchange("http://localhost:8080/api/v1/countries/" + countryNameToSearch, HttpMethod.GET, null, CountryDetails.class);
		Assertions.assertEquals(Objects.requireNonNull(countryDetailsResult.getBody()).getName() , countryNameToSearch);
	}

	@Test
	public void testReadCountryDetailFail() {
		String countryNameToSearch = "Nania";
		ResponseEntity<CountryDetails> countryDetailsResult = this.restTemplate.exchange("http://localhost:8080/api/v1/countries/" + countryNameToSearch, HttpMethod.GET, null, CountryDetails.class);
		Assertions.assertNull(countryDetailsResult.getBody());
	}

	@Test
	public void testReadCountries() {
		ResponseEntity<Country[]> countryResults = this.restTemplate.exchange("http://localhost:8080/api/v1/countries", HttpMethod.GET, null, Country[].class);
		Assertions.assertTrue(Objects.requireNonNull(countryResults.getBody()).length > 0);
	}
}
