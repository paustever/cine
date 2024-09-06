package com.proyecto.proyectostic.excepcion;

  public class CinemaAlreadyExistsException extends RuntimeException {
        public CinemaAlreadyExistsException(String message) {
            super(message);
        }

}
