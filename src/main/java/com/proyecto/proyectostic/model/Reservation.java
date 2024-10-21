package com.proyecto.proyectostic.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Reservation {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Integer reservationId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "movie_id", referencedColumnName = "movie_id", nullable = false),
            @JoinColumn(name = "showtime_date", referencedColumnName = "showtime_date", nullable = false),
            @JoinColumn(name = "room_id", referencedColumnName = "room_id", nullable = false)
    })
    private ShowTime showtime;

    @Column(name = "reservation_date")
    private Date date;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservationDetail> reservationDetails;

    public Reservation() {
    }

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
        this.showtime = showtime;
    }

}
