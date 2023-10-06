package com.ramacciotti.external.api.controller;

import com.ramacciotti.external.api.dto.PersonDTO;
import com.ramacciotti.external.api.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "*")
@Tag(name = "person Controller")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping(path = "/people")
    @Operation(description = "Save a list of people from external API into the database.")
    public List<PersonDTO> savePeople() throws Exception {
        log.info(">> savePeople()");
        List<PersonDTO> result = personService.savePeople();
        log.info("<< savePeople()");
        return result;
    }

    @GetMapping(path = "/people")
    @Operation(description = "Returns a list of people registered in the database")
    public List<PersonDTO> getPeople() throws Exception {
        log.info(">> getPeople()");
        List<PersonDTO> result = personService.getPeople();
        log.info("<< getPeople()");
        return result;
    }

}
