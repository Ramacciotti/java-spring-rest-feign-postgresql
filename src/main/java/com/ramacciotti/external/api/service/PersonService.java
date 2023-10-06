package com.ramacciotti.external.api.service;

import com.ramacciotti.external.api.api.PersonApi;
import com.ramacciotti.external.api.dto.PersonDTO;
import com.ramacciotti.external.api.model.Person;
import com.ramacciotti.external.api.repository.PersonRepository;
import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class PersonService {

    private final PersonApi personApi;

    private final PersonRepository personRepository;

    private final AddressService addressService;

    private final CompanyService companyService;

    public PersonService(PersonApi personApi, PersonRepository personRepository, AddressService addressService, CompanyService companyService) {
        this.personApi = personApi;
        this.personRepository = personRepository;
        this.addressService = addressService;
        this.companyService = companyService;
    }

    @Transactional
    public List<PersonDTO> savePeople() throws Exception {

        List<PersonDTO> personListDTO;

        try {

            log.info("** Trying to fetch data from external api...");

            personListDTO = personApi.fetchUsers();

            log.info("** Data fetched successfully!");

            saveToDatabase(personListDTO);

        } catch (FeignException error) {

            log.error("## Ops! Could not fetch data from external API: {}", error.getMessage());

            throw new Exception("could_not_fetch_data_from_external_api");

        }

        return personListDTO;

    }

    public List<PersonDTO> getPeople() throws Exception {

        List<PersonDTO> personDTOList;

        try {

            log.info("** Trying to get people from database...");

            List<Person> personList = personRepository.findAll();

            ModelMapper modelMapper = new ModelMapper();

            personDTOList = Arrays.asList(modelMapper.map(personList, PersonDTO[].class));

            log.info("** Fetched successfully!");

        } catch (Exception error) {

            log.error("## Ops! Could not fetch data from database: {}", error.getMessage());

            throw new Exception("could_not_fetch_data_from_database");

        }

        return personDTOList;

    }


    private void saveToDatabase(List<PersonDTO> personListDTO) {

        log.info("** Saving fetched data into database...");

        for (PersonDTO PersonDTO : personListDTO) {

            Person person = new Person()
                    .withEmail(PersonDTO.getEmail())
                    .withName(PersonDTO.getName())
                    .withPhone(PersonDTO.getPhone())
                    .withUsername(PersonDTO.getUsername())
                    .withWebsite(PersonDTO.getWebsite());

            person = personRepository.save(person);

            addressService.save(PersonDTO.getAddress(), person);

            companyService.save(PersonDTO.getCompany(), person);

        }

    }

}
