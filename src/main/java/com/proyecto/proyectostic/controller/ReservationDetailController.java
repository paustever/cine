package com.proyecto.proyectostic.controller;

import com.proyecto.proyectostic.model.ReservationDetail;
import com.proyecto.proyectostic.service.ReservationDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservationdetails")
public class ReservationDetailController {

    @Autowired
    private ReservationDetailService reservationDetailService;

    @GetMapping
    public List<ReservationDetail> getAllReservationDetails() {
        return reservationDetailService.getAllReservationDetails();
    }

    @GetMapping("/{id}")
    public ReservationDetail getReservationDetailById(@PathVariable Integer id) {
        return reservationDetailService.getReservationDetailById(id);
    }

    @PostMapping
    public ReservationDetail createReservationDetail(@RequestBody ReservationDetail reservationDetail) {
        return reservationDetailService.saveReservationDetail(reservationDetail);
    }

    @DeleteMapping("/{id}")
    public void deleteReservationDetail(@PathVariable Integer id) {
        reservationDetailService.deleteReservationDetail(id);
    }
}

