package com.proyecto.proyectostic.dto;

import com.proyecto.proyectostic.model.SeatId;

import java.util.List;

public class CreateReservationRequest {
    private Integer showTimeId;
    private List<SeatId> seatIds;

    // Getters y Setters
    public Integer getShowTimeId() {
        return showTimeId;
    }

    public void setShowTimeId(Integer showTimeId) {
        this.showTimeId = showTimeId;
    }

    public List<SeatId> getSeatIds() {
        return seatIds;
    }

    public void setSeatIds(List<SeatId> seatIds) {
        this.seatIds = seatIds;
    }
}
