package com.proyecto.proyectostic.model;


import jakarta.persistence.*;

import java.util.Date;


@Entity
@IdClass(ReservationDetailId.class)
public class ReservationDetail {

    @Id
    private Integer reservationId;

    @Id
    private Integer seatId;

    @ManyToOne
    @JoinColumn(name = "reservation_id", nullable = false, insertable = false, updatable = false)
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "seat_id", nullable = false, insertable = false, updatable = false)
    private Seat seat;

    // Getters y setters
}
