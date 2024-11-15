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
    void getShowTimesByMovieCinemaAndDate() {

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
        Map<Cinema, List<ShowTime>> result = showtimeService.getShowTimesByMovieCinemaAndDate(movie, currentDate);

        // Verificar los resultados
        assertEquals(1, result.size());  // Debe haber un cine
        assertEquals(2, result.get(cinema).size());  // Debe haber 2 horarios en ese cine para esa fecha

    }

    @Test
    void testGetAvailableDatesByMovieAndCinema() {

        // Configuración de datos de prueba
        Movie movie = new Movie();
        Cinema cinema = new Cinema();

        Date date1 = new Date();
        Date date2 = new Date(date1.getTime() + 86400000L); // Fecha siguiente

        // Mockear los showtimes con fechas únicas
        ShowTime showTime1 = new ShowTime();
        showTime1.setShowtimeDate(date1);

        ShowTime showTime2 = new ShowTime();
        showTime2.setShowtimeDate(date2);

        // Configurar el comportamiento del mock
        when(showtimeRepository.findByMovieAndCinema(movie, cinema))
                .thenReturn(Arrays.asList(showTime1, showTime2));

        // Llamar al metodo a testear
        List<String> availableDates = showtimeService.getAvailableDatesByMovieAndCinema(movie, cinema);

        // Crear la lista esperada de fechas únicas
        List<String> expectedDates = Arrays.asList(date1.toString(), date2.toString());

        // Verificar los resultados
        assertEquals(2, availableDates.size()); // Solo se esperan 2 fechas
        assertEquals(expectedDates, availableDates); // Verificar que las fechas coinciden exactamente

    }

    @Test
    void testGetReservedSeatsForShowtime() {

        // Crear showtime de prueba
        ShowTime showTime = new ShowTime();
        showTime.setShowtimeId(1);

        // Crear asientos de prueba
        Seat seat1 = new Seat();
        seat1.setRoomId(1);
        seat1.setRowNumber(1);
        seat1.setSeatNumber(1);

        Seat seat2 = new Seat();
        seat2.setRoomId(1);
        seat2.setRowNumber(1);
        seat2.setSeatNumber(2);

        showTime.setReservedSeats(Arrays.asList(seat1, seat2));

        // Mockear el comportamiento del repositorio
        when(showtimeRepository.findByshowtimeId(1)).thenReturn(Optional.of(showTime));

        // Llamar al metodo del servicio
        List<Seat> result = showtimeService.getReservedSeatsForShowtime(1);

        // Verificar los resultados
        assertEquals(2, result.size()); // Deben haber 2 asientos reservados
        assertEquals(1, result.get(0).getSeatNumber()); // Verificar que el primer asiento tiene el número correcto
        assertEquals(2, result.get(1).getSeatNumber()); // Verificar que el segundo asiento tiene el número correcto

    }
}