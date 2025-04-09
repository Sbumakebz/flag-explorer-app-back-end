package com.flag.explorer.app.services;

import com.flag.explorer.app.model.Country;
import com.flag.explorer.app.model.CountryDetails;

import java.util.List;

public interface FlagExplorerService {

    List<Country> findAllCountries();

    CountryDetails findCountryDetailsByName(String name);

}
