package com.fpt.jpos.controller;

import com.fpt.jpos.service.IStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
@CrossOrigin
@RequiredArgsConstructor
public class StatisticsController {

    private final IStatisticsService statisticsService;

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/get-statistics")
    public ResponseEntity<?> getStatistics() {
        ResponseEntity<?> response;

        try {
            response = ResponseEntity.ok(statisticsService.getStatistics());
        } catch (Exception e) {
            e.printStackTrace();
            response = ResponseEntity.status(400).build();
        }

         return response;
    }

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/get-sales-report")
    public ResponseEntity<?> getSalesReport() {
        ResponseEntity<?> response;

        try {
            response = ResponseEntity.ok(statisticsService.getSalesReport());
        } catch (Exception ex) {
            ex.printStackTrace();
            response = ResponseEntity.status(400).build();
        }

        return response;
    }

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/get-recently-purchased")
    public ResponseEntity<?> getRecentlyPurchased() {
        ResponseEntity<?> response;

        try {
            response = ResponseEntity.ok(statisticsService.getRecentlyPurchased());
        } catch (Exception ex) {
            ex.printStackTrace();
            response = ResponseEntity.status(400).build();
        }

        return response;
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping("/get-payment-by-date")
    public ResponseEntity<?> getPaymentByDate() {
        ResponseEntity<?> response;
        try {
            response = ResponseEntity.ok(statisticsService.getPaymentByDate());
        } catch (Exception ex) {
            response = ResponseEntity.status(400).build();
        }

        return response;
    }
}
