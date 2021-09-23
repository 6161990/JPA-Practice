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
           Address address = new Address("city", "street", "3403");

           Member2 member2 = new Member2();
           member2.setUsername("hello");
           member2.setHomeAddress(address);
           entityManager.persist(member2);


           Address copyAddress = new Address(address.getCity(), address.getStreet(), address.getZipcode());

           Member2 member3 = new Member2();
           member3.setUsername("hello");
           member3.setHomeAddress(copyAddress);
           entityManager.persist(member3);

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
