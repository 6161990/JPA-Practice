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
            //저장
            Team team = new Team();
            team.setName("BLACKPINK");
            entityManager.persist(team);

            Member2 member2 = new Member2();
            member2.setUsername("제니");
 //          member2.setTeam(team); //연관관계 정방향
 //          team.getMembers().add(member2); //연관관계 역방향 => 정, 역방향 둘다 값을 넣어주는 것이 맞음음            entityManager.persist(member2);
            member2.changeTeam(team); // 연관관계 정,역방향 둘 다 setting하는 member쪽 연관관계 편의 메소드
            //team.addMember(member2); // team쪽 연관관계 편의 메소드

            entityManager.flush();
            entityManager.clear();

            Member2 findMember = entityManager.find(Member2.class, member2.getId());

            List<Member2> members = findMember.getTeam().getMembers();

            for(Member2 m : members){
                System.out.println("m = "+m.getUsername());
            }


            transaction.commit();
        } catch (Exception e){
            transaction.rollback();
        }finally {
            entityManager.close();
        }
        entityManagerFactory.close();
    }
}
