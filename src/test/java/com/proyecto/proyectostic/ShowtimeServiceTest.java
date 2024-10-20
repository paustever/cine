package com.proyecto.proyectostic;

import com.proyecto.proyectostic.model.*;
import com.proyecto.proyectostic.repository.ShowtimeRepository;
import com.proyecto.proyectostic.service.ShowtimeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShowtimeServiceTest {

    @Mock
    private ShowtimeRepository showtimeRepository;

    @InjectMocks
    private ShowtimeService showtimeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetShowtimeById() {

        // Crear ID compuesto de prueba (movie, showtimeDate, room)
        Integer movieId = 1;
        Integer roomId = 1;
        Date showtimeDate = new Date();

        // Crear el objeto ShowTimeId
        ShowTimeId showTimeId = new ShowTimeId(movieId, showtimeDate, roomId);

        // Crear showtime de prueba
        ShowTime showTime = new ShowTime();
        showTime.setMovie(new Movie());
        showTime.getMovie().setMovieId(movieId);
        showTime.setRoom(new Room());
        showTime.getRoom().setRoomId(roomId);
        showTime.setShowtimeDate(showtimeDate);

        // Mockear el comportamiento del repositorio
        when(showtimeRepository.findById(showTimeId)).thenReturn(Optional.of(showTime));

        // Llamar al metodo del servicio
        ShowTime result = showtimeService.getShowtimeById(showTimeId);

        // Verificar el resultado
        assertNotNull(result);  // Asegurar que no es nulo
        assertEquals(movieId, result.getMovie().getMovieId());  // Verificar que la película es la correcta

    }

    @Test
    void testGetShowTimesByMovie() {

        // Crear cine de prueba
        Cinema cinema = new Cinema();
        cinema.setCinemaId(1);
        cinema.setAddress("Test Address");

        // Crear película de prueba
        Movie movie = new Movie();
        movie.setMovieId(1);
        movie.setName("Test Movie");

        // Crear cartelera de prueba
        Billboard billboard = new Billboard();
        billboard.setBillboardId(1);
        billboard.setCinema(cinema);

        // Crear horarios de prueba
        ShowTime showTime1 = new ShowTime();
        showTime1.setBillboard(billboard);
        showTime1.setMovie(movie);

        ShowTime showTime2 = new ShowTime();
        showTime2.setBillboard(billboard);
        showTime2.setMovie(movie);

        List<ShowTime> showTimes = Arrays.asList(showTime1, showTime2);

        // Mockear el comportamiento del repositorio
        when(showtimeRepository.findByMovie(movie)).thenReturn(showTimes);

        // Llamar al metodo del servicio
        Map<Cinema, List<ShowTime>> result = showtimeService.getShowTimesByMovie(movie);

        // Verificar los resultados
        assertEquals(1, result.size());  // Debe haber un cine
        assertEquals(2, result.get(cinema).size());  // Debe haber 2 horarios en ese cine

    }

    @Test
    void getShowTimesByMovieAndDate() {

        // Crear cine de prueba
        Cinema cinema = new Cinema();
        cinema.setCinemaId(1);
        cinema.setAddress("Test Address");

        // Crear película de prueba
        Movie movie = new Movie();
        movie.setMovieId(1);
        movie.setName("Test Movie");

        // Crear cartelera de prueba
        Billboard billboard = new Billboard();
        billboard.setBillboardId(1);
        billboard.setCinema(cinema);

        // Crear horarios de prueba con fechas específicas
        Date currentDate = new Date();
        ShowTime showTime1 = new ShowTime();
        showTime1.setBillboard(billboard);
        showTime1.setMovie(movie);
        showTime1.setShowtimeDate(currentDate);  // Fecha actual

        ShowTime showTime2 = new ShowTime();
        showTime2.setBillboard(billboard);
        showTime2.setMovie(movie);
        showTime2.setShowtimeDate(currentDate);  // Fecha actual

        List<ShowTime> showTimes = Arrays.asList(showTime1, showTime2);

        // Mockear el comportamiento del repositorio
        when(showtimeRepository.findByMovieAndShowtimeDate(movie, currentDate)).thenReturn(showTimes);

        // Llamar al metodo del servicio
        Map<Cinema, List<ShowTime>> result = showtimeService.getShowTimesByMovieAndDate(movie, currentDate);

        // Verificar los resultados
        assertEquals(1, result.size());  // Debe haber un cine
        assertEquals(2, result.get(cinema).size());  // Debe haber 2 horarios en ese cine para esa fecha

    }
}