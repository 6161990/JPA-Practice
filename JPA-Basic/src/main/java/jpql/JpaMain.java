package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        try{
            jpql_Member jqqlMember = new jpql_Member();
            jqqlMember.setUsername("member1");
            jqqlMember.setAge(30);
            entityManager.persist(jqqlMember);

            TypedQuery<jpql_Member> findMemberQuery = entityManager.createQuery("select m from jpql_Member m where m.username =: username", jpql_Member.class);
            findMemberQuery.setParameter("username","member1");
            jpql_Member singleResult = findMemberQuery.getSingleResult();
            System.out.println(singleResult.getUsername());

            //또는

            jpql_Member result = entityManager.createQuery("select m from jpql_Member m where m.username =: username", jpql_Member.class)
                    .setParameter("username", "member1")
                    .getSingleResult();
            System.out.println("result = " + result.getUsername());


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
