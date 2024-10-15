package com.proyecto.proyectostic;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import com.proyecto.proyectostic.excepcion.MovieAlreadyExistsException;
import com.proyecto.proyectostic.model.Movie;
import com.proyecto.proyectostic.repository.MovieRepository;
import com.proyecto.proyectostic.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class MovieServiceTest {

        @Mock
        private MovieRepository movieRepository;

        @InjectMocks
        private MovieService movieService;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);  // Inicializa los mocks
        }

        @Test
        void testGetAllMovies() {
            // Preparar datos de prueba
            Movie movie1 = new Movie();
            movie1.setName("Movie 1");
            Movie movie2 = new Movie();
            movie2.setName("Movie 2");

            List<Movie> movies = Arrays.asList(movie1, movie2);

            // Mockear el comportamiento del repositorio
            when(movieRepository.findAll()).thenReturn(movies);

            // Llamar al método del servicio
            List<Movie> result = movieService.getAllMovies();

            // Verificar el resultado
            assertEquals(2, result.size());
            assertEquals("Movie 1", result.get(0).getName());
            assertEquals("Movie 2", result.get(1).getName());
        }
    @Test
    void testGetMovieById() {
        // Preparar una película de prueba
        Movie movie = new Movie();
        movie.setMovieId(1);
        movie.setName("Movie Test");

        // Mockear el comportamiento del repositorio
        when(movieRepository.findById(1)).thenReturn(Optional.of(movie));

        // Llamar al método del servicio
        Optional<Movie> result = movieService.getMovieById(1);

        // Verificar el resultado
        assertTrue(result.isPresent());
        assertEquals("Movie Test", result.get().getName());
    }

    @Test
    void testGetMovieById_NotFound() {
        // Mockear el comportamiento del repositorio para devolver Optional vacío
        when(movieRepository.findById(1)).thenReturn(Optional.empty());

        // Llamar al método del servicio
        Optional<Movie> result = movieService.getMovieById(1);

        // Verificar que no se encontró ninguna película
        assertFalse(result.isPresent());
    }

    @Test
    void testSaveMovie() {
        // Preparar una película de prueba
        Movie movie = new Movie();
        movie.setName("New Movie");

        // Mockear el comportamiento del repositorio para devolver la película guardada
        when(movieRepository.save(movie)).thenReturn(movie);

        // Llamar al método del servicio
        Movie savedMovie = movieService.saveMovie(movie);

        // Verificar el resultado
        assertNotNull(savedMovie);
        assertEquals("New Movie", savedMovie.getName());
    }

    @Test
    void testDeleteMovie() {
        // Llamar al método del servicio para eliminar una película
        movieService.deleteMovie(1);

        // Verificar que el repositorio fue llamado para eliminar la película
        verify(movieRepository, times(1)).deleteById(1);
    }

    @Test
    void testAddMovie() {
        // Preparar una película de prueba
        Movie movie = new Movie();
        movie.setName("Unique Movie");

        // Mockear el comportamiento del repositorio para devolver que no existe una película con ese nombre
        when(movieRepository.existsByName(movie.getName())).thenReturn(false);
        when(movieRepository.save(movie)).thenReturn(movie);

        // Llamar al método del servicio
        Movie addedMovie = movieService.addMovie(movie);

        // Verificar el resultado
        assertNotNull(addedMovie);
        assertEquals("Unique Movie", addedMovie.getName());
    }

    @Test
    void testAddMovie_MovieAlreadyExists() {
        // Preparar una película de prueba
        Movie movie = new Movie();
        movie.setName("Existing Movie");

        // Mockear el comportamiento del repositorio para devolver que la película ya existe
        when(movieRepository.existsByName(movie.getName())).thenReturn(true);

        // Verificar que se lance la excepción
        assertThrows(MovieAlreadyExistsException.class, () -> movieService.addMovie(movie));
    }
}



