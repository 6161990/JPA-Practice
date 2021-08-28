package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)  //메모리 모드로 db까지 엮어서 test, 스프링과 통합 테스트 하기 위해 두가지 어노테이션
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception{
        //given
        Member member = new Member();
        member.setName("제니");

        //when ; member가 저장될때 insert문이 안나감. commit될 때 flush가 되면서 jpa 영속성 컨텍스트 안에 있는 해당 데이터(member)가 insert가 되는 것이므로.
        // @Rollback이라고 test에 지정해두면 commit을 한다. or EntityManager를 주입해서 flush()를 하면 insert가 나간다.
        Long saveId = memberService.join(member);

        //then
        Assertions.assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test
    public void 중복_회원_예외()  throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("리사");

        Member member2 = new Member();
        member2.setName("리사");

        //when
        memberService.join(member1);
        try {
            memberService.join(member2); //예외가 발생해야한다.
        } catch (IllegalStateException e) {    // try/catch 대신 @Test(expected = IllealStateException.class) 로 변경가능
            return;
        }

        //then
        fail("예외가 발생해야한다.");  //여기까지 오면 안되는 것, 그 전에 예외가 발생해야하는데 여기까지 왔다는 것은 발생해야할 에러가 발생하지않아 여기까지 도달했다는 것
    }

}