package com.proyecto.proyectostic.excepcion;


public class BillboardNotFoundException  extends RuntimeException {
    public BillboardNotFoundException (String message) {
        super(message);
    }
}