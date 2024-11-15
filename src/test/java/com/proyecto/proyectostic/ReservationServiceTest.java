package com.proyecto.proyectostic;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.proyecto.proyectostic.model.*;
import com.proyecto.proyectostic.repository.ReservationRepository;
import com.proyecto.proyectostic.repository.ReservationDetailRepository;
import com.proyecto.proyectostic.repository.SeatRepository;
import com.proyecto.proyectostic.service.ReservationService;
import com.proyecto.proyectostic.service.SeatService;
import com.proyecto.proyectostic.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private SeatService seatService;

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private ReservationDetailRepository reservationDetailRepository;

    @Mock
    private TokenService tokenService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testReserveSeats() throws Exception {

        // Preparar el token de prueba
        String token = "Bearer validToken";

        // Crear un usuario de prueba
        User user = new User();
        user.setUserId(1);
        when(tokenService.getUserFromToken("validToken")).thenReturn(Optional.of(user));

        // Crear el ShowTime de prueba
        ShowTime showTime = new ShowTime();
        showTime.setShowtimeId(1);
        showTime.setShowtimeDate(new Date());
        showTime.setReservedSeats(new ArrayList<>()); // Inicializar la lista de asientos reservados

        // Crear los SeatIds de prueba
        SeatId seatId1 = new SeatId(1, 1, 1);
        SeatId seatId2 = new SeatId(1, 1, 2);
        List<SeatId> seatIds = Arrays.asList(seatId1, seatId2);

        // Crear los asientos de prueba
        Seat seat1 = new Seat();
        seat1.setRoomId(1);
        seat1.setRowNumber(1);
        seat1.setSeatNumber(1);

        Seat seat2 = new Seat();
        seat2.setRoomId(1);
        seat2.setRowNumber(1);
        seat2.setSeatNumber(2);

        // Mockear el comportamiento del repositorio
        when(seatRepository.findAllById(seatIds)).thenReturn(Arrays.asList(seat1, seat2));

        // Mockear la creación de la reserva
        Reservation savedReservation = new Reservation();
        savedReservation.setReservationId(1);
        savedReservation.setUser(user);
        savedReservation.setShowtime(showTime);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(savedReservation);

        // Llamar al metodo del servicio
        Reservation result = reservationService.reserveSeats(token, seatIds, showTime);

        // Verificar que el repositorio de reservas fue llamado
        verify(reservationRepository, times(1)).save(any(Reservation.class));

        // Verificar que los detalles de la reserva fueron guardados
        verify(reservationDetailRepository, times(2)).save(any(ReservationDetail.class));

        // Verificar que la reserva devuelta es la esperada
        assertNotNull(result);
        assertEquals(1, result.getReservationId());

    }

    @Test
    void testCancelReservation() throws Exception {

        // Crear token de prueba
        String token = "Bearer validToken";

        // Crear usuario de prueba
        User user = new User();
        user.setUserId(1);
        when(tokenService.getUserFromToken("validToken")).thenReturn(Optional.of(user));

        // Crear reserva de prueba
        Reservation reservation = new Reservation();
        reservation.setReservationId(1);
        reservation.setUser(user);

        // Crear detalles de reserva con asientos
        Seat seat1 = new Seat();
        seat1.setRoomId(1);
        seat1.setRowNumber(1);
        seat1.setSeatNumber(1);

        Seat seat2 = new Seat();
        seat2.setRoomId(1);
        seat2.setRowNumber(1);
        seat2.setSeatNumber(2);

        ReservationDetail detail1 = new ReservationDetail();
        detail1.setSeat(seat1);
        ReservationDetail detail2 = new ReservationDetail();
        detail2.setSeat(seat2);

        List<ReservationDetail> reservationDetails = Arrays.asList(detail1, detail2);

        when(reservationRepository.findById(1)).thenReturn(Optional.of(reservation));
        when(reservationDetailRepository.findByReservationId(1)).thenReturn(reservationDetails);

        // Llamar al metodo del servicio
        reservationService.cancelReservation(token, 1);

        // Verificar que los detalles de la reserva fueron eliminados
        verify(reservationDetailRepository, times(1)).deleteAll(reservationDetails);

        // Verificar que la reserva fue eliminada
        verify(reservationRepository, times(1)).delete(reservation);

    }

    @Test
    void testGetReservationsByUserId() {

        // Crear un usuario y reservas de prueba
        User user = new User();
        user.setUserId(1);

        Reservation reservation1 = new Reservation();
        reservation1.setReservationId(1);
        reservation1.setUser(user);

        Reservation reservation2 = new Reservation();
        reservation2.setReservationId(2);
        reservation2.setUser(user);

        List<Reservation> expectedReservations = Arrays.asList(reservation1, reservation2);

        // Mockear el comportamiento del repositorio
        when(reservationRepository.findByUser_UserId(1)).thenReturn(expectedReservations);

        // Llamar al metodo del servicio
        List<Reservation> actualReservations = reservationService.getReservationsByUserId(1);

        // Verificar que el resultado es el esperado
        assertEquals(expectedReservations.size(), actualReservations.size());
        assertEquals(expectedReservations.get(0).getReservationId(), actualReservations.get(0).getReservationId());
        assertEquals(expectedReservations.get(1).getReservationId(), actualReservations.get(1).getReservationId());

        // Verificar que el repositorio fue llamado una vez
        verify(reservationRepository, times(1)).findByUser_UserId(1);

    }
}