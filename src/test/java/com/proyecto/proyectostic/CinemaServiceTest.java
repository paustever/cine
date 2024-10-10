package com.proyecto.proyectostic;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.proyecto.proyectostic.excepcion.CinemaAlreadyExistsException;
import com.proyecto.proyectostic.model.Cinema;
import com.proyecto.proyectostic.repository.CinemaRepository;
import com.proyecto.proyectostic.service.CinemaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class CinemaServiceTest {

    @Mock
    private CinemaRepository cinemaRepository;

    @InjectMocks
    private CinemaService cinemaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializa los mocks
    }

    @Test
    void testGetAllCinemas() {
        // Crear cines de prueba
        Cinema cinema1 = new Cinema();
        cinema1.setNeighborhood("Barrio 1");
        Cinema cinema2 = new Cinema();
        cinema2.setNeighborhood("Barrio 2");

        List<Cinema> cinemas = Arrays.asList(cinema1, cinema2);

        // Mockear el comportamiento del repositorio
        when(cinemaRepository.findAll()).thenReturn(cinemas);

        // Llamar al método del servicio
        List<Cinema> result = cinemaService.getAllCinemas();

        // Verificar el resultado
        assertEquals(2, result.size());
        assertEquals("Barrio 1", result.get(0).getNeighborhood());
        assertEquals("Barrio 2", result.get(1).getNeighborhood());
    }

    @Test
    void testGetCinemaById() {
        // Crear un cine de prueba
        Cinema cinema = new Cinema();
        cinema.setCinemaId(1);
        cinema.setNeighborhood("Barrio Test");

        // Mockear el comportamiento del repositorio
        when(cinemaRepository.findById(1)).thenReturn(Optional.of(cinema));

        // Llamar al método del servicio
        Optional<Cinema> result = cinemaService.getCinemaById(1);

        // Verificar el resultado
        assertTrue(result.isPresent());
        assertEquals("Barrio Test", result.get().getNeighborhood());
    }

    @Test
    void testGetCinemaById_NotFound() {
        // Mockear el comportamiento del repositorio para devolver Optional vacío
        when(cinemaRepository.findById(1)).thenReturn(Optional.empty());

        // Llamar al método del servicio
        Optional<Cinema> result = cinemaService.getCinemaById(1);

        // Verificar que no se encontró ningún cine
        assertFalse(result.isPresent());
    }
    @Test
    void testDeleteCinema() {
        // Llamar al método del servicio para eliminar un cine
        cinemaService.deleteCinema(1);

        // Verificar que el repositorio fue llamado para eliminar el cine
        verify(cinemaRepository, times(1)).deleteById(1);
    }

    @Test
    void testRegisterCinema() {
        // Crear un cine de prueba
        Cinema cinema = new Cinema();
        cinema.setCinemaId(1);
        cinema.setNeighborhood("Barrio Unico");

        // Mockear el comportamiento del repositorio para devolver que el cine no existe
        when(cinemaRepository.existsById(1)).thenReturn(false);
        when(cinemaRepository.save(cinema)).thenReturn(cinema);

        // Llamar al método del servicio
        Cinema registeredCinema = cinemaService.registerCinema(cinema);

        // Verificar el resultado
        assertNotNull(registeredCinema);
        assertEquals("Barrio Unico", registeredCinema.getNeighborhood());
    }

    @Test
    void testRegisterCinema_CinemaAlreadyExists() {
        // Crear un cine de prueba
        Cinema cinema = new Cinema();
        cinema.setCinemaId(1);

        // Mockear el comportamiento del repositorio para devolver que el cine ya existe
        when(cinemaRepository.existsById(1)).thenReturn(true);

        // Verificar que se lance la excepción
        assertThrows(CinemaAlreadyExistsException.class, () -> cinemaService.registerCinema(cinema));
    }



}
