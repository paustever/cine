package com.proyecto.proyectostic.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(ReservationDetailId.class)
public class ReservationDetail {

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
    @JoinColumns({
            @JoinColumn(name = "room_id", referencedColumnName = "room_id", nullable = false),
            @JoinColumn(name = "row_number", referencedColumnName = "row_number", nullable = false),
            @JoinColumn(name = "seat_number", referencedColumnName = "seat_number", nullable = false)
    })
    private Seat seat;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "movie_id", referencedColumnName = "movie_id", insertable = false, updatable = false),
            @JoinColumn(name = "showtime_date", referencedColumnName = "showtime_date", insertable = false, updatable = false),
            @JoinColumn(name = "room_id", referencedColumnName = "room_id", insertable = false, updatable = false)
    })
    private ShowTime showtime;



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
        this.showtime = showtime;
    }
}


