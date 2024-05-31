package com.fpt.jpos.service;

import com.cloudinary.Cloudinary;
import com.fpt.jpos.pojo.Order;
import com.fpt.jpos.pojo.enums.OrderStatus;
import com.fpt.jpos.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class FileUploadService implements IFileUploadService {

    private final Cloudinary cloudinary;

    private IOrderRepository orderRepository;

    @Override
    public String uploadModelDesignFile(MultipartFile multipartFile, Integer orderId) throws IOException {
        String url = cloudinary.uploader()
                .upload(multipartFile.getBytes(),
                        Map.of("public_id", UUID.randomUUID().toString()))
                .get("url")
                .toString();

        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            order.get().setDesignFile(url);
            order.get().setStatus(OrderStatus.pending_design);
            orderRepository.save(order.get());
        }
        return url;
    }
}
