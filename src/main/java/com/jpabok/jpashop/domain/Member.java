package com.jpabok.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded // 내장type 을 포함했다.
    private Address address;

    @OneToMany(mappedBy = "member") // Order 테이블의 member에 의해 매핑된다.
    private List<Order> orders = new ArrayList<>();
}
