package com.jpabok.jpashop.api;


import com.jpabok.jpashop.domain.Address;
import com.jpabok.jpashop.domain.Order;
import com.jpabok.jpashop.domain.OrderStatus;
import com.jpabok.jpashop.repository.OrderRepository;
import com.jpabok.jpashop.repository.OrderSearch;
import com.jpabok.jpashop.repository.OrderSimpleQueryDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * xToOne(ManyToOne, OneToOne)
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());

        for(Order order : all) {
            order.getMember().getName(); //LAZY 강제 초기화,    order.getMember()까지는 쿼리가안날라가고 .getName()하는 순간 db에 쿼리가 날라간다
            order.getDelivery().getAddress(); //LAZY 강제 초기화,    order.getMember()까지는 쿼리가안날라가고 .getAddress()하는 순간 db에 쿼리가 날라간다
        }

        return all;
    }


    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> orderV2() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<SimpleOrderDto> result =   orders.stream()
                    .map(o -> new SimpleOrderDto(o))
                    .collect(Collectors.toList());

        return result;
    }


    // Fetch 조인 Good!
    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> orderV3() {
        List<Order> orders  =   orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDto> result = orders.stream()
                    .map(o -> new SimpleOrderDto(o))
                    .collect(Collectors.toList());

        return result;
    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderRepository.findOrderDtos();

    }


    @Data
    static class SimpleOrderDto {
        private long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
        }
    }





}
