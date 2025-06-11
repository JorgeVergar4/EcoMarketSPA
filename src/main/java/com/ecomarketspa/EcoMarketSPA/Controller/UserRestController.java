package com.ecomarketspa.EcoMarketSPA.Controller;

import com.ecomarketspa.EcoMarketSPA.Dto.UserDto;
import com.ecomarketspa.EcoMarketSPA.Model.UserModel;
import com.ecomarketspa.EcoMarketSPA.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
@Tag(name = "Usuarios", description = "Operaciones relacionadas con usuarios")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    // 🔁 Conversión de modelo a DTO
    private UserDto toDto(UserModel model) {
        UserDto dto = new UserDto();
        dto.setId(model.getId().longValue());
        dto.setLogin(model.getLogin());
        dto.setEmail(model.getEmail());
        dto.setAddress(model.getAddress());
        // No exponemos la contraseña
        return dto;
    }

    private UserModel toModel(UserDto dto) {
        UserModel model = new UserModel();
        if (dto.getId() != null) model.setId(dto.getId().intValue());
        model.setLogin(dto.getLogin());
        model.setPassword(dto.getPassword());
        model.setEmail(dto.getEmail());
        model.setAddress(dto.getAddress());
        return model;
    }

    @Operation(summary = "Registrar nuevo usuario")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        UserModel registered = userService.registerUser(
                userDto.getLogin(),
                userDto.getPassword(),
                userDto.getEmail(),
                userDto.getAddress()
        );
        return registered != null
                ? ResponseEntity.ok(toDto(registered))
                : ResponseEntity.badRequest().body("Error al registrar usuario");
    }

    @Operation(summary = "Iniciar sesión de usuario")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto) {
        UserModel authenticated = userService.authenticate(
                userDto.getLogin(),
                userDto.getPassword()
        );
        if (authenticated != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "¡Bienvenido! Has iniciado sesión correctamente");
            response.put("user", toDto(authenticated));
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body("Credenciales inválidas");
        }
    }

    @Operation(summary = "Obtener lista de todos los usuarios")
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> usuarios = userService.getAllUsers()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "Obtener un usuario por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        UserModel user = userService.getUserById(id);
        return user != null ? ResponseEntity.ok(toDto(user))
                : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Actualizar un usuario por ID")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        try {
            UserModel user = toModel(userDto);
            user.setId(id.intValue());
            userService.updateUser(user);
            return ResponseEntity.ok("Usuario actualizado correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al actualizar usuario");
        }
    }

    @Operation(summary = "Eliminar un usuario por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUserById(id);
            return ResponseEntity.ok("Usuario eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar usuario");
        }
    }
}
