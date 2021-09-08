package jpabasic.ex1hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        try{
/*            Member member = new Member();
            member.setId(3L);
            member.setName("리사");

            entityManager.persist(member);*/

            Member member1 = entityManager.find(Member.class, 3L);
            Member member2 = entityManager.find(Member.class, 3L);

            System.out.println("result = " + (member1 == member2)); 

            transaction.commit();
        } catch (Exception e){
            transaction.rollback();
        }finally {
            entityManager.close();
        }
        entityManagerFactory.close();
    }
}
