package com.proyecto.proyectostic;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.proyecto.proyectostic.model.Billboard;
import com.proyecto.proyectostic.model.Movie;
import com.proyecto.proyectostic.model.ShowTime;
import com.proyecto.proyectostic.repository.BillboardRepository;
import com.proyecto.proyectostic.service.BillboardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class BillboardServiceTest {

    @Mock
    private BillboardRepository BillboardRepository;

    @InjectMocks
    private BillboardService BillboardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializa los mocks
    }

    @Test
    void testGetMoviesFromBillboard() {

        // Crear cartelera de prueba
        Billboard billboard = new Billboard();
        billboard.setBillboardId(1);

        // Crear y añadir horarios de películas de prueba
        Movie movie1 = new Movie();
        movie1.setMovieId(1);
        movie1.setName("Movie 1"); // Ajusta los atributos necesarios

        Movie movie2 = new Movie();
        movie2.setMovieId(2);
        movie2.setName("Movie 2"); // Ajusta los atributos necesarios

        ShowTime showTime1 = new ShowTime();
        showTime1.setMovie(movie1); // Asignar la primera película a el ShowTime

        ShowTime showTime2 = new ShowTime();
        showTime2.setMovie(movie2); // Asignar la segunda película a el ShowTime

        billboard.setShowTimes(Arrays.asList(showTime1, showTime2));

        // Mockear el comportamiento del repositorio
        when(BillboardRepository.findById(1)).thenReturn(Optional.of(billboard));

        // Llamar al mtodo del servicio
        List<Movie> result = BillboardService.getMoviesFromBillboard(1);

        // Verificar el resultado
        assertEquals(2, result.size());

    }

    @Test
    void testAddMovieToBillboard() {
    }

    @Test
    void testRemoveMovieFromBillboard() {
    }

    @Test
    void testGetBillboardByCinema() {
    }

    @Test
    void testGetBillboardById() {
    }

    @Test
    void testGetAllBillboards() {
    }

    @Test
    void testSaveBillboard() {
    }

    @Test
    void TestDeleteBillboard() {
    }

    @Test
    void TestGetAllMoviesFromAllBillboards() {
    }

    @Test
    void testGetAvailableBillboards() {
    }
}