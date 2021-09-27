package jpql;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class jpql_Order {

    @Id @GeneratedValue
    private Long id;
    private int orderAmount;

    @Embedded
    private jpql_Address address;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private jpql_Product product;
}
