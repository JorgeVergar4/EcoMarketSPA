package com.ecomarketspa.EcoMarketSPA.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Schema(hidden = true)
@Entity
@Table(name = "users_table")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String login;

    private String password;

    private String email;

    private String address;

    public UserModel() {
    }

    public UserModel(Integer id, String login, String password, String email, String address) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.address = address;
    }

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
