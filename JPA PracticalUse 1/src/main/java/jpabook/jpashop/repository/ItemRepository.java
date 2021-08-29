package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class  ItemRepository {

    private final EntityManager em;

    public void save(Item item){
        if(item.getId() == null){ // 아이템은 jpa에 저장하기 전까지 id가 없음. 신규등록임을 확인하기 위해.
            em.persist(item);  //그래서 이건 신규 등록
        } else { //이미 jpa를 통해 한번 들어간 item
            em.merge(item);  // 이건 update 개념쯔음-
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }


}
