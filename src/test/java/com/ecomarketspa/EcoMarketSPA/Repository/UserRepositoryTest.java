package com.ecomarketspa.EcoMarketSPA.Repository;

import com.ecomarketspa.EcoMarketSPA.Model.UserModel;
import com.ecomarketspa.EcoMarketSPA.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")  // Activar el perfil de prueba
public class UserRepositoryTest{

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenFindByLoginAndPassword_thenReturnUser() {
        // Crear usuario de prueba
        UserModel user = new UserModel();
        user.setLogin("testuser");
        user.setPassword("testpass");
        user.setEmail("test@example.com");
        user.setAddress("Test Address");

        // Guardar en la base de datos
        user = entityManager.persistAndFlush(user);

        // Buscar el usuario
        Optional<UserModel> found = userRepository.findByLoginAndPassword(user.getLogin(), user.getPassword());

        // Verificar resultados
        assertThat(found).isPresent();
        assertThat(found.get().getLogin()).isEqualTo("testuser");
    }

    @Test
    public void whenFindFirstByLogin_thenReturnUser() {
        // Crear usuario de prueba
        UserModel user = new UserModel();
        user.setLogin("testuser");
        user.setPassword("testpass");
        user.setEmail("test@example.com");
        user.setAddress("Test Address");

        // Guardar en la base de datos
        user = entityManager.persistAndFlush(user);

        // Buscar el usuario
        Optional<UserModel> found = userRepository.findFirstByLogin("testuser");

        // Verificar resultados
        assertThat(found).isPresent();
        assertThat(found.get().getLogin()).isEqualTo("testuser");
    }

    @Test
    public void whenFindFirstByNonExistentLogin_thenReturnEmpty() {
        Optional<UserModel> found = userRepository.findFirstByLogin("nonexistent");
        assertThat(found).isEmpty();
    }
}
