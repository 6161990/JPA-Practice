package jpabook.jpashop.service;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    //OrderItem, Delivery는 왜 따로 repository가 없는가.
    // -> Order 엔티티에서 CascadeType.ALL을 해놓았기 때문에 order가 persist하면 해당 orderItem, Delivery도 persist됨
    // Cascade의 범위는 어디까지 해야하는가. Order만 OrderItem, Delivery를 사용하므로 이런 경우에만 CascadeALL을 사용
    // OrderItem , Delivery 입장에서 다른 곳에 참조되는 경우가 없고 라이프 스타일이 한 곳(Order)에만 한정되어 있다면 사용

    /* 참고 : 다른 곳에서 객체에 대해 OrderItem orderItem1 = new OrderItem(); 과 같은 형식으로 객체를 생성할 수 있음.
    * but, OrderItem에 대해서 생성을 하려면 OrderItem.createOrderItem(아이템, 아이템가격, 갯수)로 진행해야함.
    * 따라서 기본 생성자를 protected로 만들어 놓으면 그나마 안전. jpa가 protected까지는 허용함.
    * @NoArgsConstructor(access = AccessLevel.PROTECTED) */

    /** 주문 */
    @Transactional
    public Long order(Long memberId, Long itemId, int count){

        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송 정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        //주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);
        return order.getId();
    }

    //JPA를 활용하면 이점 : 변경 로직들을 전부 다 세팅해줄 필요가 없다. (주문취소경우 아이템 재고수량을 직접 바꿔줘야한다던지..)
    // 엔티티안의 데이터들만 바꿔주면 JPA가 알아서 변경내용을 감지해서 (더티체킹) 바뀐 데이터들을 넣어서 UPDATE 쿼리를 날려줌

    /** 주문 취소 */
    @Transactional
    public void cancelOrder(Long orderId){
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);

        //주문 취소
        order.cancel();
    }

    /** 주문 검색*/
    public List<Order> findOrders(OrderSearch orderSearch){
        return orderRepository.findAllByString(orderSearch);
    }

}
