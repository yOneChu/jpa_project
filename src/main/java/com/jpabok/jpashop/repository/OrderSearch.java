package com.jpabok.jpashop.repository;

import com.jpabok.jpashop.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class OrderSearch {

    private String memberName; //회원 이름
    private OrderStatus orderStatus; //주문 상태[ORDER, CANCEL]
}
