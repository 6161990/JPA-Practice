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
            jpql_Member jqqlMember = new jpql_Member();
            jqqlMember.setUsername("member1");
            jqqlMember.setAge(30);
            entityManager.persist(jqqlMember);

            entityManager.flush();
            entityManager.clear();

            List<MemberDTO> result = entityManager.createQuery("select new jpql.MemberDTO(m.username, m.age) from jpql_Member m", MemberDTO.class)
                    .getResultList();

            MemberDTO memberDTO = result.get(0);
            System.out.println("memberDTO = "+ memberDTO.getName());
            System.out.println("memberDTO = "+ memberDTO.getAge());



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
