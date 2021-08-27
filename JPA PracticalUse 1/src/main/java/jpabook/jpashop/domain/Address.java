package jpabook.jpashop.domain;


import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable // 내장 타입으로 쓰이는 클래스다.
            // 둘 중 한곳만 존재해도 ok
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;
}
