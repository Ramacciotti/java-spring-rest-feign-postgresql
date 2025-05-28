package com.ramacciotti.dto;

import lombok.*;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    private String name;

    private String username;

    private String email;

    private String phone;

    private String website;

    private AddressDTO address;

    private CompanyDTO company;

}
