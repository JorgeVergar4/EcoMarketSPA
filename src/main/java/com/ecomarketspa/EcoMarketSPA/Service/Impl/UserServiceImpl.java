package com.ecomarketspa.EcoMarketSPA.Service.Impl;

import com.ecomarketspa.EcoMarketSPA.Dto.EmailDto;
import com.ecomarketspa.EcoMarketSPA.Model.UserModel;
import com.ecomarketspa.EcoMarketSPA.Repository.UserRepository;
import com.ecomarketspa.EcoMarketSPA.Service.UserService;
import com.ecomarketspa.EcoMarketSPA.Service.email.EmailProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmailProducer emailProducer;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           EmailProducer emailProducer,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.emailProducer = emailProducer;
        this.passwordEncoder = passwordEncoder;
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
        // Codificamos la contraseña aquí
        userModel.setPassword(passwordEncoder.encode(password.trim()));
        userModel.setEmail(email.trim());
        userModel.setAddress(address.trim());

        UserModel savedUser = userRepository.save(userModel);

        try {
            sendWelcomeEmail(savedUser);
        } catch (Exception e) {
            log.error("Error al enviar el email de bienvenida: {}", e.getMessage());
        }

        return savedUser;
    }

    private void sendWelcomeEmail(UserModel user) {
        if (user == null || user.getLogin() == null || user.getEmail() == null) {
            throw new IllegalArgumentException("Usuario o datos de usuario inválidos");
        }

        String safeLogin = HtmlUtils.htmlEscape(user.getLogin());
        String safeEmail = HtmlUtils.htmlEscape(user.getEmail());

        String htmlContent = String.format("""
    <div style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
        <h2 style="color: #28a745;">¡Bienvenido/a a EcoMarket, %s!</h2>
        <p>Nos alegra tenerte como parte de nuestra comunidad.</p>
        <p>Tu cuenta ha sido creada exitosamente con los siguientes detalles:</p>
        <ul>
            <li><strong>Usuario:</strong> %s</li>
            <li><strong>Email:</strong> %s</li>
        </ul>
        <p>Ahora puedes comenzar a explorar nuestra plataforma y disfrutar de todos nuestros servicios.</p>
        <p>Si tienes alguna pregunta o necesitas ayuda, no dudes en contactarnos.</p>
        <br>
        <p>¡Gracias por unirte a <strong>EcoMarket</strong>!</p>
        <p style="color: #888;">Saludos cordiales,<br>El equipo de EcoMarket 🌱</p>
    </div>
    """, safeLogin, safeLogin, safeEmail);

        try {
            EmailDto emailDto = EmailDto.builder()
                    .to(user.getEmail())
                    .subject("¡Bienvenido a EcoMarket!")
                    .body(htmlContent)
                    .build();
            emailProducer.sendEmail(emailDto);
        } catch (RuntimeException e) {
            log.error("Error al enviar el email de bienvenida para el usuario {}: {}", user.getLogin(), e.getMessage());
            throw new RuntimeException("Error al enviar el email de bienvenida", e);
        }
    }

    @Override
    public UserModel authenticate(String login, String rawPassword) {
        return userRepository.findFirstByLogin(login)
                .filter(user -> passwordEncoder.matches(rawPassword, user.getPassword()))
                .orElse(null);
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
