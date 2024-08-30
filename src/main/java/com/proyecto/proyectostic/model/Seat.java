package com.proyecto.proyectostic.model;
import jakarta.persistence.*;


@Entity
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seatId;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    private Integer rowNumber;
    private Integer seatNumber;
    private Boolean available;

    // Getters y setters
}
