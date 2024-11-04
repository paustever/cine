package com.proyecto.proyectostic.model;

import jakarta.persistence.*;

@Entity
@IdClass(SeatId.class)
public class Seat {

    @Id
    @Column(name = "room_id")
    private Integer roomId;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "room_id", nullable = false, insertable = false, updatable = false)
    private Room room;

    @Id
    @Column(name = "row_number")
    private Integer rowNumber;

    @Id
    @Column(name = "seat_number")
    private Integer seatNumber;


    public Seat(){
    }

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
}
