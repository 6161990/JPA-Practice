package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id") //fk가 member_id가 된다.
    private Member member;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    /* 원래 모든 엔티티는 persist 각각 넣어주어야함
    persist(orderItemA)
    persist(orderItemB)
    persist(orderItemC)
    persist(order)

    cascade = CascadeType.ALL 적용하면 persist를 전파함
    persist(order) / orderItemA.B.C를 List<OrderItem> 컬렉션에 담아서 persist. delete도 마찬가지
    */

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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

    public static void main(String[] args) {
        // 원래는 이렇게 구현해야하는데 번거롭고, 빠뜨릴 가능성이 있다.
        Member member = new Member();
        Order order = new Order();

        member.getOrders().add(order);
        order.setMember(member);
    }  그래서 필요한 */

    //== 연관관계 메소드 : 보통 위치는 더 컨트롤하는 한쪽에 ! ==//
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //==생성 메소드==//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //==비즈니스 로직==//
    /* 주문취소 */
    public void cancel(){
        if(delivery.getStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송 완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCLE);
        for(OrderItem orderItem : orderItems){
            orderItem.cancel();
        }
    }

    //==조회 로직==//
    /* 전체 주문 가격 조회 */
    public int getTotalPrice(){
        int totalPrice = orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
       /*
        위의 로직을 풀어놓은 코드
        for(OrderItem orderItem : orderItems){
            totalPrice += orderItem.getTotalPrice();
        }*/
        return totalPrice;
    }
}

