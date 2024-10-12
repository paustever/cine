package com.proyecto.proyectostic.model;

import java.io.Serializable;
import java.util.Objects;

public class SeatId implements Serializable {

    private Integer roomid;      // ID de la sala
    private Integer rowNumber;   // Número de fila
    private Integer seatNumber;  // Número de asiento

    public SeatId() {}

    public SeatId(Integer roomid, Integer rowNumber, Integer seatNumber) {
        this.roomid = roomid;
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
    }

    public Integer getRoomId() {
        return roomid;
    }

    public void setRoomId(Integer roomId) {
        this.roomid = roomId;
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
        return Objects.equals(roomid, seatId.roomid) &&
                Objects.equals(rowNumber, seatId.rowNumber) &&
                Objects.equals(seatNumber, seatId.seatNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomid, rowNumber, seatNumber);
    }
}
