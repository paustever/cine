package com.proyecto.proyectostic.excepcion;

public class SeatNotReservedException extends RuntimeException {
    public SeatNotReservedException(String message) {
        super(message);
    }
}
