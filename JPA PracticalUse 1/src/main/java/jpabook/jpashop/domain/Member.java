package jpabook.jpashop.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.lang.management.LockInfo;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded // 내장 타입을 포함했다.
    private Address address;

    @OneToMany(mappedBy = "member") //order 테이블에 있는 member 필드에 의해 매핑된 것, 이렇게 함으로써 읽기전용(거울이 되는 것)
    private List<Order> orders = new ArrayList<>();

    

}
