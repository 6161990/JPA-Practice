package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id") //fk가 member_id가 된다.
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; //주문 시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문 상태 ORDER, CANCLE

    /* member, order 클래스 둘다 서로를 가지고 있다.
    * 하지만, db에서 fk는 order만 가지고 있다.
    * 이말은 member,order 사이의 관계를 변경하고 싶을 때 fk를 바꿔야하는 상황이라면,
    * member, order 중 어느 곳에서 값이 변경되었을 때 fk를 바꿔야하지? 혼란.
    * 그러므로 둘 중에 한 곳만 변경을 감지해서 fk를 수정하도록 약속. 어느 곳? 더 가까운 곳.
    *
    *
    * */

}
