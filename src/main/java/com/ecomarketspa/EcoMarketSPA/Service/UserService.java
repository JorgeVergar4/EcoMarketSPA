package com.ecomarketspa.EcoMarketSPA.Service;

import com.ecomarketspa.EcoMarketSPA.Model.UserModel;
import com.ecomarketspa.EcoMarketSPA.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserModel registerUser(String login, String password, String email, String address);
    UserModel authenticate(String login, String password);
    List<UserModel> getAllUsers();
    UserModel getUserById(Long id);
    void updateUser(UserModel user);
    void deleteUserById(Long id);
}
