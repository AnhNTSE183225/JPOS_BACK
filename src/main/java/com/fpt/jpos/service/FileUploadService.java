package com.fpt.jpos.service;

import com.cloudinary.Cloudinary;
import com.fpt.jpos.pojo.Order;
import com.fpt.jpos.pojo.Staff;
import com.fpt.jpos.pojo.enums.OrderStatus;
import com.fpt.jpos.repository.IOrderRepository;
import com.fpt.jpos.repository.IStaffRepository;
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

    private final IOrderRepository orderRepository;

    private final IStaffRepository staffRepository;


    @Override
    public String upload(MultipartFile multipartFile) throws IOException{
        return cloudinary.uploader()
                .upload(multipartFile.getBytes(),
                        Map.of("public_id", UUID.randomUUID().toString()))
                .get("url")
                .toString();
    }

    @Override
    public String uploadModelDesignFile(MultipartFile multipartFile, Integer orderId, Integer designStaffId) throws IOException {
        String url = cloudinary.uploader()
                .upload(multipartFile.getBytes(),
                        Map.of("public_id", UUID.randomUUID().toString()))
                .get("url")
                .toString();

        Optional<Order> theOrder = orderRepository.findById(orderId);
        Optional<Staff> theStaff = staffRepository.findById(designStaffId);
        if (theOrder.isPresent() && theStaff.isPresent()) {
            Order order = theOrder.get();
            Staff staff = theStaff.get();
            order.setModelFile(url);
            order.setStatus(OrderStatus.pending_design);
            order.setDesignStaff(staff);
            orderRepository.save(order);
        }
        return url;
    }
}
