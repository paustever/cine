package com.proyecto.proyectostic.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ReservationDetailId implements Serializable {

    private Integer reservationId;
    private Integer seatId;
    public ReservationDetailId() {
    }


    public ReservationDetailId(Integer reservationId, Integer seatId) {
        this.reservationId = reservationId;
        this.seatId = seatId;
    }

    // Getters y Setters
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

    // Sobrescribir equals y hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReservationDetailId that = (ReservationDetailId) o;

        if (!Objects.equals(reservationId, that.reservationId)) return false;
        return Objects.equals(seatId, that.seatId);
    }

    @Override
    public int hashCode() {
        int result = reservationId != null ? reservationId.hashCode() : 0;
        result = 31 * result + (seatId != null ? seatId.hashCode() : 0);
        return result;
    }
}
