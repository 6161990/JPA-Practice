package jpabook.jpashop.web;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class BookForm {

    private Long id;

    @NotEmpty(message = "책 제목을 입력하세요.")
    private String name;

    @NotNull(message = "가격을 입력하세요.")
    @Range(min = 1000, max = 100000)
    private int price;

    private int stockQuantity;

    private String author;

    private String isbn;

}
