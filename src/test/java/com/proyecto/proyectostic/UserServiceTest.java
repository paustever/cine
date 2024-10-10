package com.proyecto.proyectostic;
import com.proyecto.proyectostic.excepcion.InvalidCredentialsException;
import com.proyecto.proyectostic.excepcion.UserAlreadyExistsException;
import com.proyecto.proyectostic.model.User;
import com.proyecto.proyectostic.repository.UserRepository;
import com.proyecto.proyectostic.service.TokenService;
import com.proyecto.proyectostic.service.UserService;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class UserServiceTest {

        @Mock
        private UserRepository userRepository;

        @Mock
        private TokenService tokenService;

        @Mock
        private PasswordEncoder passwordEncoder;

        @InjectMocks
        private UserService userService;

        @BeforeEach
        public void setup() {
            MockitoAnnotations.openMocks(this);  // Inicializa los mocks
        }

        @Test
        public void testGetAllUsers() {
            // Crear usuarios de prueba
            User user1 = new User(1, "Paula", "paula@example.com", "password123");
            User user2 = new User(2, "Carlos", "carlos@example.com", "password456");
            List<User> userList = Arrays.asList(user1, user2);

            // Mockear el comportamiento del repositorio
            when(userRepository.findAll()).thenReturn(userList);

            // Ejecutar la función del servicio
            List<User> result = userService.getAllUsers();

            // Verificar que el resultado es el esperado
            assertEquals(2, result.size());
            assertEquals("Paula", result.get(0).getName());
            assertEquals("Carlos", result.get(1).getName());
        }

    @Test
    public void testRegisterUser_Success() {
        // Crear usuario de prueba
        User user = new User(1, "Paula", "paula@example.com", "password123");

        // Simular que el email no existe en la base de datos
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(user.getPassword())).thenReturn("hashedPassword");
        when(userRepository.save(user)).thenReturn(user);

        // Ejecutar la función del servicio
        User result = userService.registerUser(user);

        // Verificar que el usuario fue registrado correctamente
        assertEquals("hashedPassword", result.getPassword());
        verify(userRepository).save(user);
    }

    @Test
    public void testRegisterUser_UserAlreadyExists() {
        // Crear usuario de prueba
        User user = new User(1, "Paula", "paula@example.com", "password123");

        // Simular que el email ya existe
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        // Verificar que se lanza la excepción adecuada
        assertThrows(UserAlreadyExistsException.class, () -> {
            userService.registerUser(user);
        });
    }

    @Test
    public void testLoginUser_Success() {
        User user = new User(1, "Paula", "paula@example.com", "hashedPassword");

        // Simular la búsqueda del usuario y la validación de la contraseña
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", user.getPassword())).thenReturn(true);
        when(tokenService.generateToken(user)).thenReturn("fakeToken");

        // Ejecutar la función de login
        String token = userService.loginUser("paula@example.com", "password123");

        // Verificar que se genera un token
        assertEquals("fakeToken", token);
    }

    @Test
    public void testLoginUser_InvalidCredentials() {
        // Simular que el usuario no existe
        when(userRepository.findByEmail("paula@example.com")).thenReturn(Optional.empty());

        // Verificar que se lanza la excepción adecuada
        assertThrows(InvalidCredentialsException.class, () -> {
            userService.loginUser("paula@example.com", "wrongPassword");
        });
    }

    @Test
    public void testUpdateUser_Success() {
        User existingUser = new User(1, "Paula", "paula@example.com", "password123");
        User updatedUser = new User(1, "UpdatedPaula", "newpaula@example.com", "password123");

        // Simular la extracción de usuario del token y la búsqueda en la base de datos
        when(tokenService.getUserFromToken(Mockito.anyString())).thenReturn(Optional.of(existingUser));
        when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(Mockito.any(User.class))).thenReturn(updatedUser);

        // Ejecutar la actualización
        User result = userService.updateUser(1, updatedUser, "Bearer fakeToken");

        // Verificar que el usuario fue actualizado correctamente
        assertEquals("UpdatedPaula", result.getName());
        assertEquals("newpaula@example.com", result.getEmail());
    }

    @Test
    public void testUpdateUser_InvalidToken() {
        // Simular que el token es inválido
        when(tokenService.getUserFromToken(Mockito.anyString())).thenReturn(Optional.empty());

        // Verificar que se lanza la excepción adecuada
        assertThrows(InvalidCredentialsException.class, () -> {
            userService.updateUser(1, new User(), "Bearer invalidToken");
        });
    }

}


