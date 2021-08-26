package jpabook.jpashop;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext// 현재 스프링 부트를 쓰고 있기 때문에 스프링 컨테이너에서 모든게 다 동작. 해당 어노테이션이 있으면 엔티티를 주입시켜줌
    private EntityManager em;

    //사이드 이펙트를 고려해 return 값을 거의 남기지 않는 방식 , id정도만 있으면 조회는 할 수 있으므로.
    public Long save(Member member){
        em.persist(member);
        return member.getId();
    }

    public Member find(Long id){
        return em.find(Member.class, id);
    }
}
