package com.proyecto.proyectostic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Integer roomId;


    @ManyToOne
    @JoinColumn(name = "cinema_id", nullable = false)
    private Cinema cinema;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    @JsonIgnore
    private Map<SeatId, Seat> seats = new HashMap<>();

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "room_number")
    private int roomNumber;
    public Room() {
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public Map<SeatId, Seat> getSeats() {
        return seats;
    }

    public void setSeats(Map<SeatId, Seat> seats) {
        this.seats = seats;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
}
