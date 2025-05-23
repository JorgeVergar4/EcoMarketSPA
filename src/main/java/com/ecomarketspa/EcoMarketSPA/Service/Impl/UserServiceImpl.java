package com.ecomarketspa.EcoMarketSPA.Service.Impl;

import com.ecomarketspa.EcoMarketSPA.Model.UserModel;
import com.ecomarketspa.EcoMarketSPA.Repository.UserRepository;
import com.ecomarketspa.EcoMarketSPA.Service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserModel registerUser(String login, String password, String email, String address) {
        if (login == null || password == null || email == null || address == null ||
                login.trim().isEmpty() || password.trim().isEmpty() ||
                email.trim().isEmpty() || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Todos los campos son requeridos");
        }

        if (userRepository.findFirstByLogin(login).isPresent()) {
            throw new RuntimeException("El usuario ya existe");
        }

        UserModel userModel = new UserModel();
        userModel.setLogin(login.trim());
        userModel.setPassword(password.trim());
        userModel.setEmail(email.trim());
        userModel.setAddress(address.trim());

        return userRepository.save(userModel);
    }


    @Override
    public UserModel authenticate(String login, String password){
        return userRepository.findByLoginAndPassword(login, password).orElse(null);
    }

    @Override
    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserModel getUserById(Long id) {
        return userRepository.findById(Math.toIntExact(id)).orElse(null);
    }

    @Override
    public void updateUser(UserModel user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(Math.toIntExact(id));
    }
}
