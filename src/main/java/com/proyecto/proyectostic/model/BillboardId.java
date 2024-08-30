package com.proyecto.proyectostic.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

public class BillboardId implements Serializable {
    private Integer movieId;
    private Integer cinemaId;

    // hashCode, equals, getters, setters
}
