package com.proyecto.proyectostic.model;

import java.io.Serializable;
import java.util.Objects;

public class ReservationDetailId implements Serializable {

    private Integer reservationId;
    private Integer seatId;

    // Constructor por defecto
    public ReservationDetailId() {
    }

    public ReservationDetailId(Integer reservationId, Integer seatId) {
        this.reservationId = reservationId;
        this.seatId = seatId;
    }

    // Getters y setters
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

    // Implementación de equals y hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationDetailId that = (ReservationDetailId) o;
        return Objects.equals(reservationId, that.reservationId) &&
                Objects.equals(seatId, that.seatId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationId, seatId);
    }
}
