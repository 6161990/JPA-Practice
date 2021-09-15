package jpabasic.ex1hellojpa;

import jpabasic.ex1hellojpa.jpashop.domain.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class MemberProduct {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member2 member;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    private int count;

    private int price;

    private LocalDateTime orderDateTime;

}
