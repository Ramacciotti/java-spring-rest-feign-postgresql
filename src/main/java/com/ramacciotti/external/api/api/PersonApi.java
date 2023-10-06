package com.ramacciotti.external.api.api;

import com.ramacciotti.external.api.dto.PersonDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(url = "${external.api.base.url}", value = "external-api")
public interface PersonApi {

    @GetMapping(path = "/users")
    List<PersonDTO> fetchUsers() throws Exception;

}
