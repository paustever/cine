package com.proyecto.proyectostic.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Billboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "billboard_id")
    private Integer billboardId;


    @ManyToOne
    @JoinColumn(name = "cinema_id", nullable = false)
    private Cinema cinema;

    @OneToMany(mappedBy = "billboard", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
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
