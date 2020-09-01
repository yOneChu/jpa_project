package com.jpabok.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @NotEmpty
    private String name;

    @Embedded // 내장type 을 포함했다.
    private Address address;

    @JsonIgnore // JSON으로 표현해줄때 제외한다
    @OneToMany(mappedBy = "member") // Order 테이블의 member에 의해 매핑된다.
    private List<Order> orders = new ArrayList<>();
}
