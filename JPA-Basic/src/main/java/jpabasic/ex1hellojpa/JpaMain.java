package jpabasic.ex1hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Set;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        try{
            Member2 member2 = new Member2();
            member2.setUsername("JENNY");
            member2.setHomeAddress(new Address("homecity", "street", "124121"));
            member2.getFavoriteFoods().add("치킨");
            member2.getFavoriteFoods().add("족발");
            member2.getFavoriteFoods().add("피자");

           member2.getAddressHistory().add(new AddressEntity("old1", "street", "3312341"));
           member2.getAddressHistory().add(new AddressEntity("old2", "street", "913128"));

           entityManager.persist(member2);

           entityManager.flush();
           entityManager.clear();
           
           System.out.println("===================START====================");
           Member2 findMember = entityManager.find(Member2.class, member2.getId());  //지연로딩임

            //homeCity -> newCity
            //findMember.getHomeAddress().setCity("newCity");
            Address old = findMember.getHomeAddress();
            findMember.setHomeAddress(new Address("newCity", old.getStreet(), old.getZipcode()));

            //치킨 -> 똠양꿍
            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("똠양꿍");

            //old1 -> newCity1
            findMember.getAddressHistory().remove(new AddressEntity("old1", "street", "3312341"));
            findMember.getAddressHistory().add(new AddressEntity("newCity1", "street", "3312341"));

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
