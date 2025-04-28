package com.example.address_book;

import jakarta.validation.constraints.NotBlank;


public class ContactDTO {
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Phone cannot be empty")
    private String phone;

    @NotBlank(message = "City cannot be empty")
    private String city;

    public ContactDTO(String name, String phone, String city) {
        this.name = name;
        this.phone = phone;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}