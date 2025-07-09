package com.ecomarketspa.EcoMarketSPA.Dto;

import com.ecomarketspa.EcoMarketSPA.Model.UserModel;

public class UserResponseDto {

    private Integer id;
    private String login;
    private String email;
    private String address;

    public UserResponseDto() {}

    public UserResponseDto(UserModel user) {
        if (user != null) {
            this.id = user.getId(); // Usa Integer directamente
            this.login = user.getLogin();
            this.email = user.getEmail();
            this.address = user.getAddress();
        }
    }

    // Getters y Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
