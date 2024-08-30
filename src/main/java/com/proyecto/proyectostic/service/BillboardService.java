package com.proyecto.proyectostic.service;

import com.proyecto.proyectostic.model.Billboard;
import com.proyecto.proyectostic.repository.BillboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BillboardService {

    private final BillboardRepository billboardRepository;

    @Autowired
    public BillboardService(BillboardRepository billboardRepository) {
        this.billboardRepository = billboardRepository;
    }

    public List<Billboard> getAllBillboards() {
        return billboardRepository.findAll();
    }

    public Optional<Billboard> getBillboardById(Integer id) {
        return billboardRepository.findById(id);
    }

    public Billboard saveBillboard(Billboard billboard) {
        return billboardRepository.save(billboard);
    }

    public void deleteBillboard(Integer id) {
        billboardRepository.deleteById(id);
    }
}
