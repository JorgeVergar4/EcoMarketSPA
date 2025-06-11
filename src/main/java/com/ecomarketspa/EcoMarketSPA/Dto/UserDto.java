package com.ecomarketspa.EcoMarketSPA.Dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO que representa un usuario del sistema")
public class UserDto {

    @Schema(description = "ID del usuario", example = "1")
    private Long id;

    @Schema(description = "Nombre de usuario (login)", example = "eco_user01")
    private String login;

    @Schema(description = "Contraseña del usuario", example = "segura123")
    private String password;

    @Schema(description = "Correo electrónico del usuario", example = "usuario@ecomarket.cl")
    private String email;

    @Schema(description = "Dirección del usuario", example = "Av. Principal 123, Santiago")
    private String address;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
