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
            jqqlMember.setUsername("관리자1");
            jqqlMember.setAge(12);
            jqqlMember.setTeam(team);
            jqqlMember.setType(MemberType.ADMIN);
            entityManager.persist(jqqlMember);

            jpql_Member jqqlMember2 = new jpql_Member();
            jqqlMember2.setUsername("관리자2");
            entityManager.persist(jqqlMember2);

            entityManager.flush();
            entityManager.clear();


            String query ="select function('group_concat', m.username) from jpql_Member m";
            List<String> resultList = entityManager.createQuery(query, String.class).getResultList();

            for(String s : resultList){
                System.out.println("s = "+ s);
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
