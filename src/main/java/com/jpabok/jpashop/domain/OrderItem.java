package com.jpabok.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jpabok.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // new로 생성하지 못하도록 막는다.
public class OrderItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문가격
    private int count; // 주문수량

    // new로 해서 만드는걸 방지한다. 이렇게 되면 생성자 변경할 경우 유지보수가 어려워진다.
    // 아래생성자는 @NoArgsConstructor(access = AccessLevel.PROTECTED) 붙이면 할 필요가 없다.
   /* protected OrderItem() {

    }*/

    //생성 메서드
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
         OrderItem orderItem = new OrderItem();
         orderItem.setItem(item);
         orderItem.setOrderPrice(orderPrice);
         orderItem.setCount(count);

         System.out.println("count ======== " + count);


         item.removeStock(count);
         return orderItem;
    }

    //비즈니스 로직
    public void cancel() {
        getItem().addStock(count); // 재고수량 원복
    }

    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
