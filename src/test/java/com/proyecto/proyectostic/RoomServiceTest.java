package com.proyecto.proyectostic;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.proyecto.proyectostic.model.Room;
import com.proyecto.proyectostic.repository.RoomRepository;
import com.proyecto.proyectostic.service.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService roomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializa los mocks
    }

    @Test
    void testGetAllRooms() {
        // Crear salas de prueba
        Room room1 = new Room();
        Room room2 = new Room();

        List<Room> rooms = Arrays.asList(room1, room2);

        // Mockear el comportamiento del repositorio
        when(roomRepository.findAll()).thenReturn(rooms);

        // Llamar al método del servicio
        List<Room> result = roomService.getAllRooms();

        // Verificar el resultado
        assertEquals(2, result.size());
    }

    @Test
    void testGetRoomById() {
        // Crear una sala de prueba
        Room room = new Room();
        room.setRoomId(1);

        // Mockear el comportamiento del repositorio
        when(roomRepository.findById(1)).thenReturn(Optional.of(room));

        // Llamar al método del servicio
        Optional<Room> result = roomService.getRoomById(1);

        // Verificar el resultado
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getRoomId());
    }

    @Test
    void testGetRoomById_NotFound() {
        // Mockear el comportamiento del repositorio para devolver Optional vacío
        when(roomRepository.findById(1)).thenReturn(Optional.empty());

        // Llamar al método del servicio
        Optional<Room> result = roomService.getRoomById(1);

        // Verificar que no se encontró ninguna sala
        assertFalse(result.isPresent());
    }
    @Test
    void testSaveRoom() {
        // Crear una sala de prueba
        Room room = new Room();

        // Mockear el comportamiento del repositorio para devolver la sala guardada
        when(roomRepository.save(room)).thenReturn(room);

        // Llamar al método del servicio
        Room savedRoom = roomService.saveRoom(room);

        // Verificar el resultado
        assertNotNull(savedRoom);
    }
    @Test
    void testDeleteRoom() {
        // Llamar al método del servicio para eliminar una sala
        roomService.deleteRoom(1);

        // Verificar que el repositorio fue llamado para eliminar la sala
        verify(roomRepository, times(1)).deleteById(1);
    }

}

