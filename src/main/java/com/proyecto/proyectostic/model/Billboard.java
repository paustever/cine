package com.proyecto.proyectostic.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Billboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer billboardId;

    @ManyToOne
    @JoinColumn(name = "cinema_id", nullable = false)  // Usa snake_case para el nombre de la columna
    private Cinema cinema;


    // Inicializa la lista de showTimes
    @OneToMany(mappedBy = "billboard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShowTime> showTimes = new ArrayList<>();

    public Billboard() {
    }

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
