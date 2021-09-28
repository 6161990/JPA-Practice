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
            jqqlMember.setUsername("관리자");
            jqqlMember.setAge(12);
            jqqlMember.setTeam(team);
            jqqlMember.setType(MemberType.ADMIN);
            entityManager.persist(jqqlMember);

            entityManager.flush();
            entityManager.clear();

            String query1 = "select case when m.age <= 10 then '학생요금'" +
                                        "when m.age >= 60 then '경로요금' " +
                                        "else '일반요금' end " +
                           "from jpql_Member m";

            String query2 = "select coalesce(m.username, '이름 없는 회원') from jpql_Member m";

            String query ="select nullif(m.username, '관리자') as username from jpql_Member m";
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
