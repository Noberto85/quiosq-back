package com.webone.quiosq.itg.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserDTO {

    private Long id;
    private String nickname;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    private String gender;
    @JsonProperty("country_id")
    private String countryId;
    private String email;
    private Identification identification;
    private Address address;
    private Phone phone;


    @Data
    public static class Identification {

        private String number;
        private String type;
    }

    @Data
    public static class Address {

        private String address;
        private String city;
        private String state;
        private String zip_code;
    }

    @Data
    public static class Phone {

        private String area_code;
        private String extension;
        private String number;
        private Boolean verified;
    }
}
