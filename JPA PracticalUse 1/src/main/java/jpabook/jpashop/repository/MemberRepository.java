package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext // Jpa가 정한 표준 annotaion
    private EntityManager em; //스프링이 해당 JPA Entity Manager를 만들어서 주입해줌.


    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long id){
        return em.find(Member.class, id); //첫번째 타입, 두번째 pk
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name){
        return em.createQuery("select  m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        //jpql과 sql은 좀 다름. sql : 테이블을 대상으로 쿼리 / jsql : 엔티티(Member) 대상으로 쿼리
    }
}
