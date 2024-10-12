package com.proyecto.proyectostic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import com.proyecto.proyectostic.model.Seat;
import com.proyecto.proyectostic.model.SeatId;
import com.proyecto.proyectostic.repository.SeatRepository;
import com.proyecto.proyectostic.service.SeatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class SeatServiceTest {

    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private SeatService seatService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);  // Inicializa mocks
    }

    @Test
    public void testGetSeatById_Success() throws Exception {
        // Crear un Seat con la clave compuesta
        Seat seat = new Seat();
        seat.setRoomid(1);
        seat.setRowNumber(5);
        seat.setSeatNumber(10);
        seat.setAvailable(true);

        // Crear el SeatId
        SeatId seatId = new SeatId(1, 5, 10);

        // Mockear el repositorio para devolver el asiento usando SeatId
        when(seatRepository.findById(seatId)).thenReturn(Optional.of(seat));

        // Llamar al método del servicio
        Seat result = seatService.getSeatById(seatId);

        // Verificar que el resultado sea correcto
        assertEquals(1, result.getRoomid());
        assertEquals(5, result.getRowNumber());
        assertEquals(10, result.getSeatNumber());
        assertEquals(true, result.getAvailable());
    }

    @Test
    public void testGetSeatsByRoomId() {
        // Crear asientos de prueba
        Seat seat1 = new Seat();
        seat1.setRoomid(1);
        seat1.setRowNumber(5);
        seat1.setSeatNumber(10);
        seat1.setAvailable(true);

        Seat seat2 = new Seat();
        seat2.setRoomid(1);
        seat2.setRowNumber(6);
        seat2.setSeatNumber(11);
        seat2.setAvailable(false);

        List<Seat> seats = Arrays.asList(seat1, seat2);

        // Mockear el repositorio para devolver la lista de asientos
        when(seatRepository.findByRoomRoomid(1)).thenReturn(seats);

        // Llamar al método del servicio
        List<Seat> result = seatService.getSeatsByRoomId(1);

        // Verificar el tamaño de la lista y los asientos
        assertEquals(2, result.size());
        assertEquals(5, result.get(0).getRowNumber());
        assertEquals(10, result.get(0).getSeatNumber());
        assertEquals(6, result.get(1).getRowNumber());
        assertEquals(11, result.get(1).getSeatNumber());
    }

    @Test
    public void testSaveSeat() {
        Seat seat = new Seat();
        seat.setRoomid(1);
        seat.setRowNumber(5);
        seat.setSeatNumber(10);
        seat.setAvailable(true);

        // Mockear el comportamiento del repositorio
        when(seatRepository.save(seat)).thenReturn(seat);

        // Llamar al método del servicio
        Seat result = seatService.saveSeat(seat);

        // Verificar que el asiento fue guardado correctamente
        assertEquals(1, result.getRoomid());
        assertEquals(5, result.getRowNumber());
        assertEquals(10, result.getSeatNumber());
        assertEquals(true, result.getAvailable());
    }

    @Test
    public void testDeleteSeat_Success() throws Exception {
        Seat seat = new Seat();
        seat.setRoomid(1);
        seat.setRowNumber(5);
        seat.setSeatNumber(10);
        seat.setAvailable(true);

        // Crear el SeatId
        SeatId seatId = new SeatId(1, 5, 10);

        // Mockear el comportamiento del repositorio
        when(seatRepository.findById(seatId)).thenReturn(Optional.of(seat));

        // Llamar al método del servicio
        seatService.deleteSeat(seatId);

        // Verificar que el repositorio eliminó el asiento
        verify(seatRepository).delete(seat);
    }

    @Test
    public void testDeleteSeat_NotFound() {
        // Crear el SeatId
        SeatId seatId = new SeatId(1, 5, 10);

        // Mockear que no se encuentre el asiento
        when(seatRepository.findById(seatId)).thenReturn(Optional.empty());

        // Verificar que se lanza la excepción
        assertThrows(Exception.class, () -> {
            seatService.deleteSeat(seatId);
        });
    }
    @Test
    public void testUpdateSeatAvailability() throws Exception {
        // Crear un Seat de prueba
        Seat seat = new Seat();
        seat.setRoomid(1);
        seat.setRowNumber(5);
        seat.setSeatNumber(10);
        seat.setAvailable(false);  // El estado original del asiento

        // Crear el SeatId correspondiente
        SeatId seatId = new SeatId(1, 5, 10);

        // Mockear el repositorio para devolver el Seat cuando se busca por SeatId
        when(seatRepository.findById(seatId)).thenReturn(Optional.of(seat));

        // Mockear el repositorio para devolver el Seat actualizado cuando se guarda
        when(seatRepository.save(seat)).thenReturn(seat);

        // Llamar al método del servicio
        Seat updatedSeat = seatService.updateSeatAvailability(seatId, true);

        // Verificar que el Seat fue actualizado correctamente
        assertNotNull(updatedSeat);  // Verificar que no sea null
        assertEquals(true, updatedSeat.getAvailable());
        verify(seatRepository).save(seat);  // Verificar que save() fue llamado
    }


}



