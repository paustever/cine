package com.proyecto.proyectostic;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.proyecto.proyectostic.model.*;
import com.proyecto.proyectostic.repository.BillboardRepository;
import com.proyecto.proyectostic.repository.MovieRepository;
import com.proyecto.proyectostic.repository.RoomRepository;
import com.proyecto.proyectostic.repository.ShowtimeRepository;
import com.proyecto.proyectostic.service.BillboardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

class BillboardServiceTest {

    @Mock
    private BillboardRepository billboardRepository;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private ShowtimeRepository showTimeRepository;

    @InjectMocks
    private BillboardService billboardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializa los mocks
    }

    @Test
    void testGetMoviesFromBillboard() {

        // Crear cartelera de prueba
        Billboard billboard = new Billboard();
        billboard.setBillboardId(1);

        // Crear película de prueba
        Movie movie = new Movie();
        movie.setMovieId(1);
        movie.setName("Movie 1");

        // Crear showtime de prueba y agregar a la cartelera
        ShowTime showTime = new ShowTime();
        showTime.setMovie(movie);
        billboard.setShowTimes(Arrays.asList(showTime));  // Agregar showTime a la lista de showTimes

        // Mockear el comportamiento del repositorio
        when(billboardRepository.findById(1)).thenReturn(Optional.of(billboard));

        // Llamar al metodo del servicio
        List<Movie> result = billboardService.getMoviesFromBillboard(1);

        // Verificar el resultado
        assertEquals(1, result.size());  // Verificar que la lista contenga 1 película
        assertEquals(movie.getName(), result.get(0).getName());  // Verificar que la película sea la correcta

    }

    @Test
    void testAddMovieToBillboard() {

        // Crear cartelera de prueba
        Billboard billboard = new Billboard();
        billboard.setBillboardId(1);

        // Crear película de prueba
        Movie movie = new Movie();
        movie.setMovieId(1);
        movie.setName("Movie 1");

        // Crear habitación de prueba
        Room room = new Room();
        room.setRoomId(1);
        room.setCinema(new Cinema()); // Establecer un cine (puedes ajustarlo según tus necesidades)

        // Crear fecha de inicio y fin para la película
        Date startDate = new Date(); // Asignar la fecha actual como ejemplo
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        cal.add(Calendar.DATE, 1); // Hacer que endDate sea un día después de startDate
        Date endDate = cal.getTime();

        movie.setStartDate(startDate);
        movie.setEndDate(endDate);

        // Mockear el comportamiento de los repositorios
        when(billboardRepository.findById(1)).thenReturn(Optional.of(billboard));
        when(movieRepository.findById(1)).thenReturn(Optional.of(movie));
        when(roomRepository.findById(1)).thenReturn(Optional.of(room));

        // Llamar al método del servicio
        ShowTime result = billboardService.addMovieToBillboard(1, 1, 1, startDate);

        // Verificar el resultado
        assertNotNull(result);
        assertEquals(movie, result.getMovie());
        assertEquals(billboard, result.getBillboard());
        assertEquals(room, result.getRoom());

        // Verificar que el showTime haya sido guardado en el repositorio
        verify(showTimeRepository).save(result);  // Verifica que el repositorio fue llamado con el showTime
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