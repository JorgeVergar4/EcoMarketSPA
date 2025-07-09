package com.ecomarketspa.EcoMarketSPA.Controller;

import com.ecomarketspa.EcoMarketSPA.Dto.UserDto;
import com.ecomarketspa.EcoMarketSPA.Model.UserModel;
import com.ecomarketspa.EcoMarketSPA.Service.UserService;
import com.ecomarketspa.EcoMarketSPA.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
@Tag(name = "Usuarios", description = "Operaciones relacionadas con usuarios")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    private UserDto toDto(UserModel model) {
        UserDto dto = new UserDto();
        dto.setId(model.getId().longValue());
        dto.setLogin(model.getLogin());
        dto.setEmail(model.getEmail());
        dto.setAddress(model.getAddress());
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
        if (registered == null) {
            return ResponseEntity.badRequest().body("Error al registrar usuario");
        }

        UserDto dto = toDto(registered);
        EntityModel<UserDto> resource = EntityModel.of(dto,
                linkTo(methodOn(UserRestController.class).getUserById(dto.getId())).withSelfRel(),
                linkTo(methodOn(UserRestController.class).getAllUsers()).withRel("all-users")
        );

        return ResponseEntity.ok(resource);
    }

    @Operation(summary = "Iniciar sesión de usuario")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto) {
        UserModel authenticated = userService.authenticate(userDto.getLogin(), userDto.getPassword());
        if (authenticated == null) {
            return ResponseEntity.badRequest().body("Credenciales inválidas");
        }

        UserDto dto = toDto(authenticated);
        EntityModel<UserDto> resource = EntityModel.of(dto,
                linkTo(methodOn(UserRestController.class).getUserById(dto.getId())).withSelfRel()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("message", "¡Bienvenido! Has iniciado sesión correctamente");
        response.put("user", resource);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener lista de todos los usuarios")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<UserDto>>> getAllUsers() {
        List<EntityModel<UserDto>> users = userService.getAllUsers().stream()
                .map(this::toDto)
                .map(dto -> EntityModel.of(dto,
                        linkTo(methodOn(UserRestController.class).getUserById(dto.getId())).withSelfRel()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(users,
                linkTo(methodOn(UserRestController.class).getAllUsers()).withSelfRel()));
    }

    @Operation(summary = "Obtener un usuario por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        UserModel user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        UserDto dto = toDto(user);
        EntityModel<UserDto> resource = EntityModel.of(dto,
                linkTo(methodOn(UserRestController.class).getUserById(id)).withSelfRel(),
                linkTo(methodOn(UserRestController.class).updateUser(id, dto)).withRel("update"),
                linkTo(methodOn(UserRestController.class).deleteUser(id)).withRel("delete")
        );

        return ResponseEntity.ok(resource);
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

    //Endpoint protegido: perfil de usuario autenticado (requiere JWT)
    @Operation(
            summary = "Ver perfil del usuario autenticado",
            description = "Este endpoint requiere autenticación con JWT",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        UserModel user = userDetails.getUser();
        return ResponseEntity.ok(toDto(user));
    }
}