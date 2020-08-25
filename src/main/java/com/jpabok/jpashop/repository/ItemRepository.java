package com.jpabok.jpashop.repository;

import com.jpabok.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ItemRepository {
    private final EntityManager em;

    public void  save(Item item){
        if(item.getId() == null){
            em.persist(item);
        }else{
            log.info("merge ==============");
            em.merge(item); // 실무에서는 잘 쓰이지 않는다.
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }
}
