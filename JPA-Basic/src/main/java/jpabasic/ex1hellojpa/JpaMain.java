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

           member2.getAddressHistory().add(new Address("old1", "street", "3312341"));
           member2.getAddressHistory().add(new Address("old2", "street", "913128"));

           entityManager.persist(member2);

           entityManager.flush();
           entityManager.clear();
           
           System.out.println("===================START====================");
           Member2 findMember = entityManager.find(Member2.class, member2.getId());  //지연로딩임

            List<Address> addressHistory = findMember.getAddressHistory();
            for(Address address : addressHistory){
                System.out.println("address = "+ address.getCity());
            }

            Set<String> favoriteFoods = findMember.getFavoriteFoods();
            for(String food : favoriteFoods){
                System.out.println("food = "+ food);
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
