package com.ramacciotti.external.api.dto;


import lombok.*;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {

    private String name;

    private String username;

    private String email;

    private String phone;

    private String website;

    private AddressDTO address;

    private CompanyDTO company;

}
