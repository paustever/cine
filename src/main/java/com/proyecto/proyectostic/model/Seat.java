package com.proyecto.proyectostic.model;

import jakarta.persistence.*;

@Entity
@IdClass(SeatId.class)
public class Seat {

    @Id
    private Integer roomId;
    @ManyToOne
    @JoinColumn(name = "roomId", nullable = false)
    private Room room; // Clave compuesta - sala

    @Id
    private Integer rowNumber; // Clave compuesta - número de fila

    @Id
    private Integer seatNumber; // Clave compuesta - número de asiento

    private Boolean available;

    public Seat() {}

    // Getters y Setters

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Integer getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(Integer rowNumber) {
        this.rowNumber = rowNumber;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

}
