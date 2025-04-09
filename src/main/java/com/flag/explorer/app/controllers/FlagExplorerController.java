package com.flag.explorer.app.controllers;

import com.flag.explorer.app.model.Country;
import com.flag.explorer.app.model.CountryDetails;
import com.flag.explorer.app.services.FlagExplorerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/countries", produces = {MediaType.APPLICATION_JSON_VALUE})
@Tag(name = "FlagExplorer Controller", description = "Retrieve All Countries, Retrieve Country Details")
public class FlagExplorerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FlagExplorerController.class);
    private final FlagExplorerService flagExplorerService;

    public FlagExplorerController(FlagExplorerService flagExplorerService) {
        this.flagExplorerService = flagExplorerService;
    }

    @Operation(summary = "Retrieve all countries", description = "Provides a list of all countries")
    @ApiResponse(responseCode = "200", description = "A list of countries", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Country.class))))
    @ApiResponse(responseCode = "204", description = "No countries exists", content = @Content())
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Country>> findAllCountries() {
        LOGGER.info("getAllCountries Invoked");
        List<Country> countries = flagExplorerService.findAllCountries();
        return new ResponseEntity<>(countries, countries.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @Operation(summary = "Retrieve details about a specific country", description = "Provides country details based on the provided Name")
    @ApiResponse(responseCode = "200", description = "Details about the country", content = @Content(schema = @Schema(implementation = CountryDetails.class)))
    @ApiResponse(responseCode = "204", description = "A country with the specified name does not exist", content = @Content())
    @GetMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CountryDetails> findCountryDetailsByName(@PathVariable String name) {
        try {
            LOGGER.info("getCountryDetailsByName Invoked");
            CountryDetails countryDetails = flagExplorerService.findCountryDetailsByName(name);
            LOGGER.info("Retrieved country name : {}", countryDetails.getName());
            return new ResponseEntity<>(countryDetails, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }
}
