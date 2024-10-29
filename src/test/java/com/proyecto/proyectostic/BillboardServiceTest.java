package com.proyecto.proyectostic;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.proyecto.proyectostic.model.*;
import com.proyecto.proyectostic.repository.*;
import com.proyecto.proyectostic.service.BillboardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class BillboardServiceTest {

    @Mock
    private BillboardRepository billboardRepository;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private ShowtimeRepository showTimeRepository;

    @Mock
    private CinemaRepository cinemaRepository;

    @InjectMocks
    private BillboardService billboardService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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

        // Llamar al metodo del servicio
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

        // Crear cine de prueba con atributos completos
        Cinema cinema = new Cinema();
        cinema.setCinemaId(1); // ID que buscas en el test
        cinema.setAddress("123 Street Name");

        // Crear película de prueba con atributos completos
        Movie movie = new Movie();
        movie.setMovieId(1);
        movie.setName("Movie 1");

        // Crear cartelera de prueba con atributos completos
        Billboard billboard = new Billboard();
        billboard.setBillboardId(1);
        billboard.setCinema(cinema); // Relación con el cine
        billboard.setShowTimes(new ArrayList<>());

        // Crear showtime de prueba y agregar a la cartelera
        ShowTime showTime = new ShowTime();
        showTime.setMovie(movie);
        showTime.setBillboard(billboard);
        showTime.setShowtimeDate(new Date()); // Fecha del showtime
        billboard.getShowTimes().add(showTime);  // Agregar showTime a la lista de showTimes

        // Mockear el comportamiento de los repositorios
        when(cinemaRepository.findById(1)).thenReturn(Optional.of(cinema)); // Mock del cine
        when(movieRepository.findById(1)).thenReturn(Optional.of(movie)); // Mock de la película
        when(billboardRepository.findByCinema_CinemaId(1)).thenReturn(billboard); // Mock de la cartelera

        // Llamar al metodo del servicio
        billboardService.removeMovieFromBillboard(1, 1);

        verify(cinemaRepository).findById(1);  // Verificar que el repositorio fue llamado
        verify(billboardRepository).save(billboard);  // Verificar que el repositorio fue llamado

        // Verificar que la película fue eliminada de la lista de showtimes
        assertTrue(billboard.getShowTimes().isEmpty(), "La lista de showTimes debería estar vacía después de eliminar la película");


    }
//
//    @Test
//    void testGetBillboardByCinema() {
//
//        // Crear cine de prueba con atributos completos
//        Cinema cinema = new Cinema();
//        cinema.setCinemaId(1); // ID que buscas en el test
//        cinema.setAddress("123 Street Name");
//
//        // Crear cartelera de prueba con atributos completos
//        Billboard billboard = new Billboard();
//        billboard.setBillboardId(1);
//        billboard.setCinema(cinema); // Relación con el cine
//
//        // Mockear el comportamiento de los repositorios
//        when(billboardRepository.findByCinema_CinemaId(1)).thenReturn(billboard); // Mock de la cartelera
//        when(cinemaRepository.findById(1)).thenReturn(Optional.of(cinema)); // Mock del cine
//
//        // Llamar al metodo del servicio
//        Billboard result = billboardService.getBillboardByCinema(1);
//
//        // Verificar el resultado
//        assertNotNull(result);
//        assertEquals(billboard, result);
//    }

    @Test
    void TestGetAllMoviesFromAllBillboards() {

        // Crear cine de prueba
        Cinema cinema = new Cinema();
        cinema.setCinemaId(1);
        cinema.setAddress("123 Street Name");
        cinema.setNeighborhood("Downtown");
        cinema.setNoRoom(5);
        cinema.setTelephone("123-456-789");

        // Crear películas de prueba
        Movie movie1 = new Movie();
        movie1.setMovieId(1);
        movie1.setName("Movie 1");

        Movie movie2 = new Movie();
        movie2.setMovieId(2);
        movie2.setName("Movie 2");

        // Crear cartelera de prueba con showtimes
        Billboard billboard1 = new Billboard();
        billboard1.setBillboardId(1);
        billboard1.setCinema(cinema);
        List<ShowTime> showTimes1 = new ArrayList<>();
        ShowTime showTime1 = new ShowTime();
        showTime1.setMovie(movie1);
        showTimes1.add(showTime1);
        billboard1.setShowTimes(showTimes1);

        Billboard billboard2 = new Billboard();
        billboard2.setBillboardId(2);
        billboard2.setCinema(cinema);
        List<ShowTime> showTimes2 = new ArrayList<>();
        ShowTime showTime2 = new ShowTime();
        showTime2.setMovie(movie2);
        showTimes2.add(showTime2);
        billboard2.setShowTimes(showTimes2);

        // Simular el comportamiento del repositorio
        when(billboardRepository.findAll()).thenReturn(List.of(billboard1, billboard2));

        // Llamar al metodo
        List<Movie> result = billboardService.getAllMoviesFromAllBillboards();

        // Verificar que el resultado contiene las películas correctas
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(movie1));
        assertTrue(result.contains(movie2));

        // Verificar que el repositorio fue llamado
        verify(billboardRepository).findAll();

    }

    @Test
    void testGetAvailableBillboards() {

        // Crear cine de prueba
        Cinema cinema = new Cinema();
        cinema.setCinemaId(1);
        cinema.setAddress("123 Street Name");
        cinema.setNeighborhood("Downtown");
        cinema.setNoRoom(5);
        cinema.setTelephone("123-456-789");

        // Crear película de prueba
        Movie movie = new Movie();
        movie.setMovieId(1);
        movie.setName("Movie 1");

        // Crear cartelera de prueba con showtimes en el futuro
        Billboard billboard1 = new Billboard();
        billboard1.setBillboardId(1);
        billboard1.setCinema(cinema);
        List<ShowTime> showTimes1 = new ArrayList<>();
        ShowTime showTime1 = new ShowTime();
        showTime1.setMovie(movie);
        showTime1.setShowtimeDate(new Date(System.currentTimeMillis() + 1000000)); // Futuro
        showTimes1.add(showTime1);
        billboard1.setShowTimes(showTimes1);

        // Crear cartelera de prueba con showtimes en el pasado (no disponible)
        Billboard billboard2 = new Billboard();
        billboard2.setBillboardId(2);
        billboard2.setCinema(cinema);
        List<ShowTime> showTimes2 = new ArrayList<>();
        ShowTime showTime2 = new ShowTime();
        showTime2.setMovie(movie);
        showTime2.setShowtimeDate(new Date(System.currentTimeMillis() - 1000000)); // Pasado
        showTimes2.add(showTime2);
        billboard2.setShowTimes(showTimes2);

        // Mockear el comportamiento del repositorio: devolver solo la cartelera con showtimes en el futuro
        when(billboardRepository.findAvailableBillboards(any(Date.class))).thenReturn(List.of(billboard1));

        // Llamar al método
        List<Billboard> result = billboardService.getAvailableBillboards();

        // Verificar que el resultado contiene solo las carteleras con showtimes futuros
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains(billboard1));
        assertFalse(result.contains(billboard2)); // No debe contener la cartelera con showtimes en el pasado

        // Verificar que el repositorio fue llamado con la fecha actual
        verify(billboardRepository).findAvailableBillboards(any(Date.class));

    }
}