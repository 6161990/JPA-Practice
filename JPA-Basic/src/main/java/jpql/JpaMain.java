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
            jpql_Team team = new jpql_Team();
            team.setName("team a");
            entityManager.persist(team);

            jpql_Member jqqlMember = new jpql_Member();
            jqqlMember.setUsername("member1");
            jqqlMember.setAge(12);
            jqqlMember.setTeam(team);
            jqqlMember.setType(MemberType.ADMIN);
            entityManager.persist(jqqlMember);

            entityManager.flush();
            entityManager.clear();

/*
            String innerJoin = "select m from jpql_Member m inner join m.team t";
            String leftJoin = "select m from jpql_Member m left join m.team t";
            String setaJoin = "select count(m) from jpql_Member m, Team t where m.username = t.name";
            List<jpql_Member> resultList = entityManager.createQuery(setaJoin, jpql_Member.class).getResultList();
 */

            String type = "select m.username, 'HELLO', TRUE From jpql_Member m " +
                    "where m.type = :userType";
            List<Object[]> result = entityManager.createQuery(type)
                    .setParameter("userType", MemberType.ADMIN)
                    .getResultList();

            for(Object[] objects : result){
                System.out.println("objects = " + objects[0]);
                System.out.println("objects = " + objects[1]);
                System.out.println("objects = " + objects[2]);
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
