package com.jpabok.jpashop.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable // 어딘가에 내장될 수 있다.
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

}
