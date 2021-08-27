package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

  /* @Inheritance :상속관계 매핑시 상속관계 전략을 지정해야함

    JOINED :가장 정교화된 스타일
    SINGLE_TABLE : 한 테이블에 다 때려박기
    TABLE_PER_CLASS : 상속화된 애들(앨범, 북, 무비) 만 나오는 전략

    @DiscriminatorColumn : 부모클래스에 걸어둠. 하위 클래스를 구분하는 용도

    */
}
