package com.fpt.jpos.service;

import com.fpt.jpos.dto.StatisticsDTO;
import com.fpt.jpos.pojo.Payment;
import com.fpt.jpos.repository.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsService implements IStatisticsService {

    private final ICustomerService customerService;
    private final IOrderService orderService;
    private final IPaymentService paymentService;
    private final IProductRepository productRepository;

    @Override
    public StatisticsDTO getStatistics() {

        Double revenue = 0.0;
        List<Payment> payments = paymentService.findAll();
        for(Payment payment : payments) {
            revenue += payment.getAmountPaid();
        }

        return StatisticsDTO.builder()
                .noCustomers(customerService.findAll().size())
                .noOrders(orderService.findAll().size())
                .noSales(orderService.findCompletedOrders().size())
                .revenue(revenue)
                .build();
    }

    @Override
    public List<Integer> getSalesReport() {
        List<Integer> result = new ArrayList<>();
        result.add(productRepository.getRingProducts().size());
        result.add(productRepository.getNecklaceProducts().size());
        result.add(productRepository.getEarringsProducts().size());
        result.add(productRepository.getNecklaceProducts().size());
        return result;
    }
}
