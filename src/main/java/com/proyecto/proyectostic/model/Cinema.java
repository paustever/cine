package com.proyecto.proyectostic.model;

import jakarta.persistence.*;

@Entity
public class Cinema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cinema_id")
    private Integer cinemaId;

    @Column(name = "neighborhood")
    private String neighborhood;

    @Column(name = "address")
    private String address;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "no_room")
    private Integer noRoom;

    public Cinema() {
    }

    public Integer getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(Integer cinemaId) {
        this.cinemaId = cinemaId;
    }

    public Integer getNoRoom() {
        return noRoom;
    }

    public void setNoRoom(Integer noRoom) {
        this.noRoom = noRoom;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

}
