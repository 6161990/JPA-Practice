package jpql;

import jpabasic.ex1hellojpa.jpashop.domain.Member;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        try{
            jpql_Team teamA = new jpql_Team();
            teamA.setName("팀 A");
            entityManager.persist(teamA);

            jpql_Team teamB = new jpql_Team();
            teamB.setName("팀 B");
            entityManager.persist(teamB);

            jpql_Member jqqlMember1 = new jpql_Member();
            jqqlMember1.setUsername("회원1");
            jqqlMember1.setTeam(teamA);
            entityManager.persist(jqqlMember1);

            jpql_Member jqqlMember2 = new jpql_Member();
            jqqlMember2.setUsername("회원2");
            jqqlMember2.setTeam(teamA);
            entityManager.persist(jqqlMember2);

            jpql_Member jqqlMember3 = new jpql_Member();
            jqqlMember3.setUsername("회원3");
            jqqlMember3.setTeam(teamB);
            entityManager.persist(jqqlMember3);


            entityManager.flush();
            entityManager.clear();

           // String query = "select m from jpql_Member m";
            String query ="select m from jpql_Member m join fetch m.team";

            List<jpql_Member> result = entityManager.createQuery(query, jpql_Member.class)
                    .getResultList();

            for(jpql_Member member : result){
                System.out.println("Member = "+ member.getUsername()+", "+member.getTeam().getName());
                //회원 1, 팀 A (SQL)
                //회원 2, 팀 A (1차 캐시)
                //회원 3, 팀 B (SQL)
            }


            transaction.commit();
        } catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
        }finally {
            entityManager.close();
        }
        entityManagerFactory.close();
    }
}
