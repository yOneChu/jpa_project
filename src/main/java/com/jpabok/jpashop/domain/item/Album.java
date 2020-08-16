package com.jpabok.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A")
@Setter
@Getter
public class Album extends Item {

    private String artist;
    private String etc;
}
