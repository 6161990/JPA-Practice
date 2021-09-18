package jpabasic.ex1hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        try{
            Team team = new Team();
            team.setName("teamA");
            entityManager.persist(team);

            Member2 member1 = new Member2();
            member1.setUsername("제니");
            member1.setTeam(team);

            entityManager.persist(member1);

            entityManager.flush();
            entityManager.clear();

            List<Member2> members = entityManager.createQuery("select m from Member2 m", Member2.class)
                    .getResultList();


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
