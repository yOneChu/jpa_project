package com.jpabok.jpashop.domain.item;

import com.jpabok.jpashop.domain.Category;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


// JOIN : 정규화된 스타일로
// SINGLE_TABLE : 한 테이블에 다 넣는거
// TABLE_PER_CLASS : 관련 테이블만 나오게
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter @Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

}
