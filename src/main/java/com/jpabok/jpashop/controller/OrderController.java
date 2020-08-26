package com.jpabok.jpashop.controller;

import com.jpabok.jpashop.domain.Member;
import com.jpabok.jpashop.domain.Order;
import com.jpabok.jpashop.domain.item.Item;
import com.jpabok.jpashop.repository.OrderSearch;
import com.jpabok.jpashop.service.ItemService;
import com.jpabok.jpashop.service.MemberService;
import com.jpabok.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;


    @GetMapping("/order")
    public String createForm(Model model) {
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items",items);

        return "order/orderForm";
    }

    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count) {

        log.info("memberId == " + memberId);
        log.info("itemId == " + itemId);
        log.info("count == " + count);

        Long orderId = orderService.order(memberId, itemId, count);
        return "redirect:/orders";
    }


    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch")OrderSearch orderSearch, Model model) {
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);

        return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }
}
