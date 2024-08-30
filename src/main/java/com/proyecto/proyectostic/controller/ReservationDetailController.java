package com.proyecto.proyectostic.controller;

import com.proyecto.proyectostic.model.ReservationDetail;
import com.proyecto.proyectostic.service.ReservationDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservationDetails")
public class ReservationDetailController {

    private final ReservationDetailService reservationDetailService;

    @Autowired
    public ReservationDetailController(ReservationDetailService reservationDetailService) {
        this.reservationDetailService = reservationDetailService;
    }

    @GetMapping
    public List<ReservationDetail> getAllReservationDetails() {
        return reservationDetailService.getAllReservationDetails();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDetail> getReservationDetailById(@PathVariable Integer id) {
        return reservationDetailService.getReservationDetailById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ReservationDetail createReservationDetail(@RequestBody ReservationDetail reservationDetail) {
        return reservationDetailService.saveReservationDetail(reservationDetail);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationDetail(@PathVariable Integer id) {
        reservationDetailService.deleteReservationDetail(id);
        return ResponseEntity.noContent().build();
    }
}
