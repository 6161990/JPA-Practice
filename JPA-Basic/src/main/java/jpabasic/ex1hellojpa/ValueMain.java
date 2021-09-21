package jpabasic.ex1hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class ValueMain {

    public static void main(String[] args) {
        // 기본 값 타입
        // 기본 타입은 절대 공유 x
        int a = 10;
        int b = a;

        a = 20;

        System.out.println("a = "+a); //20
        System.out.println("b = "+b); //10

        // 래퍼클래스나 String 같은 특수한 클래스는 공유 가능하지만 변경은 불가
        Integer aInteger = new Integer(10);
        Integer bInteger = a; //a의 주소가 복사된 것. 둘은 같은 것

        //a.setValue(20); 예를 들어 값을 바꾸는 메소드가 있다고 치면

        System.out.println("aInteger = " + aInteger); //둘다 같은 값
        System.out.println("bInteger = " + bInteger);

        //공유 가능한 객체이지만 변경 x
    }
}
