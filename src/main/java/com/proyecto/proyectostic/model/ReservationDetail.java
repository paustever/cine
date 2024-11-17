package com.proyecto.proyectostic.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.io.Serializable;

@Entity
public class ReservationDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name= "reservation_detail_id")
    private Integer reservationDetailId;

    @Column(name = "reservation_id")
    private Integer reservationId;


    @Column(name = "seat_id")
    private Integer seatId;

    @ManyToOne
    @JoinColumn(name = "reservation_id", nullable = false, insertable = false, updatable = false)
    @JsonBackReference("reservations-reservationDetail")
    private Reservation reservation;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "room_id", referencedColumnName = "room_id", nullable = false),
            @JoinColumn(name = "row_number", referencedColumnName = "row_number", nullable = false),
            @JoinColumn(name = "seat_number", referencedColumnName = "seat_number", nullable = false)
    })
    @JsonIgnore
    private Seat seat;

    @ManyToOne
    @JoinColumn(name = "showtime_id", referencedColumnName = "showtime_id", insertable = false, updatable = false)
    @JsonIgnore
    private ShowTime showtime;

    public Integer getReservationDetailId() {
        return reservationDetailId;
    }

    public void setReservationDetailId(Integer reservationDetailId) {
        this.reservationDetailId = reservationDetailId;
    }

    // Getters y Setters
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

    public ShowTime getShowtime() {
        return showtime;
    }

    public void setShowtime(ShowTime showtime) {
        this.showtime = showtime;}
}