package com.proyecto.proyectostic.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(ReservationDetailId.class) // Aquí se utiliza la clave compuesta
public class ReservationDetail implements Serializable {

    @Id
    @Column(name = "reservation_id")
    private Integer reservationId;

    @Id
    @Column(name = "seat_id")
    private Integer seatId;

    @ManyToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;
    @ManyToOne
    @JoinColumns({ // Usar @JoinColumns si Seat tiene una clave compuesta
            @JoinColumn(name = "roomId", referencedColumnName = "roomId", nullable = false),
            @JoinColumn(name = "rowNumber", referencedColumnName = "rowNumber", nullable = false),
            @JoinColumn(name = "seatNumber", referencedColumnName = "seatNumber", nullable = false)
    })
    private Seat seat;

    public ReservationDetail() {
    }

    public Integer getReservationId() {
        return reservationId;
    }

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }

    public Integer getSeatId() {
        return seatId;
    }

    public void setSeatId(Integer seatId) {
        this.seatId = seatId;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }
}

