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

            List<jpql_Member> resultList = entityManager.createNamedQuery("jpql_Member.findByUsername", jpql_Member.class)
                    .setParameter("username", "회원1")
                    .getResultList();

            for(jpql_Member member : resultList){
                System.out.println("member = " + member);
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
