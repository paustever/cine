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
    @JoinColumn(name = "reservation_id", nullable = false, insertable = false, updatable = false)
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "seat_id", nullable = false, insertable = false, updatable = false)
    private Seat seat;

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

