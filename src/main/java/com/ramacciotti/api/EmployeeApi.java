package com.ramacciotti.api;

import com.ramacciotti.dto.EmployeeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(url = "${external.api.base.url}", value = "external-api")
public interface EmployeeApi {

    @GetMapping(path = "/users")
    List<EmployeeDTO> fetchUsers() throws Exception;

}
