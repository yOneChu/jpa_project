package com.jpabok.jpashop.service;

import com.jpabok.jpashop.domain.Address;
import com.jpabok.jpashop.domain.Member;
import com.jpabok.jpashop.domain.Order;
import com.jpabok.jpashop.domain.OrderStatus;
import com.jpabok.jpashop.domain.exception.NotEnughStockException;
import com.jpabok.jpashop.domain.item.Book;
import com.jpabok.jpashop.domain.item.Item;
import com.jpabok.jpashop.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderService orderService;


    @Test // 상품주문
    public void product_order() throws Exception {

        //given
        Member member = createMember();

        //when
        Book book = createBook("jpa 책", 10000, 10);

        int orderCount = 2;

        //then
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);


        Assert.assertEquals("삼풍 주문시 상태는 OERDER", OrderStatus.ORDER, getOrder.getStatus());
        Assert.assertEquals("주문한 상품 종류 수가 정확해야 한다.", 1, getOrder.getOrderItems().size());
        Assert.assertEquals("주문 가격은 가격 * 수량이다.", (10000 * orderCount), getOrder.getTotalPrice());
        Assert.assertEquals("주문 수량만큼 재고가 줄어야 한다.", 8, book.getStockQuantity());

    }



    //상품주문_재고수량초과
    @Test(expected = NotEnughStockException.class) // 해당 NotEnughStockException 발생해야 된다는 표시
    public void order_stockCancel() {
        Member member = createMember();
        Item item = createBook("jpa 책", 10000, 10);

        int orderCount = 11;

        //when
        orderService.order(member.getId(), item.getId(), orderCount);

        //then -- 혹시라도 이 부분까지 오면 예외발생해야 된다고 알려준다.
        Assert.fail("재고 수량 부족 예외가 발생해야 한다");
    }

    //주문취소
    @Test
    public void order_cancel() throws  Exception {

        //given
        Member member = createMember();
        Item item = createBook("jpa 책", 10000, 10);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        Assert.assertEquals("주문 취소시 상태는 CANCEL 이다.", OrderStatus.CANCEL, getOrder.getStatus());
        Assert.assertEquals("주문이 취소된 상품은 그만큼 재고가 증가해야 한다.", 10, item.getStockQuantity());


    }


    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }

}