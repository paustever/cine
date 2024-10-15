package com.proyecto.proyectostic.model;

import jakarta.persistence.Column;

import java.io.Serializable;
import java.util.Objects;

public class SeatId implements Serializable {

    @Column(name = "room_id")  // Consistente con el nombre de la columna
    private Integer roomId;

    @Column(name = "row_number")
    private Integer rowNumber;

    @Column(name = "seat_number")
    private Integer seatNumber;
    public SeatId() {}

    public SeatId(Integer roomid, Integer rowNumber, Integer seatNumber) {
        this.roomId = roomid;
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeatId seatId = (SeatId) o;
        return Objects.equals(roomId, seatId.roomId) &&
                Objects.equals(rowNumber, seatId.rowNumber) &&
                Objects.equals(seatNumber, seatId.seatNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId, rowNumber, seatNumber);
    }
}
