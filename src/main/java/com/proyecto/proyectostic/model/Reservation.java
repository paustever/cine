package com.proyecto.proyectostic.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reservationId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Date date;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservationDetail> reservationDetails;

    @ManyToOne
    @JoinColumn(name = "showtime_id")
    private ShowTime showtime;


    public Integer getReservationId() {
        return reservationId;
    }

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<ReservationDetail> getReservationDetails() {
        return reservationDetails;
    }

    public void setReservationDetails(List<ReservationDetail> reservationDetails) {
        this.reservationDetails = reservationDetails;
    }

    public void setShowtime(ShowTime showtime) {
    }
}
