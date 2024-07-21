package com.fpt.jpos.service;

import com.fpt.jpos.dto.StatisticsDTO;
import com.fpt.jpos.pojo.Order;
import com.fpt.jpos.pojo.Payment;
import com.fpt.jpos.pojo.Product;
import com.fpt.jpos.repository.IPaymentRepository;
import com.fpt.jpos.repository.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsService implements IStatisticsService {

    private final ICustomerService customerService;
    private final IOrderService orderService;
    private final IProductRepository productRepository;
    private final IPaymentRepository paymentRepository;

    @Override
    public StatisticsDTO getStatistics() {

        Double revenue = 0.0;
        List<Payment> payments = paymentRepository.findAll();
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
        result.add(productRepository.getBraceletsProducts().size());
        return result;
    }

    @Override
    public List<Product> getRecentlyPurchased() {
        List<Order> orderList = orderService.findAll();
        orderList.sort(Comparator.comparing(Order::getOrderDate).reversed());
        return orderList.stream()
                .map(Order::getProduct)
                .filter(Objects::nonNull)
                .limit(5)
                .collect(Collectors.toList());

    }

    @Override
    public List<Object[]> getPaymentByDate() {
        return this.paymentRepository.getPaymentByDates();
    }
}
