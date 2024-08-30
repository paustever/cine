package com.proyecto.proyectostic.controller;

import com.proyecto.proyectostic.model.Billboard;
import com.proyecto.proyectostic.service.BillboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/billboards")
public class BillboardController {

    private final BillboardService billboardService;

    @Autowired
    public BillboardController(BillboardService billboardService) {
        this.billboardService = billboardService;
    }

    @GetMapping
    public List<Billboard> getAllBillboards() {
        return billboardService.getAllBillboards();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Billboard> getBillboardById(@PathVariable Integer id) {
        return billboardService.getBillboardById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Billboard createBillboard(@RequestBody Billboard billboard) {
        return billboardService.saveBillboard(billboard);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBillboard(@PathVariable Integer id) {
        billboardService.deleteBillboard(id);
        return ResponseEntity.noContent().build();
    }
}
