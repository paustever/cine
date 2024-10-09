package com.proyecto.proyectostic.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Billboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer billboardId;

    @ManyToOne
    @JoinColumn(name = "cinema_id", nullable = false)
    private Cinema cinema;

    // Una cartelera puede tener múltiples horarios de películas (ShowTimes)
    @OneToMany(mappedBy = "billboard", cascade = CascadeType.ALL)
    private List<ShowTime> showTimes;

    // Getters y Setters
    public Integer getBillboardId() {
        return billboardId;
    }

    public void setBillboardId(Integer billboardId) {
        this.billboardId = billboardId;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public List<ShowTime> getShowTimes() {
        return showTimes;
    }

    public void setShowTimes(List<ShowTime> showTimes) {
        this.showTimes = showTimes;
    }
}
